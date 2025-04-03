package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.COMPOSTABLES)
                // 85% chance
                .add(ModItems.BOILED_EGG.getId(), new Compostable(0.85f, true), false)
                .add(ModItems.EGG_SLICE.getId(), new Compostable(0.85f, true), false)
                .add(ModItems.BUNNY_COOKIE.getId(), new Compostable(0.85f, true), false)
        ;
    }
}
