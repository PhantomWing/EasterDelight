package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final Block EGG_PAINTER = registerBlock("egg_painter",
            new EggPainterBlock(Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).forceSolidOn().pushReaction(PushReaction.DESTROY).noOcclusion()));
    public static final Block DYED_EGG = registerBlock("dyed_egg",
            new DyedEggBlock(Block.Properties.of().noOcclusion().forceSolidOn().strength(0.5F).mapColor(MapColor.SAND).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name), block);
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering blocks for " + EastersDelight.MOD_ID);
    }
}
