package com.codeka.castawayterrain.biome;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class VolcanoIslandSurfaceBuilderConfig extends SurfaceBuilderConfig {
    private Map<Long, SimplexNoiseGenerator> noise = new TreeMap<>();

    public VolcanoIslandSurfaceBuilderConfig() {
        super(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState());
    }

    public synchronized SimplexNoiseGenerator getNoiseGenerator(long seed) {
        SimplexNoiseGenerator noiseGenerator = noise.get(seed);
        if (noiseGenerator == null) {
            noiseGenerator = new SimplexNoiseGenerator(new Random(seed));
            noise.put(seed, noiseGenerator);
        }
        return noiseGenerator;
    }
}
