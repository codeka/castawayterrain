package com.codeka.castawayterrain;

import com.codeka.castawayterrain.biome.ShallowWarmOceanBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.codeka.castawayterrain.world.CastawayWorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Main mod entrypoint for CastawayTerrain. We just register our custom world type.
 */
@Mod("castawayterrain")
public class CastawayTerrainMod {
    public CastawayWorldType castawayWorldType;

    public CastawayTerrainMod() {
        registerBiome("volcano_island", VolcanoIslandBiome.BIOME);
        registerBiome("shallow_warm_ocean", ShallowWarmOceanBiome.BIOME);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        castawayWorldType = new CastawayWorldType();
    }

    private static void registerBiome(String name, Biome biome) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addSpawnBiome(biome);
        BiomeDictionary.addTypes(biome, BiomeDictionary.Type.MOUNTAIN); // TODO??

    }
}
