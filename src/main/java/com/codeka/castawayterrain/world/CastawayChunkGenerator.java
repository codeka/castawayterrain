package com.codeka.castawayterrain.world;

import com.codeka.castawayterrain.biome.Volcano;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Chunk generator. We're basically {@link OverworldChunkGenerator}, except for how we generate
 * {@link VolcanoIslandBiome}s.
 */
public class CastawayChunkGenerator extends OverworldChunkGenerator {
    private static final Logger L = LogManager.getLogger();
    private final SimplexNoiseSampler noise;

    public CastawayChunkGenerator(IWorld world, CastawayChunkGeneratorConfig config) {
        super(world, new CastawayBiomeSource(world.getSeed()), config);
        L.info("CastawayChunkGenerator starting up.");
        noise = new SimplexNoiseSampler(new Random(seed));
    }

    /**
     * We override generateSurface to do some special processing for {@link VolcanoIslandBiome} (only). Other biomes
     * just inherit the default processing.
     */
    @Override
    public void buildSurface(Chunk chunk) {
        Biome[] biomes = chunk.getBiomeArray();
        boolean hasVolcano = false;
        for (Biome biome : biomes) {
            if (biome == VolcanoIslandBiome.BIOME) {
                hasVolcano = true;
                break;
            }
        }
        if (!hasVolcano) {
            // We can skip the whole thing.
            super.buildSurface(chunk);
        }

        generateSurfaceWithVolcanos(chunk);
    }

    /**
     * Called when we have at least one volcano biome in the chunk. We call the super class to build the default terrain
     * the modify it a bit based on our own logic.
     */
    private void generateSurfaceWithVolcanos(Chunk chunk) {
        super.buildSurface(chunk);

        ChunkPos chunkPos = chunk.getPos();
        int cx = chunkPos.x;
        int cz = chunkPos.z;
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setSeed(cx, cz);
        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();
        Biome[] biomes = chunk.getBiomeArray();

        for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
                Biome biome = biomes[z * 16 + x];
                if (biome == VolcanoIslandBiome.BIOME) {
                    int px = startX + x;
                    int pz = startZ + z;

                    int startHeight = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, z);

                    double distanceToCenter = Volcano.distanceToCenter(noise, px, pz);
                    double noiseValue;
                    if (distanceToCenter < 4) {
                        // It's the lava part
                        startHeight = world.getSeaLevel() + 35;
                        if (Volcano.isCenter(noise, px, pz)) {
                            L.info("Adding smoker @ " + px + "," + pz);
                            noiseValue = 0.0;
                        } else {
                            noiseValue = 0.1;
                        }
                    } else if (distanceToCenter < Volcano.VOLCANO_SIZE * 0.15) {
                        // It's the mountain part
                        double factor = 1.0 - (distanceToCenter / (Volcano.VOLCANO_SIZE * 0.15));
                        startHeight += (factor * factor * 60) + (noise.sample(px, pz)) + 5;
                        noiseValue = 0.5;
                    } else {
                        // It's the grassy/forest-y part.
                        double factor = 1.0 - (distanceToCenter / (Volcano.VOLCANO_SIZE * 0.3));
                        startHeight += (factor * 5);
                        noiseValue = 1.0;
                    }

                    biome.buildSurface(
                            chunkRandom, chunk, px, pz, startHeight, noiseValue, config.getDefaultBlock(),
                            config.getDefaultFluid(), world.getSeaLevel(), world.getSeed());
                }
            }
        }
    }
}
