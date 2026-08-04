package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.villager.ModVillagers;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * 26.2 requires every provider registered through the Fabric datagen entrypoint to extend
 * {@link FabricTagsProvider}; vanilla's {@code PoiTypeTagsProvider} still compiles but is
 * rejected with a ClassCastException at generation time.
 */
public class ModPoiTypeTagsProvider extends FabricTagsProvider<PoiType> {
    public ModPoiTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addModTags();
    }

    private void addModTags() {
        builder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
            .addOptional(ModVillagers.EGG_BUNNY_POI_KEY);
    }
}
