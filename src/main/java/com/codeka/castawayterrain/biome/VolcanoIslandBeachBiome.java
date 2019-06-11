package com.codeka.castawayterrain.biome;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class VolcanoIslandBeachBiome extends Biome {
    public static final VolcanoIslandBeachBiome BIOME = new VolcanoIslandBeachBiome();

    private static SurfaceBuilderConfig surfaceBuilderConfig() {
        return new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
    }

    VolcanoIslandBeachBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(
                        new DefaultSurfaceBuilder(SurfaceBuilderConfig::func_215455_a),
                        surfaceBuilderConfig()))
                .precipitation(Biome.RainType.RAIN)
                .category(Category.BEACH)
                .depth(0.0F)
                .scale(0.0F)
                .temperature(0.95F)
                .downfall(0.3F)
                .waterColor(4566514)
                .waterFogColor(267827)
                .parent(null));


/*
        super(new Biome.Builder()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(
                        new MountainSurfaceBuilder(SurfaceBuilderConfig::func_215455_a),
                        new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState())))
                .precipitation(RainType.RAIN)
                .category(Category.EXTREME_HILLS)
                .depth(7.5F)
                .scale(0.2F)
                .temperature(3.F)
                .downfall(0.3F)
                .waterColor(4159204)
                .waterFogColor(329011)
                .parent(null));

 */
    }

}
