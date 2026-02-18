package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import com.phantomwing.eastersdelight.component.EggPattern;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class ModBlockStateProvider {
    public static void registerStatesAndModels(BlockModelGenerators g) {
        eggPainter(g, ModBlocks.EGG_PAINTER);
        dyedEgg(g, ModBlocks.DYED_EGG);
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

    private static void dyedEgg(BlockModelGenerators g, Block block) {
        ResourceLocation parentModel = resourceBlock(blockName(block) + "_patterned");

        for (EggPattern pattern : EggPattern.values()) {
            ResourceLocation modelLocation = resourceBlock(blockName(block) + "_" + pattern.getName());

            TextureMapping mapping = new TextureMapping().put(TextureSlot.PATTERN, modelLocation);
            ModelTemplate template = new ModelTemplate(Optional.of(parentModel), Optional.empty(), TextureSlot.PATTERN);

            template.create(modelLocation, mapping, g.modelOutput);
        }

        MultiVariantGenerator generator = MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.properties(DyedEggBlock.FACING, DyedEggBlock.PATTERNED, DyedEggBlock.EGG_PATTERN)
                        .generate((direction, patterned, pattern) -> Variant.variant()
                                .with(VariantProperties.Y_ROT, dirToRot(direction))
                                .with(VariantProperties.MODEL, resourceBlock(blockName(block) + (patterned ? ("_" + pattern.getName()) : ""))))
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
