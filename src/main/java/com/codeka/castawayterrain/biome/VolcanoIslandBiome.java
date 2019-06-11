package com.codeka.castawayterrain.biome;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.MountainSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class VolcanoIslandBiome extends Biome {

    public static final VolcanoIslandBiome BIOME = new VolcanoIslandBiome();

    public static final SurfaceBuilderConfig configSand = new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
    public static final SurfaceBuilderConfig configDirt = new SurfaceBuilderConfig(Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());
    public static final SurfaceBuilderConfig configStone = new SurfaceBuilderConfig(Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());

    VolcanoIslandBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(
                        new VolcanoSurfaceBuilder(SurfaceBuilderConfig::func_215455_a),
                        new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState())))
                .precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.NONE)
                .depth(4.5F)
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
