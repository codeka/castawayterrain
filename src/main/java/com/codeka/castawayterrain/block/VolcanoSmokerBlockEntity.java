package com.codeka.castawayterrain.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class VolcanoSmokerBlockEntity extends BlockEntity implements Tickable {
  public VolcanoSmokerBlockEntity() {
    super(ModBlocks.VOLCANO_SMOKER_BLOCK_ENTITY_TYPE);
  }

  @Override
  public void tick() {
    if (world != null && world.isClient) {
      for (int i = 0; i < world.random.nextInt(2) + 2; ++i) {
        addSmoke(world, getPos());
      }
    }
  }

  public static void addSmoke(World world, BlockPos pos) {
    Random random = world.getRandom();
    world.addParticle(
        ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
        true,
        pos.getX() + 0.5D + random.nextDouble() * 2.0D * (random.nextBoolean() ? 1 : -1),
        pos.getY() + random.nextDouble() + random.nextDouble(),
        pos.getZ() + 0.5D + random.nextDouble() * 2.0D * (random.nextBoolean() ? 1 : -1),
        0.0D,
        0.07D,
        0.0D);

  }

}
