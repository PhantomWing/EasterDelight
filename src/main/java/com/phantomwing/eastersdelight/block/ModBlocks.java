package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
    public static final Block EGG_PAINTER = registerBlock("egg_painter",
            new EggPainterBlock(Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).noOcclusion()));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        EastersDelight.LOGGER.info("Registering blocks for " + EastersDelight.MOD_ID);
    }
}
