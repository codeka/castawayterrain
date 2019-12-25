package com.codeka.castawayterrain.biome;

import com.codeka.castawayterrain.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import java.util.Random;

public class VolcanoIslandSurfaceBuilder extends SurfaceBuilder<VolcanoIslandSurfaceBuilderConfig> {
  private VolcanoIslandSurfaceBuilderConfig config;

  public VolcanoIslandSurfaceBuilder(VolcanoIslandSurfaceBuilderConfig config) {
    super(dynamic -> config);
    this.config = config;
  }

  @Override
  public void generate(
      Random random, Chunk chunkIn, Biome biomeIn, int x, int z, int startHeight,
      double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed,
      VolcanoIslandSurfaceBuilderConfig config) {
    BlockPos.Mutable blockPos = new BlockPos.Mutable();
    int chunkX = x & 15;
    int chunkZ = z & 15;

    blockPos.set(chunkX, startHeight, chunkZ);
    if (noise < 0.2) {
      // lava part, all the way down to bedrock
      for (int y = startHeight; y > 0; y--) {
        blockPos.set(chunkX, y, chunkZ);
        if (noise < 0.05 && y == startHeight - 1) {
          chunkIn.setBlockState(blockPos, ModBlocks.VOLCANO_SMOKER_BLOCK.getDefaultState(), false);
          BlockEntity be = ModBlocks.VOLCANO_SMOKER_BLOCK.createBlockEntity(null);
          if (be != null) {
            blockPos.set(x, y, z);
            chunkIn.setBlockEntity(blockPos, be);
          }
        } else {
          chunkIn.setBlockState(blockPos, Blocks.LAVA.getDefaultState(), false);
        }
      }
    } else if (noise < 0.7) {
      // mountain part
      for (int y = startHeight; y > 0; y--) {
        blockPos.set(chunkX, y, chunkZ);
        chunkIn.setBlockState(blockPos, Blocks.STONE.getDefaultState(), false);
      }
    } else {
      // grassy part
      chunkIn.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState(), false);
      for (int y = startHeight - 1; y > startHeight - 5; y--) {
        blockPos.set(chunkX, y, chunkZ);
        chunkIn.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), false);
      }
      for (int y = startHeight - 5; y > 0; y--) {
        blockPos.set(chunkX, y, chunkZ);
        chunkIn.setBlockState(blockPos, Blocks.STONE.getDefaultState(), false);
      }
    }
    blockPos.set(chunkX, 0, chunkZ);
    chunkIn.setBlockState(blockPos, Blocks.BEDROCK.getDefaultState(), false);
  }
}