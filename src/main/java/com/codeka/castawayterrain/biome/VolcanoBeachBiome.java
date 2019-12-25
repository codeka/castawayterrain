package com.codeka.castawayterrain.biome;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class VolcanoBeachBiome extends Biome {
  public VolcanoBeachBiome() {
    super(new Biome.Settings()
        .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_CONFIG)
        .precipitation(Precipitation.RAIN)
        .category(Biome.Category.BEACH)
        .depth(0.0F)
        .scale(0.025F)
        .temperature(0.8F)
        .downfall(0.4F)
        .waterColor(4445678)
        .waterFogColor(270131)
        .parent(null));
//    addStructureFeature(
//        Feature.MINESHAFT, new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL));
//    addStructureFeature(Feature.BURIED_TREASURE, new BuriedTreasureFeatureConfig(0.01F));
//    addStructureFeature(Feature.SHIPWRECK, new ShipwreckFeatureConfig(true));

    DefaultBiomeFeatures.addDefaultStructures(this);
    DefaultBiomeFeatures.addDefaultLakes(this);
    DefaultBiomeFeatures.addDefaultFlowers(this);
    DefaultBiomeFeatures.addDefaultGrass(this);
    DefaultBiomeFeatures.addDefaultMushrooms(this);
    DefaultBiomeFeatures.addDefaultVegetation(this);
    DefaultBiomeFeatures.addSprings(this);

    addSpawn(EntityCategory.CREATURE, new SpawnEntry(EntityType.TURTLE, 5, 2, 5));
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
