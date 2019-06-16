package com.codeka.castawayterrain.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class VolcanoSmokerBlock extends Block implements BlockEntityProvider {
    VolcanoSmokerBlock() {
        super(Block.Settings.of(Material.STONE, MaterialColor.STONE));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new VolcanoSmokerBlockEntity();
    }
}
