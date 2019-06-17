package com.codeka.castawayterrain.biome;

import com.google.common.collect.Lists;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class ModBiomes {
    public static ShallowWarmOceanBiome SHALLOW_WARN_OCEAN = register("shallow_warm_ocean", new ShallowWarmOceanBiome());
    public static VolcanoBeachBiome VOLCANO_BEACH = register("volcano_beach", new VolcanoBeachBiome());
    public static VolcanoIslandBiome FOREST_VOLCANO = register("forest_volcano", new VolcanoIslandBiome(VolcanoIslandBiome.VegitationType.FOREST));
    public static VolcanoIslandBiome JUNGLE_VOLCANO = register("jungle_volcano", new VolcanoIslandBiome(VolcanoIslandBiome.VegitationType.JUNGLE));
    public static VolcanoIslandBiome BAMBOO_VOLCANO = register("bamboo_volcano", new VolcanoIslandBiome(VolcanoIslandBiome.VegitationType.BAMBOO_FOREST));

    public static void init() {
        // Nothing to do, the static initializer does it all.
    }

    public static <T extends Biome> T register(String name, T biome) {
        Registry.register(Registry.BIOME, name, biome);
        return biome;
    }

    public static List<Biome> all() {
        return Lists.newArrayList(SHALLOW_WARN_OCEAN, VOLCANO_BEACH, FOREST_VOLCANO, JUNGLE_VOLCANO, BAMBOO_VOLCANO);
    }

    public static boolean isVolcano(Biome biome) {
        return biome instanceof VolcanoIslandBiome;
    }
}
