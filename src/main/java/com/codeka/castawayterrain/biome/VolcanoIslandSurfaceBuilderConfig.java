package com.codeka.castawayterrain.biome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class VolcanoIslandSurfaceBuilderConfig implements SurfaceConfig {
    private Map<Long, SimplexNoiseSampler> noise = new TreeMap<>();

    public synchronized SimplexNoiseSampler getNoiseGenerator(long seed) {
        SimplexNoiseSampler noiseGenerator = noise.get(seed);
        if (noiseGenerator == null) {
            noiseGenerator = new SimplexNoiseSampler(new Random(seed));
            noise.put(seed, noiseGenerator);
        }
        return noiseGenerator;
    }

    @Override
    public BlockState getTopMaterial() {
        return Blocks.GRASS_BLOCK.getDefaultState();
    }

    @Override
    public BlockState getUnderMaterial() {
        return Blocks.DIRT.getDefaultState();
    }
}
