package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.block.custom.EggPainterBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.PieBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EastersDelight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        eggPainterBlock(ModBlocks.EGG_PAINTER.get());
    }

    private void farmersDelightCrate(Block block) {
        String blockName = blockName(block);
        this.simpleBlock(block,
                models().cubeBottomTop(blockName, resourceBlock(blockName + "_side"), farmersDelightResourceBlock("crate_bottom"), resourceBlock(blockName + "_top")));
    }

    private void canvasBag(Block block) {
        String blockName = blockName(block);
        this.simpleBlock(block, models().withExistingParent(blockName, "cube")
                .texture("particle", resourceBlock(blockName + "_top"))
                .texture("down", resourceBlock(blockName + "_bottom"))
                .texture("up", resourceBlock(blockName + "_top"))
                .texture("north", resourceBlock(blockName + "_side_tied"))
                .texture("south", resourceBlock(blockName + "_side_tied"))
                .texture("east", resourceBlock(blockName + "_side"))
                .texture("west", resourceBlock(blockName + "_side"))
        );
    }

    private void pieBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                            int bites = state.getValue(PieBlock.BITES);
                            String suffix = bites == 0 ? "" : "_slice" + bites;
                            return ConfiguredModel.builder()
                                    .modelFile(existingModel(blockName(block) + suffix))
                                    .rotationY(((int) state.getValue(PieBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
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

    public ResourceLocation farmersDelightResourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/" + path);
    }

    public void eggPainterBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(existingModel(blockName(block)))
                        .rotationY(((int) state.getValue(EggPainterBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                        .build()
                );
    }

    public void modelBlock(Block block, String modelPath) {
        simpleBlock(block,
                new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, modelPath)));
    }
}
