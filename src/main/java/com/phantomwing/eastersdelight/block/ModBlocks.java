package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.EggPaintingTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EastersDelight.MOD_ID);

    // Blocks
    public static final DeferredBlock<Block> EGG_PAINTING_TABLE = BLOCKS.register("egg_painting_table",
            () -> new EggPaintingTableBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
