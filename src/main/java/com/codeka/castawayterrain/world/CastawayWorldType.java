package com.codeka.castawayterrain.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;

public class CastawayWorldType extends WorldType {
    public CastawayWorldType() {
        super("castaway");
    }

    @Override
    @Nonnull
    public IChunkGenerator getChunkGenerator(@Nonnull World world, String generatorSettings) {
        return new CastawayChunkGenerator(world);
    }

    @Override
    @Nonnull
    public BiomeProvider getBiomeProvider(World world) {
        return new CastawayBiomeProvider(world.getSeed());
    }
}
