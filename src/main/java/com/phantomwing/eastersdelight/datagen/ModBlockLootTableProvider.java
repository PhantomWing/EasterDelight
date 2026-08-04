package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    // Actually add our loot tables.
    @Override
    public void generate() {
        dropSelf(ModBlocks.EGG_PAINTER);

        // Dyed Egg is absent on purpose: DyedEggBlock#getDrops builds its own stack so the color
        // and pattern components survive. Boiled eggs carry no state, so plain drops are enough.
        dropSelf(ModBlocks.BOILED_EGG);
        dropSelf(ModBlocks.BOILED_BROWN_EGG);
        dropSelf(ModBlocks.BOILED_BLUE_EGG);
    }
}
