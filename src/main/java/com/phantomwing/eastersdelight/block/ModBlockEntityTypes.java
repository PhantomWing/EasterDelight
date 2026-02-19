package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.entity.DyedEggBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes
{
    public static final BlockEntityType<DyedEggBlockEntity> DYED_EGG = registerBlockEntity("dyed_egg", DyedEggBlockEntity::new);

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name),
                BlockEntityType.Builder.of(factory, blocks).build(null));
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering block entity types for " + EastersDelight.MOD_ID);
    }
}