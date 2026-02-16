package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.villager.ModVillagers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public ModPoiTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addModTags();
    }

    private void addModTags() {
        this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
            .addOptional(ModVillagers.EGG_BUNNY_POI_KEY.location());
    }
}
