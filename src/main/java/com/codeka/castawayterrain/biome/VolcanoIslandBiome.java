package com.codeka.castawayterrain.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;


public class VolcanoIslandBiome extends Biome {

    public static final VolcanoIslandBiome BIOME = new VolcanoIslandBiome();

    private static final VolcanoIslandSurfaceBuilderConfig SURFACE_BUILDER_CONFIG = new VolcanoIslandSurfaceBuilderConfig();

    VolcanoIslandBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(new ConfiguredSurfaceBuilder<>(
                        new VolcanoIslandSurfaceBuilder(SURFACE_BUILDER_CONFIG), new VolcanoIslandSurfaceBuilderConfig()))
                .precipitation(Biome.RainType.RAIN)
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

        DefaultBiomeFeatures.func_222338_N(this);

        DefaultBiomeFeatures.func_222302_w(this);
        DefaultBiomeFeatures.func_222342_U(this);
        DefaultBiomeFeatures.func_222298_O(this);
        DefaultBiomeFeatures.func_222315_Z(this);
        DefaultBiomeFeatures.func_222311_aa(this);
        DefaultBiomeFeatures.func_222337_am(this);


        addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
        addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.COW, 8, 4, 4));
        addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.WOLF, 5, 4, 4));
        addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
    }
}
