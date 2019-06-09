package com.codeka.castawayterrain;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGenerator;

public class CastawayWorldType extends WorldType {
    public CastawayWorldType() {
        super("castaway");
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator(World world) {
        return new CastawayChunkGenerator(world);
    }
}
