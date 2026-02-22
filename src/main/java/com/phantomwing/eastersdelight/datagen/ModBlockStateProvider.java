package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import com.phantomwing.eastersdelight.component.EggPattern;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

public class ModBlockStateProvider {
    public static void registerStatesAndModels(BlockModelGenerators g) {
        eggPainter(g, ModBlocks.EGG_PAINTER);
        dyedEgg(g, ModBlocks.DYED_EGG);
    }

    private static void eggPainter(BlockModelGenerators g, Block block) {
        MultiVariantGenerator generator = MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(EggPainterBlock.FACING)
                        .generate((direction) -> {
                            ResourceLocation modelLoc = resourceBlock(blockName(block));
                            MultiVariant variant = plainVariant(modelLoc);
                            VariantMutator rotation = dirToRot(direction);

                            if (rotation != null) {
                                variant = variant.with(rotation);
                            }

                            return variant;
                        })
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

        MultiVariantGenerator generator = MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(DyedEggBlock.FACING, DyedEggBlock.PATTERNED, DyedEggBlock.EGG_PATTERN)
                        .generate((direction, patterned, pattern) -> {
                            ResourceLocation modelLoc = resourceBlock(blockName(block) + (patterned ? ("_" + pattern.getName()) : ""));
                            MultiVariant variant = plainVariant(modelLoc);
                            VariantMutator rotation = dirToRot(direction);

                            if (rotation != null) {
                                variant = variant.with(rotation);
                            }

                            return variant;
                        })
                );

        g.blockStateOutput.accept(generator);
    }

    private static String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private static ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "block/" + path);
    }

    private static VariantMutator dirToRot(Direction direction) {
        return direction == Direction.NORTH ? null
                : direction == Direction.EAST ? BlockModelGenerators.Y_ROT_90
                : direction == Direction.SOUTH ? BlockModelGenerators.Y_ROT_180
                : BlockModelGenerators.Y_ROT_270;
    }
}
