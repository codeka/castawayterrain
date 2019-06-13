package com.codeka.castawayterrain.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;


public class VolcanoIslandBiome extends Biome {

    public static final VolcanoIslandBiome BIOME = new VolcanoIslandBiome();

    VolcanoIslandBiome() {
        super(new BiomeProperties("volcano_island")
                .setWaterColor(4445678));
    }
/*
    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noise) {
        int cx = x & 15;
        int cz = z & 15;
        int y = 255;
        for (; y > 0; y++) {
            if (chunkPrimerIn.getBlockState(cx, y, cz) != Blocks.AIR.getDefaultState()) {
                break;
            }
        }
        if (noise < 0.2) {
            // lava part
            for(; y > worldIn.getSeaLevel(); y--) {
                chunkPrimerIn.setBlockState(cx, y, cz, Blocks.LAVA.getDefaultState());
            }
            for(; y > 0; y--) {
                chunkPrimerIn.setBlockState(cx, y, cz, Blocks.STONE.getDefaultState());
            }
        } else if (noise < 0.7) {
            // mountain part
            for(; y > 0; y--) {
                chunkPrimerIn.setBlockState(cx, y, cz, Blocks.STONE.getDefaultState());
            }
        } else {
            // grassy part
            chunkPrimerIn.setBlockState(cx, y, cz, Blocks.GRASS.getDefaultState());
            for(int dy = 0; dy < 5; dy++) {
                y--;
                chunkPrimerIn.setBlockState(cx, y, cz, Blocks.DIRT.getDefaultState());
            }
            for(; y > 0; y--) {
                chunkPrimerIn.setBlockState(cx, y, cz, Blocks.STONE.getDefaultState());
            }
        }
        chunkPrimerIn.setBlockState(cx, y, cz, Blocks.BEDROCK.getDefaultState());
    }*/
}
