package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class ModBlocks {
    public static final Block EGG_PAINTER = registerBlock("egg_painter",
            Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).forceSolidOn().pushReaction(PushReaction.DESTROY).noOcclusion(),
            EggPainterBlock::new);
    public static final Block DYED_EGG = registerBlock("dyed_egg",
            Block.Properties.of().noOcclusion().forceSolidOn().strength(0.5F).mapColor(MapColor.SAND).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY),
            DyedEggBlock::new);

    private static Block registerBlock(String name, BlockBehaviour.Properties baseProps, Function<Block.Properties, Block> function) {
        Identifier loc = Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, name);
        BlockBehaviour.Properties props = baseProps.setId(ResourceKey.create(Registries.BLOCK, loc));

        return Registry.register(BuiltInRegistries.BLOCK, loc, function.apply(props));
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering blocks for " + EastersDelight.MOD_ID);
    }
}
