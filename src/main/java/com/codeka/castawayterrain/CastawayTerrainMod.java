package com.codeka.castawayterrain;

import com.codeka.castawayterrain.biome.ShallowWarmOceanBiome;
import com.codeka.castawayterrain.biome.VolcanoBeachBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.codeka.castawayterrain.block.ModBlocks;
import com.codeka.castawayterrain.world.CastawayBiomeSource;
import com.codeka.castawayterrain.world.CastawayChunkGenerator;
import com.codeka.castawayterrain.world.CastawayChunkGeneratorConfig;
import com.codeka.castawayterrain.world.CastawayLevelType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/**
 * Main mod entrypoint for CastawayTerrain. We just register our custom world type.
 */
public class CastawayTerrainMod implements ModInitializer {
    private static final Logger L = LogManager.getLogger();

    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, CastawayChunkGenerator> CASTAWAY;
    public static LevelGeneratorType CASTAWAY_LEVEL_TYPE;

    @Override
    public void onInitialize() {
        L.info("Castaway Terrain initializing...");

        registerBiome("volcano_island", VolcanoIslandBiome.BIOME);
        registerBiome("volcano_beach", VolcanoBeachBiome.BIOME);
        registerBiome("shallow_warm_ocean", ShallowWarmOceanBiome.BIOME);

        ModBlocks.init();

        CASTAWAY_LEVEL_TYPE = CastawayLevelType.getType();
        CastawayGeneratorFactory factory = new CastawayGeneratorFactory();
        CASTAWAY = factory.getChunkGeneratorType(CastawayChunkGeneratorConfig::new);
        Registry.register(Registry.CHUNK_GENERATOR_TYPE, "castawayterrain:castaway", CASTAWAY);
    }

    private static void registerBiome(String name, Biome biome) {
        Registry.register(Registry.BIOME, name, biome);
   }

    /**
     * This is a bit hacky,
     * The short of it is we want to register the wastelands as CHUNK_GENERATOR_TYPE
     *
     * However  ChunkGeneratorType requires a factory interface ChunkGeneratorFactory
     * that is package private.  (thus we can't use the interface directly)
     *
     * The folowing class uses reflection to become an instance of "ChunkGeneratorFactory"
     * as well as reflection to create the ChunkGeneratorType object to pass in the
     * interface object
     */
    private class CastawayGeneratorFactory implements InvocationHandler {
        private Object factoryProxy;
        private Class factoryClass;

        CastawayGeneratorFactory(){
            //reflection hack, dev = mapped in dev environment, prod = intermediate value
            String dev_name = "net.minecraft.world.gen.chunk.ChunkGeneratorFactory";
            String prod_name = "net.minecraft.class_2801";

            try {
                factoryClass = Class.forName(dev_name);
            } catch (ClassNotFoundException e1){
                try {
                    factoryClass = Class.forName(prod_name);
                }catch (ClassNotFoundException e2){
                    throw new RuntimeException("Unable to find " + dev_name);
                }
            }
            factoryProxy = Proxy.newProxyInstance(factoryClass.getClassLoader(),
                    new Class[] {factoryClass},
                    this);
        }

        public CastawayChunkGenerator createProxy(World w, CastawayChunkGeneratorConfig config) {
            return new CastawayChunkGenerator(w, config);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            if(args.length == 3 &&
                    args[0] instanceof World &&
                    args[1] instanceof CastawayBiomeSource &&
                    args[2] instanceof OverworldChunkGeneratorConfig) {
                return createProxy((World)args[0], (CastawayChunkGeneratorConfig) args[2]);
            }

            throw new UnsupportedOperationException("Unknown Method: " + method.toString());
        }

        @SuppressWarnings("unchecked")
        <C extends ChunkGeneratorConfig, G extends ChunkGenerator<C>> ChunkGeneratorType<C, G> getChunkGeneratorType(
                Supplier<OverworldChunkGeneratorConfig> configSupplier){
            Constructor<?>[] constructors = ChunkGeneratorType.class.getDeclaredConstructors();

            for (Constructor<?> cons : constructors) {
                cons.setAccessible(true);
                if (cons.getParameterCount() != 3) {
                    continue;
                }

                try {
                    return (ChunkGeneratorType<C, G>) cons.newInstance(factoryProxy, true, configSupplier);
                } catch (Exception e) {
                    L.error("Error in calling Chunk Generator Type", e);
                }
            }
            L.error("Unable to find constructor for ChunkGeneratorType");
            return null;
        }
    }
}
