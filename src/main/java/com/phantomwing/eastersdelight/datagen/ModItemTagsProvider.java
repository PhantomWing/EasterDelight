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
        // Whole boiled eggs, regardless of shell color. Recipes that want "a boiled egg" use this
        // so the brown and blue variants are never second-class.
        this.valueLookupBuilder(ModTags.Items.BOILED_EGGS).add(
            ModItems.BOILED_EGG,
            ModItems.BOILED_BROWN_EGG,
            ModItems.BOILED_BLUE_EGG
        );

        // Painting discards the shell color (the result is a Dyed Egg tinted by the dye), so every
        // boiled egg is equally paintable.
        this.valueLookupBuilder(ModTags.Items.PAINTABLE_EGGS)
            .addTag(ModTags.Items.BOILED_EGGS)
            .add(ModItems.DYED_EGG);

        // Farmer's Delight's Baked Cod Stew and Noodle Soup take #minecraft:eggs. Their overrides take
        // those and c:eggs, and cooked eggs too, so boiled and dyed eggs count.
        this.valueLookupBuilder(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
            .addOptionalTag(ItemTags.EGGS)
            .addOptionalTag(CommonTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);

        this.valueLookupBuilder(ModTags.Items.NOODLE_SOUP_INGREDIENTS)
            .addOptionalTag(ItemTags.EGGS)
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
        this.valueLookupBuilder(CommonTags.FOODS_BOILED_EGG)
                .addTag(ModTags.Items.BOILED_EGGS)
                .add(
                        ModItems.DYED_EGG,
                        ModItems.EGG_SLICE
                );

        // Boiled eggs are always Cooked, but not all cooked eggs are boiled (Like Fried Egg)
        this.valueLookupBuilder(CommonTags.FOODS_COOKED_EGG)
                .addTag(CommonTags.FOODS_BOILED_EGG);

        // Potatoes as food, for other mods' recipes
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
