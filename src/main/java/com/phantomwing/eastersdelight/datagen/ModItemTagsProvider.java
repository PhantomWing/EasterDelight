package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.CompatibilityTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
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

        // Farmer's Delight's Baked Cod Stew and Noodle Soup take #minecraft:eggs. Their overrides take
        // c:eggs, which every mod's eggs and vanilla's go in, and cooked eggs, so boiled and dyed eggs count.
        itemTag(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
            .addOptionalTag(CommonTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);

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

        // Potatoes as food, the crop included, as in Rustic Delight. Recipes take this one.
        itemTag(CommonTags.FOODS_POTATO)
                .add(Items.POTATO)
                .addOptionalTag(ConventionalItemTags.POTATO_CROPS);

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
