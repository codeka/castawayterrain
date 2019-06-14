package com.codeka.castawayterrain;

import com.codeka.castawayterrain.biome.ShallowWarmOceanBiome;
import com.codeka.castawayterrain.biome.VolcanoBeachBiome;
import com.codeka.castawayterrain.biome.VolcanoIslandBiome;
import com.codeka.castawayterrain.block.VolcanoSmokerBlock;
import com.codeka.castawayterrain.block.VolcanoSmokerTileEntity;
import com.codeka.castawayterrain.world.CastawayWorldType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main mod entrypoint for CastawayTerrain. We just register our custom world type.
 */
@Mod("castawayterrain")
public class CastawayTerrainMod {
    private static final Logger L = LogManager.getLogger();

    public CastawayWorldType castawayWorldType;

    public CastawayTerrainMod() {
        registerBiome("volcano_island", VolcanoIslandBiome.BIOME);
        registerBiome("volcano_beach", VolcanoBeachBiome.BIOME);
        registerBiome("shallow_warm_ocean", ShallowWarmOceanBiome.BIOME);

        ForgeRegistries.BLOCKS.register(VolcanoSmokerBlock.BLOCK);
        BlockItem blockItem = new BlockItem(VolcanoSmokerBlock.BLOCK, new Item.Properties().group(ItemGroup.DECORATIONS));
        blockItem.setRegistryName("volcano_smoker");
        ForgeRegistries.ITEMS.register(blockItem);
        blockItem.addToBlockToItemMap(Item.BLOCK_TO_ITEM, blockItem);

        ForgeRegistries.TILE_ENTITIES.register(VolcanoSmokerTileEntity.TILE_ENTITY_TYPE);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        castawayWorldType = new CastawayWorldType();
    }

    private static void registerBiome(String name, Biome biome) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addSpawnBiome(biome);
        BiomeDictionary.addTypes(biome, BiomeDictionary.Type.MOUNTAIN); // TODO??
    }
}
