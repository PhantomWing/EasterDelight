package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import com.phantomwing.eastersdelight.component.EggPattern;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.Optional;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EastersDelight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        eggPainterBlock(ModBlocks.EGG_PAINTER.get());
        dyedEgg(ModBlocks.DYED_EGG.get());
    }

    public void eggPainterBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(existingModel(blockName(block)))
                        .rotationY(((int) state.getValue(EggPainterBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                        .build()
                );
    }

    private void dyedEgg(Block block) {
        ResourceLocation parentModel = resourceBlock(blockName(block) + "_patterned");

        // Generate models for each pattern variant.
        for (EggPattern pattern : EggPattern.values()) {
            String modelName = blockName(block) + "_" + pattern.getName();
            ResourceLocation modelLocation = resourceBlock(modelName);
            this.models().withExistingParent(modelName, parentModel).texture("pattern", modelLocation).renderType("cutout");
        }

        getVariantBuilder(block)
                .forAllStates(state -> {
                    boolean patterned = state.getValue(DyedEggBlock.PATTERNED);
                    EggPattern pattern = state.getValue(DyedEggBlock.EGG_PATTERN);

                    return ConfiguredModel.builder()
                        .modelFile(existingModel(blockName(block) + (patterned ? ("_" + pattern.getName()) : "")))
                        .rotationY(((int) state.getValue(EggPainterBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                        .build();
                    }
                );
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "block/" + path);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }
}
