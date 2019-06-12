package com.codeka.castawayterrain.world;

import com.codeka.castawayterrain.biome.Volcano;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.SimplexNoiseGenerator;

import java.util.Random;

/**
 * Chunk generator. We're basically {@link OverworldChunkGenerator}, except for how we generate
 * {@link VolcanoIslandBiome}s.
 */
public class CastawayChunkGenerator extends OverworldChunkGenerator {
    private final SimplexNoiseGenerator noise;

    public CastawayChunkGenerator(IWorld world) {
        super(world, new CastawayBiomeProvider(world.getSeed()), new OverworldGenSettings());

        noise = new SimplexNoiseGenerator(new Random(seed));
    }

    /**
     * We override generateSurface to do some special processing for {@link VolcanoIslandBiome} (only). Other biomes
     * just inherit the default processing.
     */
    @Override
    public void generateSurface(IChunk chunk) {
        Biome[] biomes = chunk.getBiomes();
        boolean hasVolcano = false;
        for (Biome biome : biomes) {
            if (biome == VolcanoIslandBiome.BIOME) {
                hasVolcano = true;
                break;
            }
        }
        if (!hasVolcano) {
            // We can skip the whole thing.
            super.generateSurface(chunk);
        }

        generateSurfaceWithVolcanos(chunk);
    }

    /**
     * Called when we have at least one volcano biome in the chunk. We call
     * {@link net.minecraft.world.gen.NoiseChunkGenerator} to generate the initial terrain, then we call our special
     * method on the blocks that are in a volcano biome.
     */
    private void generateSurfaceWithVolcanos(IChunk chunk) {
        super.generateSurface(chunk);

        ChunkPos chunkPos = chunk.getPos();
        int cx = chunkPos.x;
        int cz = chunkPos.z;
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom();
        sharedSeedRandom.setBaseChunkSeed(cx, cz);
        int startX = chunkPos.getXStart();
        int startZ = chunkPos.getZStart();
        Biome[] biomes = chunk.getBiomes();

        for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
                Biome biome = biomes[z * 16 + x];
                if (biome == VolcanoIslandBiome.BIOME) {
                    int px = startX + x;
                    int pz = startZ + z;

                    int startHeight = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, x, z);

                    double distanceToCenter = Volcano.distanceToCenter(noise, px, pz);
                    double noiseValue;
                    if (distanceToCenter < 6) {
                        // It's the lava part
                        startHeight = world.getSeaLevel() + 25;
                        noiseValue = 0.1;
                    } else if (distanceToCenter < Volcano.VOLCANO_SIZE * 0.15) {
                        // It's the mountain part
                        double factor = 1.0 - (distanceToCenter / (Volcano.VOLCANO_SIZE * 0.15));
                        startHeight += (factor * factor * 60) + (noise.getValue(px, pz)) + 5;
                        noiseValue = 0.5;
                    } else {
                        // It's the grassy/foresty part.
                        double factor = 1.0 - (distanceToCenter / (Volcano.VOLCANO_SIZE * 0.3));
                        startHeight += (factor * 5);
                        noiseValue = 1.0;
                    }

                    biome.buildSurface(
                            sharedSeedRandom, chunk, px, pz, startHeight, noiseValue, settings.getDefaultBlock(),
                            settings.getDefaultFluid(), world.getSeaLevel(), world.getSeed());
                }
            }
        }
    }
}
