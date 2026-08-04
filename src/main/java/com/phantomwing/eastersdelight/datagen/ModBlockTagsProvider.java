package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.tags.CommonTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
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
        blockTag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.EGG_PAINTER);
    }

    private void addCommonTags() {
        blockTag(CommonTags.MINEABLE_WITH_KNIFE)
                .add(ModBlocks.DYED_EGG);
    }

    private void addCompatibilityTags() {
    }

    // 26.2: the tag appender only accepts ResourceKeys; this wrapper restores value-based add(Block...).
    private RegistryTagAppender<Block, Block> blockTag(TagKey<Block> tag) {
        return new RegistryTagAppender<>(builder(tag), block -> block.builtInRegistryHolder().key());
    }
}
