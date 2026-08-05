package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.tags.CommonTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
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
        this.valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.EGG_PAINTER);
    }

    private void addCommonTags() {
        this.valueLookupBuilder(CommonTags.MINEABLE_WITH_KNIFE)
                .add(ModBlocks.DYED_EGG,
                        ModBlocks.BOILED_EGG,
                        ModBlocks.BOILED_BROWN_EGG,
                        ModBlocks.BOILED_BLUE_EGG);
    }

    private void addCompatibilityTags() {
    }
}
