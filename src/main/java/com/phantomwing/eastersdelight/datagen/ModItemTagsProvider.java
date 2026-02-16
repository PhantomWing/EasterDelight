package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.CompatibilityTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
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
        getOrCreateTagBuilder(ModTags.Items.PAINTABLE_EGGS).add(
            ModItems.BOILED_EGG,
            ModItems.DYED_EGG
        );

        // Override for Baked Cod Stew (by default only contains Tags.Items.EGGS)
        getOrCreateTagBuilder(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
            .addOptionalTag(ConventionalItemTags.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);
    }

    private void addMinecraftTags() {
        getOrCreateTagBuilder(ItemTags.PARROT_POISONOUS_FOOD).add(
            ModItems.BUNNY_COOKIE
        );
    }

    private void addCommonTags() {
        // Define boiled eggs
        getOrCreateTagBuilder(CommonTags.FOODS_BOILED_EGG).add(
                ModItems.BOILED_EGG,
                ModItems.DYED_EGG,
                ModItems.EGG_SLICE
        );

        // Boiled eggs are always Cooked, but not all cooked eggs are boiled (Like Fried Egg)
        getOrCreateTagBuilder(CommonTags.FOODS_COOKED_EGG)
                .addTag(CommonTags.FOODS_BOILED_EGG);

        // For compatibility, replace Items.POTATO with a tag (in some override recipes)
        getOrCreateTagBuilder(CommonTags.FOODS_POTATO).add(
                Items.POTATO
        );

        // Cookies
        getOrCreateTagBuilder(CommonTags.FOODS_COOKIE).add(
                ModItems.BUNNY_COOKIE
        );
    }

    private void addCompatibilityTags() {
        // Farmer's Delight
        getOrCreateTagBuilder(vectorwing.farmersdelight.common.tag.ModTags.CABBAGE_ROLL_INGREDIENTS)
                .addTag(CommonTags.FOODS_COOKED_EGG);

        // Supplementaries
        getOrCreateTagBuilder(CompatibilityTags.SUPPLEMENTARIES_COOKIES)
                .addTag(CommonTags.FOODS_COOKIE);
    }
}
