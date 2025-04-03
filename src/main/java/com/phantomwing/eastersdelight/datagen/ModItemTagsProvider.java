package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, EastersDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addModTags();
        addMinecraftTags();
        addCommonTags();
        addCompatibilityTags();
    }

    private void addModTags() {
        this.tag(ModTags.Items.PAINTABLE_EGGS).add(
            ModItems.BOILED_EGG.get(),
            ModItems.DYED_EGG.get()
        );

        // Override for Baked Cod Stew (by default only contains Tags.Items.EGGS)
        this.tag(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
            .addTag(Tags.Items.EGGS)
            .addTag(CommonTags.FOODS_COOKED_EGG);
    }

    private void addMinecraftTags() {
    }

    private void addCommonTags() {
        addStorageBlockTags();
        addSeedTags();
        addCropTags();
        addFoodTags();
    }

    private void addCompatibilityTags() {
        // Farmer's Delight
        this.tag(vectorwing.farmersdelight.common.tag.ModTags.CABBAGE_ROLL_INGREDIENTS)
                .addTag(CommonTags.FOODS_COOKED_EGG);
    }

    private void addStorageBlockTags() {
    }

    private void addSeedTags() {
    }

    private void addCropTags() {
    }

    private void addFoodTags() {
        // Define boiled eggs
        this.tag(CommonTags.FOODS_BOILED_EGG).add(
                ModItems.BOILED_EGG.get(),
                ModItems.DYED_EGG.get(),
                ModItems.EGG_SLICE.get()
        );

        // Boiled eggs are always Cooked, but not all cooked eggs are boiled (Like Fried Egg)
        this.tag(CommonTags.FOODS_COOKED_EGG)
                .addTag(CommonTags.FOODS_BOILED_EGG);

        // For compatibility, replace Items.POTATO with a tag (in some override recipes)
        this.tag(CommonTags.FOODS_POTATO).add(
                Items.POTATO
        );

        // Cookies
        this.tag(CommonTags.FOODS_COOKIE).add(
                ModItems.BUNNY_COOKIE.get()
        );
    }
}
