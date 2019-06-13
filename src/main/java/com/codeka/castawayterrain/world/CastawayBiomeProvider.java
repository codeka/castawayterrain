package com.codeka.castawayterrain.world;

import com.codeka.castawayterrain.biome.ShallowOceanBiome;
import com.codeka.castawayterrain.biome.Volcano;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.google.common.collect.ImmutableMap;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class CastawayBiomeProvider extends BiomeProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final NoiseGeneratorSimplex noise;

    private final double TEMPERATURE_SCALE = 700;
    private final double DEPTH_SCALE = 200;

    private final double NOISE_SCALE = 10;
    private final double NOISE_VALUE = 0.05;

    private enum Temperature {
        FROZEN,
        NORMAL,
    }

    // TODO: this duplicates the list in biomesByDepth, we could calculate it from the other.
    private final Biome[] allBiomes = new Biome[]{
            Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.BEACH, Biomes.DEEP_OCEAN, VolcanoIslandBiome.BIOME,
            ShallowOceanBiome.BIOME,
    };

    private final ImmutableMap<Temperature, Biome[]> biomesByDepth = ImmutableMap.<Temperature, Biome[]>builder()
            .put(Temperature.FROZEN, new Biome[]{Biomes.FROZEN_OCEAN})
            .put(Temperature.NORMAL, new Biome[]{Biomes.DEEP_OCEAN, Biomes.OCEAN})
            .build();

    public CastawayBiomeProvider(long seed) {
        noise = new NoiseGeneratorSimplex(new Random(seed));

        // Remove all underground ores from all our biomes. TODO: how to only do this for our world type?
//        for (Biome b : allBiomes) {
//            b.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).clear();
//        }
    }

    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
        return true;
    }

    @Override
    public Biome getBiome(BlockPos blockPos) {
        int x = blockPos.getX();
        int y = blockPos.getY();

        double t = (1.0 + noise.getValue((double) x / TEMPERATURE_SCALE, (double) y / TEMPERATURE_SCALE)) * 0.5;
        double d = (1.0 + noise.getValue((double) x / DEPTH_SCALE, (double) y / DEPTH_SCALE)) * 0.5;
       // double v = (1.0 + noise.getValue((double) x / VOLCANO_SCALE, (double) y / VOLCANO_SCALE)) * 0.5;
        double rand = noise.getValue((double) x / NOISE_SCALE, (double) y / NOISE_SCALE);
        rand = 1.0 + (rand * NOISE_VALUE);

        Temperature temp;
        if ((t * rand) < 0.1) {
            temp = Temperature.FROZEN;
        } else {
            temp = Temperature.NORMAL;
        }

        // We're in the radius of a volcano. The Volcano islands start off being perfectly circular, but we add
        // a bit of noise to break up the shoreline, etc.
        double distanceToCenter = Volcano.distanceToCenter(noise, x, y);
        if (distanceToCenter < Volcano.VOLCANO_SIZE * 0.3) {
            return VolcanoIslandBiome.BIOME;
        } else if (distanceToCenter < Volcano.VOLCANO_SIZE * 0.5) {
            // Surround the volcano by beach.
            return Biomes.BEACH;
        } else if (distanceToCenter < Volcano.VOLCANO_SIZE) {
            return ShallowOceanBiome.BIOME;
        }

        Biome[] biomes = biomesByDepth.get(temp);
        if (biomes.length > 2) {
            if (d < 0.1) {
                // beaches are rare
                return biomes[2];
            }
        }
        if (d < 0.5 && biomes.length > 1) {
            return biomes[1];
        } else {
            return biomes[0];
        }
    }

    @Override
    public Biome[] getBiomesForGeneration(@Nullable Biome[] biomes, int x, int z, int width, int length) {
        if (biomes == null || biomes.length < width * length) {
            biomes = new Biome[width * length];
        }

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < length; dy++) {
                blockPos.setPos((x + dx)*4, 0, (z + dy)*4);
                biomes[dy * width + dx] = getBiome(blockPos);
            }
        }
        return biomes;
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] biomes, int x, int z, int width, int length, boolean cacheFlag) {
        if (biomes == null || biomes.length < width * length) {
            biomes = new Biome[width * length];
        }

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < length; dy++) {
                blockPos.setPos(x + dx, 0, z + dy);
                biomes[dy * width + dx] = getBiome(blockPos);
            }
        }
        return biomes;
    }

    @Nullable
    @Override
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Biome[] abiome = getBiomes(null, i, j, i1, j1, true);
        BlockPos blockpos = null;
        int k1 = 0;

        for(int l1 = 0; l1 < i1 * j1; ++l1) {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            if (biomes.contains(abiome[l1])) {
                if (blockpos == null || random.nextInt(k1 + 1) == 0) {
                    blockpos = new BlockPos(i2, 0, j2);
                }

                ++k1;
            }
        }

        return blockpos;
    }
}
