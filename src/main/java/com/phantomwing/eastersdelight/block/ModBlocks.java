package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EastersDelight.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> EGG_PAINTER = BLOCKS.register("egg_painter",
            () -> new EggPainterBlock(Block.Properties.copy(Blocks.CRAFTING_TABLE).forceSolidOn().pushReaction(PushReaction.DESTROY).noOcclusion()));

    public static final RegistryObject<Block> DYED_EGG = BLOCKS.register("dyed_egg",
            () -> new DyedEggBlock(Block.Properties.of().noOcclusion().forceSolidOn().strength(0.5F).mapColor(MapColor.SAND).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
