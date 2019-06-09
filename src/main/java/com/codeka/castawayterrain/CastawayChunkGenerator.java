package com.codeka.castawayterrain;

import net.minecraft.world.IWorld;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

// TODO: don't inherit from OverworldChunkGenerator, we'll probably want our own.
public class CastawayChunkGenerator extends OverworldChunkGenerator {
    public CastawayChunkGenerator(IWorld world) {
        super(world, new CastawayBiomeProvider(world.getSeed()), new OverworldGenSettings());
    }
}
