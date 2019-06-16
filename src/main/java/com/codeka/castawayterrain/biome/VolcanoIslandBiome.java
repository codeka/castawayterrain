package com.codeka.castawayterrain.biome;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;

public class VolcanoIslandBiome extends Biome {

    public static final VolcanoIslandBiome BIOME = new VolcanoIslandBiome();

    private static final VolcanoIslandSurfaceBuilderConfig SURFACE_BUILDER_CONFIG = new VolcanoIslandSurfaceBuilderConfig();

    VolcanoIslandBiome() {
        super(new Biome.Settings()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(
                        new VolcanoIslandSurfaceBuilder(SURFACE_BUILDER_CONFIG), new VolcanoIslandSurfaceBuilderConfig()))
                .precipitation(Precipitation.RAIN)
                .category(Category.EXTREME_HILLS)
                // We keep depth & scale low so the OverworldChunkGenerator thinks the island is at sea level and
                // generates the surrounding terrain correctly.
                .depth(0.0f)
                .scale(0.2f)
                .temperature(0.95f)
                .downfall(0.3f)
                .waterColor(4445678)
                .waterFogColor(270131)
                .parent(null));

        DefaultBiomeFeatures.addForestFlowers(this);
        DefaultBiomeFeatures.addForestTrees(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addForestGrass(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);

        addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.PIG, 10, 4, 4));
        addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.COW, 8, 4, 4));
        addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.WOLF, 5, 4, 4));
        addSpawn(EntityCategory.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
