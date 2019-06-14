package com.codeka.castawayterrain.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class VolcanoSmokerBlock extends Block implements IWaterLoggable {
    public static final VolcanoSmokerBlock BLOCK = new VolcanoSmokerBlock();

    private VolcanoSmokerBlock() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.STONE).tickRandomly());
        setRegistryName("volcano_smoker");
    }

    /**
     * Called every now and then to show some sort of client-side animation. We'll have it throw up some fire
     * particles.
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(2) == 0) {
            for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                worldIn.addParticle(
                        ParticleTypes.LAVA,
                        (float)pos.getX() + 0.5f,
                        (float)pos.getY() + 0.5f,
                        (float)pos.getZ() + 0.5f,
                        rand.nextFloat() / 2.0f,
                        5.0E-5D,
                        rand.nextFloat() / 2.0f);
            }
        }
    }

}
