package com.codeka.castawayterrain.biome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class VolcanoIslandSurfaceBuilder extends DefaultSurfaceBuilder {
    private VolcanoIslandSurfaceBuilderConfig config;

    public VolcanoIslandSurfaceBuilder(VolcanoIslandSurfaceBuilderConfig config) {
        super(dynamic -> config);
        this.config = config;
    }

    @Override
    public void buildSurface(
            @Nonnull Random random, @Nonnull IChunk chunkIn, @Nonnull Biome biomeIn, int x, int z, int startHeight,
            double noise, @Nonnull BlockState defaultBlock, @Nonnull BlockState defaultFluid, int seaLevel, long seed,
            @Nonnull SurfaceBuilderConfig config) {

        buildSurface1(random, chunkIn, x, z, startHeight, noise, seaLevel);

       // super.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
    }

    private void buildSurface1(
            Random random, IChunk chunkIn, int x, int z, int startHeight, double noise, int seaLevel) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        int chunkX = x & 15;
        int chunkZ = z & 15;

        blockPos.setPos(chunkX, startHeight, chunkZ);
        if (noise < 0.2) {
            // lava part
            for(int y = startHeight; y > seaLevel; y--) {
                blockPos.setPos(chunkX, y, chunkZ);
                chunkIn.setBlockState(blockPos, Blocks.LAVA.getDefaultState(), false);
            }
            for(int y = seaLevel; y >= 0; y--) {
                blockPos.setPos(chunkX, y, chunkZ);
                chunkIn.setBlockState(blockPos, Blocks.STONE.getDefaultState(), false);
            }
        } else if (noise < 0.7) {
            // mountain part
            for(int y = startHeight; y >= 0; y--) {
                blockPos.setPos(chunkX, y, chunkZ);
                chunkIn.setBlockState(blockPos, Blocks.STONE.getDefaultState(), false);
            }
        } else {
            // grassy part
            chunkIn.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState(), false);
            for(int y = startHeight - 1; y >= startHeight - 5; y--) {
                blockPos.setPos(chunkX, y, chunkZ);
                chunkIn.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), false);
            }
            for(int y = startHeight - 5; y >= 0; y--) {
                blockPos.setPos(chunkX, y, chunkZ);
                chunkIn.setBlockState(blockPos, Blocks.STONE.getDefaultState(), false);
            }
        }
    }

}