package com.codeka.castawayterrain.biome;

import net.minecraft.world.gen.NoiseGeneratorSimplex;

public class Volcano {
    // Scale is the size of the area in which a volcano will spawn. Larger values = fewer volcanos.
    public final static int VOLCANO_SCALE = 1000;

    // Size is the size of the volcano island itself. Large value = large island.
    public final static int VOLCANO_SIZE = 128;

    private final static double VOLCANO_NOISE_SCALE = 10;
    private final static double VOLCANO_NOISE_VALUE = 0.05;

    /**
     * Calculates the distance of the given (x,y) coords to the nearest volcano. You must always use the same exact
     * instance of {@link NoiseGeneratorSimplex}.
     */
    public static double distanceToCenter(NoiseGeneratorSimplex noise, int x, int y) {
        double rand = noise.getValue((double) x / VOLCANO_NOISE_SCALE, (double) y / VOLCANO_NOISE_SCALE);
        rand = 1.0 + (rand * VOLCANO_NOISE_VALUE);

        double volcanoCenterX = Math.round((double) x / VOLCANO_SCALE) * VOLCANO_SCALE;
        double volcanoCenterY = Math.round((double) y / VOLCANO_SCALE) * VOLCANO_SCALE;

        // Randomize the center of the volcano a bit so it's not exactly at every 1000x block.
        // Except for the one at 0,0 -- that's spawn
        if (volcanoCenterX != 0 || volcanoCenterY != 0) {
            double randX = noise.getValue(volcanoCenterX, volcanoCenterY);
            double randY = noise.getValue(volcanoCenterX + 10, volcanoCenterY - 10);
            volcanoCenterX += randX * VOLCANO_SIZE * 6;
            volcanoCenterY += randY * VOLCANO_SIZE * 6;
        }

        double distanceToCenter =
                Math.sqrt((volcanoCenterX - x) * (volcanoCenterX - x) + (volcanoCenterY - y) * (volcanoCenterY - y));
        distanceToCenter *= rand;

        return distanceToCenter;
    }
}
