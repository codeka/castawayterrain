package com.codeka.castawayterrain.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;

/**
 * This is a cut'n'paste of {@link BiomeOcean}, but shallower.
 */
public class ShallowOceanBiome extends Biome {
    public static final ShallowOceanBiome BIOME = new ShallowOceanBiome();

    public ShallowOceanBiome() {
        super(new BiomeProperties("shallow_ocean")
                .setWaterColor(4445678));
    }
}
