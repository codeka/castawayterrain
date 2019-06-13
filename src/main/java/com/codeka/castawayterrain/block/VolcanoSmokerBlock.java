package com.codeka.castawayterrain.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class VolcanoSmokerBlock extends Block {
    public static final VolcanoSmokerBlock BLOCK = new VolcanoSmokerBlock();

    private VolcanoSmokerBlock() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.STONE));
        setRegistryName("volcano_smoker");

    }
}
