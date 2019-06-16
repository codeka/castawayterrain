package com.codeka.castawayterrain.world;

import com.codeka.castawayterrain.CastawayConfig;
import com.codeka.castawayterrain.biome.ShallowWarmOceanBiome;
import com.codeka.castawayterrain.biome.Volcano;
import com.codeka.castawayterrain.biome.VolcanoBeachBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static net.minecraft.world.gen.GenerationStep.Feature.UNDERGROUND_ORES;

public class CastawayBiomeSource extends BiomeSource {
    private static final Logger L = LogManager.getLogger();
    private final SimplexNoiseSampler noise;

    // These parameters are used to generate the ocean biomes, they're not configurable. I could make them
    // configurable, but I think they're not that important and it might just be too confusing -- the most
    // important thing to configure is the island generation.
    private static final double TEMPERATURE_SCALE = 700;
    private static final double DEPTH_SCALE = 200;
    private static final double NOISE_SCALE = 10;
    private static final double NOISE_VALUE = 0.05;

    private enum Temperature {
        FROZEN,
        COLD,
        NORMAL,
        LUKEWARM,
        WARM,
    }

    private final Set<Biome> allBiomes;

    private final ImmutableMap<Temperature, Biome[]> biomesByDepth = ImmutableMap.<Temperature, Biome[]>builder()
            .put(Temperature.FROZEN, new Biome[] { Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_OCEAN, Biomes.SNOWY_BEACH })
            .put(Temperature.COLD, new Biome[] { Biomes.DEEP_COLD_OCEAN, Biomes.COLD_OCEAN})
            .put(Temperature.NORMAL, new Biome[] { Biomes.DEEP_OCEAN, Biomes.OCEAN})
            .put(Temperature.LUKEWARM, new Biome[] { Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.BEACH})
            .put(Temperature.WARM, new Biome[] { Biomes.DEEP_WARM_OCEAN, Biomes.WARM_OCEAN})
            .build();

    public CastawayBiomeSource(long seed) {
        noise = new SimplexNoiseSampler(new Random(seed));

        allBiomes = new HashSet<>();
        allBiomes.addAll(Arrays.asList(VolcanoIslandBiome.BIOME, VolcanoBeachBiome.BIOME, ShallowWarmOceanBiome.BIOME));
        for (Map.Entry<Temperature, Biome[]> entry : biomesByDepth.entrySet()) {
            allBiomes.addAll(Arrays.asList(entry.getValue()));
        }

        // Remove all underground ores from all our biomes. TODO: how to only do this for our world type?
        if (CastawayConfig.instance.disable_ore_gen) {
            for (Biome b : allBiomes) {
                b.getFeaturesForStep(UNDERGROUND_ORES).clear();
            }
        }
    }

    @Override
    public Biome getBiome(int x, int y) {
        double t = (1.0 + noise.sample((double) x / TEMPERATURE_SCALE, (double) y / TEMPERATURE_SCALE)) * 0.5;
        double d = (1.0 + noise.sample((double) x / DEPTH_SCALE, (double) y / DEPTH_SCALE)) * 0.5;
        double rand = noise.sample((double) x / NOISE_SCALE, (double) y / NOISE_SCALE);
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

        // We're in the radius of a volcano.
        double distanceToCenter = Volcano.distanceToCenter(noise, x, y);
        if (distanceToCenter < CastawayConfig.instance.volcano_size * 0.3) {
            // The actual volcano part of the island (trees + mountain)
            return VolcanoIslandBiome.BIOME;
        } else if (distanceToCenter < CastawayConfig.instance.volcano_size * 0.5) {
            // Surround the volcano by beach.
            return VolcanoBeachBiome.BIOME;
        } else if (distanceToCenter < CastawayConfig.instance.volcano_size) {
            // And the beach by "shallow warm ocean".
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
    public Biome[] sampleBiomes(int x, int z, int width, int length, boolean cacheFlag) {
        Biome[] biomes = new Biome[width * length];
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < length; dy++) {
                biomes[dy * width + dx] = getBiome(x + dx, z+dy);
            }
        }
        return biomes;
    }

    @Override
    public Set<Biome> getBiomesInArea(int centerX, int centerZ, int sideLength) {
        int i = centerX - sideLength >> 2;
        int j = centerZ - sideLength >> 2;
        int k = centerX + sideLength >> 2;
        int l = centerZ + sideLength >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Set<Biome> set = Sets.newHashSet();
        Collections.addAll(set, sampleBiomes(i, j, i1, j1, true));
        return set;
    }

    @Override
    public BlockPos locateBiome(int x, int z, int range, List<Biome> biomes, Random random) {
        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Biome[] abiome = sampleBiomes(i, j, i1, j1, true);
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
    public boolean hasStructureFeature(StructureFeature<?> structure) {
        return structureFeatures.computeIfAbsent(structure, (s) -> {
            for(Biome biome : allBiomes) {
                if (biome.hasStructureFeature(s)) {
                    return true;
                }
            }

            return false;
        });
    }

    @Override
    public Set<BlockState> getTopMaterials() {
        if (topMaterials.isEmpty()) {
            for(Biome biome : allBiomes) {
                topMaterials.add(biome.getSurfaceConfig().getTopMaterial());
            }
        }

        return topMaterials;
    }
}
