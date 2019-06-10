package com.codeka.castawayterrain;

import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.codeka.castawayterrain.world.CastawayWorldType;
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
        VolcanoIslandBiome.BIOME.setRegistryName("volcano_island");
        ForgeRegistries.BIOMES.register(VolcanoIslandBiome.BIOME);
        BiomeManager.addSpawnBiome(VolcanoIslandBiome.BIOME);
        BiomeDictionary.addTypes(VolcanoIslandBiome.BIOME, BiomeDictionary.Type.MOUNTAIN);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        castawayWorldType = new CastawayWorldType();
    }
}
