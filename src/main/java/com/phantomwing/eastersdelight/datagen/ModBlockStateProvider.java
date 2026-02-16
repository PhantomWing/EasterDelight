package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModBlockStateProvider {
    public static void registerStatesAndModels(BlockModelGenerators g) {
        eggPainter(g, ModBlocks.EGG_PAINTER);
    }

    private static void eggPainter(BlockModelGenerators g, Block block) {
        MultiVariantGenerator generator = MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(EggPainterBlock.FACING)
                        .generate((direction) -> Variant.variant()
                                .with(VariantProperties.Y_ROT, dirToRot(direction))
                                .with(VariantProperties.MODEL, resourceBlock(blockName(block))))
                );
        g.blockStateOutput.accept(generator);
    }

    private static String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private static ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "block/" + path);
    }

    private static VariantProperties.Rotation dirToRot(Direction direction) {
        return direction == Direction.NORTH ? VariantProperties.Rotation.R0
                : direction == Direction.EAST ? VariantProperties.Rotation.R90
                : direction == Direction.SOUTH ? VariantProperties.Rotation.R180
                : VariantProperties.Rotation.R270;
    }
}
