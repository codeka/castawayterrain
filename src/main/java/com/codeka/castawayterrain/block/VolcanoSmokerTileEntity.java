package com.codeka.castawayterrain.block;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class VolcanoSmokerTileEntity extends TileEntity implements ITickableTileEntity {
    public static final TileEntityType<VolcanoSmokerTileEntity> TILE_ENTITY_TYPE =
            TileEntityType.Builder
                    .func_223042_a(VolcanoSmokerTileEntity::new, VolcanoSmokerBlock.BLOCK)
                    .build(null);

    static {
        TILE_ENTITY_TYPE.setRegistryName("castawayterrain", "volcano_smoker");
    }

    public VolcanoSmokerTileEntity() {
        super(TILE_ENTITY_TYPE);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            for(int i = 0; i < world.rand.nextInt(2) + 2; ++i) {
                addSmoke(world, getPos());
            }
        }
    }

    public static void addSmoke(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.func_217404_b(
                ParticleTypes.field_218418_af,
                true,
                pos.getX() + 0.5D + random.nextDouble() * 2.0D * (random.nextBoolean() ? 1 : -1),
                pos.getY() + random.nextDouble() + random.nextDouble(),
                pos.getZ() + 0.5D + random.nextDouble() * 2.0D * (random.nextBoolean() ? 1 : -1),
                0.0D,
                0.07D,
                0.0D);

    }

}
