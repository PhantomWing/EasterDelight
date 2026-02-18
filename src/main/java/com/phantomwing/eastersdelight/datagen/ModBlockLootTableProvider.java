package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    // Actually add our loot tables.
    @Override
    public void generate() {
        dropSelf(ModBlocks.EGG_PAINTER);
        dropSelf(ModBlocks.DYED_EGG);
    }
}
