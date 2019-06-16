package com.codeka.castawayterrain.mixins;

import com.codeka.castawayterrain.biome.VolcanoBeachBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BiomeSource.class)
public class SpawnBiomesMixin {
    @Inject(at = @At("HEAD"), cancellable = true, method = "getSpawnBiomes")
    private void getSpawnBiomes(CallbackInfoReturnable<List<Biome>> info) {
        List<Biome> toReturn = new ArrayList<>();
        toReturn.add(VolcanoBeachBiome.BIOME);
        toReturn.add(VolcanoIslandBiome.BIOME);

        info.setReturnValue(toReturn);
    }
}
