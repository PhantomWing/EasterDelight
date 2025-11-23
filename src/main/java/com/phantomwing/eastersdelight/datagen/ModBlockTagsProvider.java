package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EastersDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addModTags();
        addMinecraftTags();
        addCommonTags();
        addCompatibilityTags();
    }

    private void addModTags() {
    }

    private void addMinecraftTags() {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.EGG_PAINTER.get());
    }

    private void addCommonTags() {
    }

    private void addCompatibilityTags() {
    }
}
