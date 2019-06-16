package com.codeka.castawayterrain.mixins;

import com.codeka.castawayterrain.world.CastawayChunkGenerator;
import com.codeka.castawayterrain.world.CastawayChunkGeneratorConfig;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(OverworldDimension.class)
public abstract class OverworldDimensionMixin extends Dimension {
    private static final Logger L = LogManager.getLogger();

    public OverworldDimensionMixin(World world, DimensionType dimensionType) {
        super(world, dimensionType);
    }

    /**
     * Overwriting or something.
     *
     * @author Codeka
     * @reason Returning our castaway generator.
     */
    @Overwrite()
    public ChunkGenerator<? extends ChunkGeneratorConfig> createChunkGenerator() {
        L.info("Castaway createChunkGenerator");
        return new CastawayChunkGenerator(world, new CastawayChunkGeneratorConfig());
    }
}
