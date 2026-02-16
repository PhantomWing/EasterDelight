package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addMinecraftTags();
        addCommonTags();
        addCompatibilityTags();
        addModTags();
    }

    private void addModTags() {
    }

    private void addMinecraftTags() {
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.EGG_PAINTER);
    }

    private void addCommonTags() {
    }

    private void addCompatibilityTags() {
    }
}
