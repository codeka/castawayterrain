package com.codeka.castawayterrain.biome;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.MountainSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class VolcanoIslandBiome extends Biome {

    public static final VolcanoIslandBiome BIOME = new VolcanoIslandBiome();

    public static final SurfaceBuilderConfig configDirt = new SurfaceBuilderConfig(Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());
    public static final SurfaceBuilderConfig configStone = new SurfaceBuilderConfig(Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());

    VolcanoIslandBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(
                        new VolcanoSurfaceBuilder(SurfaceBuilderConfig::func_215455_a),
                        new SurfaceBuilderConfig(Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState())))
                .precipitation(Biome.RainType.RAIN)
                .category(Category.EXTREME_HILLS)
                .depth(1.5f)
                .scale(1.0f)
                .temperature(0.95f)
                .downfall(0.3f)
                .waterColor(4566514)
                .waterFogColor(267827)
                .parent(null));
    }
}
