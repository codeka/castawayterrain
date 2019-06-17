package com.codeka.castawayterrain.biome;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.TopSolidHeightmapNoiseBiasedDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

/**
 * This is a cut'n'paste of {@link net.minecraft.world.biome.OceanWarmBiome}, but shallower.
 */
public class ShallowWarmOceanBiome extends Biome {
    public ShallowWarmOceanBiome() {
        super(new Settings()
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_SAND_UNDERWATER_CONFIG)
                .precipitation(Precipitation.RAIN)
                .category(Category.OCEAN
                ).depth(-0.3F)
                .scale(0.03F)
                .temperature(0.5F)
                .downfall(0.5F)
                .waterColor(4445678)
                .waterFogColor(270131)
                .parent(null));
        addStructureFeature(
                Feature.OCEAN_RUIN,
                new OceanRuinFeatureConfig(OceanRuinFeature.BiomeType.WARM, 0.3F, 0.9F));
        addStructureFeature(
                Feature.SHIPWRECK,
                new ShipwreckFeatureConfig(false));

        DefaultBiomeFeatures.addOceanCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addWaterBiomeOakTrees(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);

        addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                configureFeature(
                        Feature.SIMPLE_RANDOM_SELECTOR,
                        new SimpleRandomFeatureConfig(
                                new Feature[]{Feature.CORAL_TREE, Feature.CORAL_CLAW, Feature.CORAL_MUSHROOM},
                                new FeatureConfig[]{
                                        FeatureConfig.DEFAULT, FeatureConfig.DEFAULT, FeatureConfig.DEFAULT}),
                        Decorator.TOP_SOLID_HEIGHTMAP_NOISE_BIASED,
                        new TopSolidHeightmapNoiseBiasedDecoratorConfig(
                                20, 400.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG)));
        DefaultBiomeFeatures.addSeagrass(this);
        addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                configureFeature(
                        Feature.SEA_PICKLE,
                        new SeaPickleFeatureConfig(20),
                        Decorator.CHANCE_TOP_SOLID_HEIGHTMAP,
                        new ChanceDecoratorConfig(16)));
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        addSpawn(EntityCategory.WATER_CREATURE, new SpawnEntry(EntityType.SQUID, 10, 4, 4));
        addSpawn(EntityCategory.WATER_CREATURE, new SpawnEntry(EntityType.PUFFERFISH, 15, 1, 3));
        addSpawn(EntityCategory.WATER_CREATURE, new SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
        addSpawn(EntityCategory.WATER_CREATURE, new SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
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
