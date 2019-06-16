package com.codeka.castawayterrain.mixins;

import com.codeka.castawayterrain.CastawayTerrainMod;
import com.codeka.castawayterrain.world.CastawayChunkGenerator;
import com.codeka.castawayterrain.world.CastawayChunkGeneratorConfig;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OverworldDimension.class)
public abstract class OverworldDimensionMixin extends Dimension {
    public OverworldDimensionMixin(World world, DimensionType dimensionType) {
        super(world, dimensionType);
    }

    /**
     * Add a check to see if the generator type is set to castaway, and return our chunk generator instead.
     */
    @Inject(at = @At("HEAD"), cancellable = true, method="createChunkGenerator")
    public void createChunkGenerator(CallbackInfoReturnable<ChunkGenerator<? extends ChunkGeneratorConfig>> callbackInfo) {
        if (world.getLevelProperties().getGeneratorType() == CastawayTerrainMod.CASTAWAY_LEVEL_TYPE) {
            callbackInfo.setReturnValue(new CastawayChunkGenerator(world, new CastawayChunkGeneratorConfig()));
            callbackInfo.cancel();
        }
    }
}
