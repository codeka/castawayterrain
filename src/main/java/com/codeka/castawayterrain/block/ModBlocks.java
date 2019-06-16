package com.codeka.castawayterrain.block;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static VolcanoSmokerBlock VOLCANO_SMOKER_BLOCK;
    public static BlockEntityType<VolcanoSmokerBlockEntity> VOLCANO_SMOKER_BLOCK_ENTITY_TYPE;
    public static BlockItem VOLCANO_SMOKER_BLOCK_ITEM;


    public static void init() {
        Identifier id = new Identifier("castawayterrain", "volcano_smoker");
        VOLCANO_SMOKER_BLOCK = new VolcanoSmokerBlock();
        Registry.register(Registry.BLOCK, id, VOLCANO_SMOKER_BLOCK);

        VOLCANO_SMOKER_BLOCK_ITEM =
                new BlockItem(VOLCANO_SMOKER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));
        Registry.register(Registry.ITEM, id, VOLCANO_SMOKER_BLOCK_ITEM);

        VOLCANO_SMOKER_BLOCK_ENTITY_TYPE = Registry.register(
                Registry.BLOCK_ENTITY,
                id.toString(),
                BlockEntityType.Builder.create(VolcanoSmokerBlockEntity::new, VOLCANO_SMOKER_BLOCK).build(null));

    }
}
