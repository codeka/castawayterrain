package com.codeka.castawayterrain.world;

import com.codeka.castawayterrain.biome.ShallowWarmOceanBiome;
import com.codeka.castawayterrain.biome.Volcano;
import com.codeka.castawayterrain.biome.VolcanoBeachBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.WarmOceanBiome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public class CastawayBiomeProvider extends BiomeProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final SimplexNoiseGenerator noise;

    private final double TEMPERATURE_SCALE = 700;
    private final double DEPTH_SCALE = 200;

    private final double NOISE_SCALE = 10;
    private final double NOISE_VALUE = 0.05;

    private enum Temperature {
        FROZEN,
        COLD,
        NORMAL,
        LUKEWARM,
        WARM,
    }

    // TODO: this duplicates the list in biomesByDepth, we could calculate it from the other.
    private final Biome[] allBiomes = new Biome[]{
            Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.BEACH, Biomes.DEEP_OCEAN, Biomes.SNOWY_BEACH, Biomes.WARM_OCEAN,
            Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN,
            Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, VolcanoIslandBiome.BIOME, VolcanoBeachBiome.BIOME,
            ShallowWarmOceanBiome.BIOME,
    };

    private final ImmutableMap<Temperature, Biome[]> biomesByDepth = ImmutableMap.<Temperature, Biome[]>builder()
            .put(Temperature.FROZEN, new Biome[] { Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_OCEAN, Biomes.SNOWY_BEACH })
            .put(Temperature.COLD, new Biome[] { Biomes.DEEP_COLD_OCEAN, Biomes.COLD_OCEAN})
            .put(Temperature.NORMAL, new Biome[] { Biomes.DEEP_OCEAN, Biomes.OCEAN})
            .put(Temperature.LUKEWARM, new Biome[] { Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.BEACH})
            .put(Temperature.WARM, new Biome[] { Biomes.DEEP_WARM_OCEAN, Biomes.WARM_OCEAN})
            .build();

    public CastawayBiomeProvider(long seed) {
        noise = new SimplexNoiseGenerator(new Random(seed));

        // Remove all underground ores from all our biomes. TODO: how to only do this for our world type?
        for (Biome b : allBiomes) {
            b.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).clear();
        }
    }

    @Override
    public Biome getBiome(int x, int y) {
        double t = (1.0 + noise.getValue((double) x / TEMPERATURE_SCALE, (double) y / TEMPERATURE_SCALE)) * 0.5;
        double d = (1.0 + noise.getValue((double) x / DEPTH_SCALE, (double) y / DEPTH_SCALE)) * 0.5;
       // double v = (1.0 + noise.getValue((double) x / VOLCANO_SCALE, (double) y / VOLCANO_SCALE)) * 0.5;
        double rand = noise.getValue((double) x / NOISE_SCALE, (double) y / NOISE_SCALE);
        rand = 1.0 + (rand * NOISE_VALUE);

        Temperature temp;
        if ((t * rand) < 0.1) {
            temp = Temperature.FROZEN;
        } else if (t < 0.2) {
            temp = Temperature.COLD;
        } else if (t < 0.5) {
            temp = Temperature.NORMAL;
        } else if (t < 0.7) {
            temp = Temperature.LUKEWARM;
        } else {
            temp = Temperature.WARM;
        }

        // We're in the radius of a volcano. The Volcano islands start off being perfectly circular, but we add
        // a bit of noise to break up the shoreline, etc.
        double distanceToCenter = Volcano.distanceToCenter(noise, x, y);
        if (distanceToCenter < Volcano.VOLCANO_SIZE * 0.3) {
            return VolcanoIslandBiome.BIOME;
        } else if (distanceToCenter < Volcano.VOLCANO_SIZE * 0.5) {
            // Surround the volcano by beach.
            return VolcanoBeachBiome.BIOME;
        } else if (distanceToCenter < Volcano.VOLCANO_SIZE) {
            return ShallowWarmOceanBiome.BIOME;
        }

        Biome[] biomes = biomesByDepth.get(temp);
        if (biomes.length > 2) {
            if (d < 0.1) {
                // beaches are rare
                return biomes[2];
            }
        }
        if (d < 0.5) {
            return biomes[1];
        } else {
            return biomes[0];
        }
    }

    @Override
    public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
        Biome[] biomes = new Biome[width * length];
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < length; dy++) {
                biomes[dy * width + dx] = getBiome(x + dx, z+dy);
            }
        }
        return biomes;
    }

    @Override
    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
        int i = centerX - sideLength >> 2;
        int j = centerZ - sideLength >> 2;
        int k = centerX + sideLength >> 2;
        int l = centerZ + sideLength >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Set<Biome> set = Sets.newHashSet();
        Collections.addAll(set, getBiomes(i, j, i1, j1, true));
        return set;
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
        Biome[] abiome = getBiomes(i, j, i1, j1, true);
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

    @Override
    public boolean hasStructure(Structure<?> structure) {
        return this.hasStructureCache.computeIfAbsent(structure, (s) -> {
            for(Biome biome : allBiomes) {
                if (biome.hasStructure(s)) {
                    return true;
                }
            }

            return false;
        });
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for(Biome biome : allBiomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTopMaterial());
            }
        }

        return this.topBlocksCache;
    }
}
