package com.codeka.castawayterrain.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.ChunkGenerator;

public class CastawayWorldType extends WorldType {
    public CastawayWorldType() {
        super("castaway");
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator(World world) {
        if (world.getDimension().isSurfaceWorld()) {
            return new CastawayChunkGenerator(world);
        }

        return super.createChunkGenerator(world);
    }
}
