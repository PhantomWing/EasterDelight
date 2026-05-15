package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.CompatibilityTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
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
        this.valueLookupBuilder(ModTags.Items.PAINTABLE_EGGS).add(
            ModItems.BOILED_EGG,
            ModItems.DYED_EGG
        );

        // Override for Baked Cod Stew (by default only contains Tags.Items.EGGS)
        this.valueLookupBuilder(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
            .addOptionalTag(CommonTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);

        // Override for Noodle Soup (FDR's recipe hardcodes c:eggs — mirror the cod stew set so
        // boiled / dyed eggs also count as a valid noodle soup ingredient).
        this.valueLookupBuilder(ModTags.Items.NOODLE_SOUP_INGREDIENTS)
            .addOptionalTag(CommonTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);
    }

    private void addMinecraftTags() {
        this.valueLookupBuilder(ItemTags.PARROT_POISONOUS_FOOD).add(
            ModItems.BUNNY_COOKIE
        );
    }

    private void addCommonTags() {
        // Define boiled eggs
        this.valueLookupBuilder(CommonTags.FOODS_BOILED_EGG).add(
                ModItems.BOILED_EGG,
                ModItems.DYED_EGG,
                ModItems.EGG_SLICE
        );

        // Boiled eggs are always Cooked, but not all cooked eggs are boiled (Like Fried Egg)
        this.valueLookupBuilder(CommonTags.FOODS_COOKED_EGG)
                .addTag(CommonTags.FOODS_BOILED_EGG);

        // For compatibility, replace Items.POTATO with a tag (in some override recipes)
        this.valueLookupBuilder(CommonTags.FOODS_POTATO).add(
                Items.POTATO
        );

        // Cookies
        this.valueLookupBuilder(CommonTags.FOODS_COOKIE).add(
                ModItems.BUNNY_COOKIE
        );
    }

    private void addCompatibilityTags() {
        // Farmer's Delight
        // Note: ModTags.CABBAGE_ROLL_INGREDIENTS was removed in FDR 3.x — cabbage roll recipes
        // now use a different mechanism. Boiled eggs are still exposed via c:foods/cooked_egg.

        // Supplementaries
        this.valueLookupBuilder(CompatibilityTags.SUPPLEMENTARIES_COOKIES)
                .addTag(CommonTags.FOODS_COOKIE);
    }
}
