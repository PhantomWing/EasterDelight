package com.phantomwing.eastersdelight.block.entity;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EastersDelight.MOD_ID);

    public static final Supplier<BlockEntityType<EggPaintingTableBlockEntity>> EGG_PAINTING_TABLE =
            BLOCK_ENTITY_TYPE.register("egg_painting_table", () ->
                    BlockEntityType.Builder.of(EggPaintingTableBlockEntity::new,
                            ModBlocks.EGG_PAINTING_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPE.register(eventBus);
    }
}
