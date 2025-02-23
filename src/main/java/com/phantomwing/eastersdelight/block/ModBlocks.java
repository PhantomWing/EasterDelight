package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EastersDelight.MOD_ID);

    // Blocks

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
