package com.codeka.castawayterrain.world;

import com.codeka.castawayterrain.CastawayConfig;
import com.codeka.castawayterrain.biome.*;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
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

  public final static Set<Biome> ALL_BIOMES;

  private final static ImmutableMap<Temperature, Biome[]> BIOMES_BY_DEPTH = ImmutableMap.<Temperature, Biome[]>builder()
      .put(Temperature.FROZEN, new Biome[]{Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_OCEAN, Biomes.SNOWY_BEACH})
      .put(Temperature.COLD, new Biome[]{Biomes.DEEP_COLD_OCEAN, Biomes.COLD_OCEAN})
      .put(Temperature.NORMAL, new Biome[]{Biomes.DEEP_OCEAN, Biomes.OCEAN})
      .put(Temperature.LUKEWARM, new Biome[]{Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.BEACH})
      .put(Temperature.WARM, new Biome[]{Biomes.DEEP_WARM_OCEAN, Biomes.WARM_OCEAN})
      .build();

  static {
    ALL_BIOMES = new HashSet<>();
    ALL_BIOMES.addAll(ModBiomes.all());
    for (Map.Entry<Temperature, Biome[]> entry : BIOMES_BY_DEPTH.entrySet()) {
      ALL_BIOMES.addAll(Arrays.asList(entry.getValue()));
    }

    // Remove all underground ores from all our biomes. TODO: how to only do this for our world type?
    if (CastawayConfig.instance.disable_ore_gen) {
      for (Biome b : ALL_BIOMES) {
        b.getFeaturesForStep(UNDERGROUND_ORES).clear();
      }
    }
  }

  public CastawayBiomeSource(long seed, Set<Biome> set) {
    super(set);
    noise = new SimplexNoiseSampler(new Random(seed));
  }

  private Biome getBiome(int x, int y) {
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
      if (CastawayConfig.instance.island_types.size() > 1) {
        int n = Volcano.getVolcanoRandom(noise, x, y).nextInt(CastawayConfig.instance.island_types.size());
        return getBiomeByConfigName(CastawayConfig.instance.island_types.get(n));
      } else if (CastawayConfig.instance.island_types.size() == 1) {
        return getBiomeByConfigName(CastawayConfig.instance.island_types.get(0));
      } else {
        return getBiomeByConfigName("jungle");
      }
    } else if (distanceToCenter < CastawayConfig.instance.volcano_size * 0.5) {
      // Surround the volcano by beach.
      return ModBiomes.VOLCANO_BEACH;
    } else if (distanceToCenter < CastawayConfig.instance.volcano_size) {
      // And the beach by "shallow warm ocean".
      return ModBiomes.SHALLOW_WARN_OCEAN;
    }

    Biome[] biomes = BIOMES_BY_DEPTH.get(temp);
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

  private static Biome getBiomeByConfigName(String name) {
    switch (name.toLowerCase()) {
      case "bamboo":
        return ModBiomes.BAMBOO_VOLCANO;
      case "forest":
        return ModBiomes.FOREST_VOLCANO;
      default:
        return ModBiomes.JUNGLE_VOLCANO;
    }
  }

  private Biome[] sampleBiomes(int x, int z, int width, int length, boolean cacheFlag) {
    Biome[] biomes = new Biome[width * length];
    for (int dx = 0; dx < width; dx++) {
      for (int dy = 0; dy < length; dy++) {
        biomes[dy * width + dx] = getBiome(x + dx, z + dy);
      }
    }
    return biomes;
  }

  @Override
  public Biome getBiomeForNoiseGen(int y, int z, int x) {
    return getBiome(x, y);
  }

  @Override
  public boolean hasStructureFeature(StructureFeature<?> structure) {
    return structureFeatures.computeIfAbsent(structure, (s) -> {
      for (Biome biome : ALL_BIOMES) {
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
      for (Biome biome : ALL_BIOMES) {
        topMaterials.add(biome.getSurfaceConfig().getTopMaterial());
      }
    }

    return topMaterials;
  }
}
