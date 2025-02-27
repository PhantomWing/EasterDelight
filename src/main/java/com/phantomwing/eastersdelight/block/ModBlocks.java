package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EastersDelight.MOD_ID);

    // Blocks
    public static final DeferredBlock<Block> EGG_PAINTER = BLOCKS.register("egg_painter",
            () -> new EggPainterBlock(Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).noOcclusion()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
