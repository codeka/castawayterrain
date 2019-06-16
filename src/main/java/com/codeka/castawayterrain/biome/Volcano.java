package com.codeka.castawayterrain.biome;

import com.codeka.castawayterrain.CastawayConfig;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Volcano {
    private static final Logger L = LogManager.getLogger();

    private final static double VOLCANO_NOISE_SCALE = 10;
    private final static double VOLCANO_ISLAND_NOISE_SCALE = 100;
    private final static double VOLCANO_NOISE_VALUE = 0.05;

    /**
     * Calculates the distance of the given (x,y) coords to the nearest volcano. You must always use the same exact
     * instance of {@link SimplexNoiseSampler}.
     */
    public static double distanceToCenter(SimplexNoiseSampler noise, int x, int y) {
        double volcanoScale = CastawayConfig.instance.volcano_island_frequency;
        double volcanoCenterX = Math.round((double) x / volcanoScale) * volcanoScale;
        double volcanoCenterY = Math.round((double) y / volcanoScale) * volcanoScale;

        // Randomize the center of the volcano a bit so it's not exactly at every 1000x block.
        // Except for the one at 0,0 -- that's spawn
        if (volcanoCenterX != 0 || volcanoCenterY != 0) {
            double randX = noise.sample(volcanoCenterX, volcanoCenterY);
            double randY = noise.sample(volcanoCenterX + 10, volcanoCenterY - 10);
            volcanoCenterX += randX * CastawayConfig.instance.volcano_size * 6;
            volcanoCenterY += randY * CastawayConfig.instance.volcano_size * 6;
        }

        double distanceToCenter =
                Math.sqrt((volcanoCenterX - x) * (volcanoCenterX - x) + (volcanoCenterY - y) * (volcanoCenterY - y));
        double noiseScale = VOLCANO_NOISE_SCALE;
        if (distanceToCenter > CastawayConfig.instance.volcano_size * 0.75) {
            noiseScale = VOLCANO_ISLAND_NOISE_SCALE;
        }
        double rand = noise.sample((double) x / noiseScale, (double) y / noiseScale);
        rand = 1.0 + (rand * VOLCANO_NOISE_VALUE);
        distanceToCenter *= rand;

        return distanceToCenter;
    }

    public static boolean isCenter(SimplexNoiseSampler noise, int x, int y) {
        double volcanoScale = CastawayConfig.instance.volcano_island_frequency;
        double volcanoCenterX = Math.round((double) x / volcanoScale) * volcanoScale;
        double volcanoCenterY = Math.round((double) y / volcanoScale) * volcanoScale;

        // Randomize the center of the volcano a bit so it's not exactly at every 1000x block.
        // Except for the one at 0,0 -- that's spawn
        if (volcanoCenterX != 0 || volcanoCenterY != 0) {
            double randX = noise.sample(volcanoCenterX, volcanoCenterY);
            double randY = noise.sample(volcanoCenterX + 10, volcanoCenterY - 10);
            volcanoCenterX += randX * CastawayConfig.instance.volcano_size * 6;
            volcanoCenterY += randY * CastawayConfig.instance.volcano_size * 6;
        }

        L.info("isCenter(" + x + ", " + y + ") == " + Math.round(volcanoCenterX) + ", " + Math.round(volcanoCenterY));
        return x == Math.round(volcanoCenterX) && y == Math.round(volcanoCenterY);
    }
}
