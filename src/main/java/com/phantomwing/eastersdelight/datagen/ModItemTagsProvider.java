package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.CompatibilityTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
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
        itemTag(ModTags.Items.BOILED_EGGS).add(
            ModItems.BOILED_EGG,
            ModItems.BOILED_BROWN_EGG,
            ModItems.BOILED_BLUE_EGG
        );

        // Painting discards the shell color (the result is a Dyed Egg tinted by the dye), so every
        // boiled egg is equally paintable.
        itemTag(ModTags.Items.PAINTABLE_EGGS)
            .addTag(ModTags.Items.BOILED_EGGS)
            .add(ModItems.DYED_EGG);

        // Override for Baked Cod Stew (by default only contains Tags.Items.EGGS)
        itemTag(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
            .addOptionalTag(CommonTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);

        // Override for Noodle Soup (FDR's recipe hardcodes c:eggs — mirror the cod stew set so
        // boiled / dyed eggs also count as a valid noodle soup ingredient).
        itemTag(ModTags.Items.NOODLE_SOUP_INGREDIENTS)
            .addOptionalTag(CommonTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);
    }

    private void addMinecraftTags() {
        itemTag(ItemTags.PARROT_POISONOUS_FOOD).add(
            ModItems.BUNNY_COOKIE
        );
    }

    private void addCommonTags() {
        // Define boiled eggs
        itemTag(CommonTags.FOODS_BOILED_EGG)
                .addTag(ModTags.Items.BOILED_EGGS)
                .add(
                        ModItems.DYED_EGG,
                        ModItems.EGG_SLICE
                );

        // Boiled eggs are always Cooked, but not all cooked eggs are boiled (Like Fried Egg)
        itemTag(CommonTags.FOODS_COOKED_EGG)
                .addTag(CommonTags.FOODS_BOILED_EGG);

        // For compatibility, replace Items.POTATO with a tag (in some override recipes)
        itemTag(CommonTags.FOODS_POTATO).add(
                Items.POTATO
        );

        // Cookies
        itemTag(CommonTags.FOODS_COOKIE).add(
                ModItems.BUNNY_COOKIE
        );
    }

    private void addCompatibilityTags() {
        // Farmer's Delight
        // Note: ModTags.CABBAGE_ROLL_INGREDIENTS was removed in FDR 3.x — cabbage roll recipes
        // now use a different mechanism. Boiled eggs are still exposed via c:foods/cooked_egg.

        // Supplementaries
        itemTag(CompatibilityTags.SUPPLEMENTARIES_COOKIES)
                .addTag(CommonTags.FOODS_COOKIE);
    }

    // 26.2: the tag appender only accepts ResourceKeys; this wrapper restores value-based add(ItemLike...).
    private RegistryTagAppender<Item, ItemLike> itemTag(TagKey<Item> tag) {
        return new RegistryTagAppender<>(builder(tag), item -> item.asItem().builtInRegistryHolder().key());
    }
}
