package com.codeka.castawayterrain;

import com.codeka.castawayterrain.biome.ShallowOceanBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.codeka.castawayterrain.world.CastawayWorldType;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Main mod entrypoint for CastawayTerrain. We just register our custom world type.
 */
@Mod(modid="castawayterrain")
public class CastawayTerrainMod {
    public CastawayWorldType castawayWorldType;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        registerBiome("volcano_island", VolcanoIslandBiome.BIOME);
        registerBiome("shallow_warm_ocean", ShallowOceanBiome.BIOME);

        castawayWorldType = new CastawayWorldType();
    }

    private static void registerBiome(String name, Biome biome) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addSpawnBiome(biome);
        BiomeDictionary.addTypes(biome, BiomeDictionary.Type.MOUNTAIN); // TODO??
    }
}
