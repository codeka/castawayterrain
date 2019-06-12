package com.codeka.castawayterrain.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SingleRandomFeature;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

/**
 * This is a cut'n'paste of {@link net.minecraft.world.biome.WarmOceanBiome}, but shallower.
 */
public class ShallowWarmOceanBiome extends Biome {
    public static final ShallowWarmOceanBiome BIOME = new ShallowWarmOceanBiome();

    public ShallowWarmOceanBiome() {
        super(new Builder()
                .func_222351_a(SurfaceBuilder.field_215396_G, SurfaceBuilder.field_215391_B)
                .precipitation(RainType.RAIN)
                .category(Category.OCEAN
                ).depth(-0.3F)
                .scale(0.03F)
                .temperature(0.5F)
                .downfall(0.5F)
                .waterColor(4445678)
                .waterFogColor(270131)
                .parent(null));
        addStructure(
                Feature.OCEAN_RUIN,
                new OceanRuinConfig(OceanRuinStructure.Type.WARM, 0.3F, 0.9F));
        addStructure(
                Feature.SHIPWRECK,
                new ShipwreckConfig(false));

        DefaultBiomeFeatures.func_222346_b(this);
        DefaultBiomeFeatures.func_222295_c(this);
        DefaultBiomeFeatures.func_222333_d(this);
        DefaultBiomeFeatures.func_222335_f(this);
        DefaultBiomeFeatures.func_222326_g(this);
        DefaultBiomeFeatures.func_222288_h(this);
        DefaultBiomeFeatures.func_222282_l(this);
        DefaultBiomeFeatures.func_222296_u(this);
        DefaultBiomeFeatures.func_222342_U(this);
        DefaultBiomeFeatures.func_222348_W(this);
        DefaultBiomeFeatures.func_222315_Z(this);
        DefaultBiomeFeatures.func_222311_aa(this);
        DefaultBiomeFeatures.func_222337_am(this);

        addFeature(
                GenerationStage.Decoration.VEGETAL_DECORATION,
                func_222280_a(
                        Feature.RANDOM_FEATURE_WITH_CONFIG,
                        new SingleRandomFeature(
                                new Feature[]{Feature.CORAL_TREE, Feature.CORAL_CLAW, Feature.CORAL_MUSHROOM},
                                new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}),
                        Placement.field_215038_x,
                        new TopSolidWithNoiseConfig(20, 400.0D, 0.0D, net.minecraft.world.gen.Heightmap.Type.OCEAN_FLOOR_WG)));
        DefaultBiomeFeatures.func_222309_aj(this);

        addFeature(
                GenerationStage.Decoration.VEGETAL_DECORATION,
                func_222280_a(
                        Feature.SEA_PICKLE,
                        new CountConfig(20),
                        Placement.field_215026_l, new ChanceConfig(16)));
        DefaultBiomeFeatures.func_222297_ap(this);

        addSpawn(
                EntityClassification.WATER_CREATURE,
                new SpawnListEntry(EntityType.SQUID, 10, 4, 4));
        addSpawn(
                EntityClassification.WATER_CREATURE,
                new SpawnListEntry(EntityType.PUFFERFISH, 15, 1, 3));
        addSpawn(
                EntityClassification.WATER_CREATURE,
                new SpawnListEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
        addSpawn(
                EntityClassification.WATER_CREATURE,
                new SpawnListEntry(EntityType.DOLPHIN, 2, 1, 2));
        addSpawn(
                EntityClassification.AMBIENT,
                new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        addSpawn(
                EntityClassification.MONSTER
                , new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        addSpawn(
                EntityClassification.MONSTER,
                new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
    }
}
