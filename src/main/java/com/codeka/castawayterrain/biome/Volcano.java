package com.codeka.castawayterrain.biome;

import com.codeka.castawayterrain.CastawayConfig;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Volcano {
    private static final Logger L = LogManager.getLogger();

    private final static double VOLCANO_NOISE_SCALE = 10;
    private final static double VOLCANO_ISLAND_NOISE_SCALE = 100;
    private final static double VOLCANO_NOISE_VALUE = 0.05;

    public static class VolcanoCenter {
        public long x;
        public long y;

        public VolcanoCenter(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static VolcanoCenter getCenter(SimplexNoiseSampler noise, int x, int y) {
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

        return new VolcanoCenter(Math.round(volcanoCenterX), Math.round(volcanoCenterY));
    }

    /**
     * Calculates the distance of the given (x,y) coords to the nearest volcano. You must always use the same exact
     * instance of {@link SimplexNoiseSampler}.
     */
    public static double distanceToCenter(SimplexNoiseSampler noise, int x, int y) {
        VolcanoCenter center = getCenter(noise, x, y);

        double distanceToCenter =
                Math.sqrt((center.x - x) * (center.x - x) + (center.y - y) * (center.y - y));
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
        VolcanoCenter center = getCenter(noise, x, y);
        return x == center.x && y == center.y;
    }

    public static ChunkRandom getVolcanoRandom(SimplexNoiseSampler noise, int x, int y) {
        VolcanoCenter center = getCenter(noise, x, y);
        ChunkRandom rand = new ChunkRandom();
        rand.setSeed((int)(center.x / 16), (int)(center.y / 16));
        return rand;
    }
}
