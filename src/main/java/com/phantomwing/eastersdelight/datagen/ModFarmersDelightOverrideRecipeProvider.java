package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import com.phantomwing.eastersdelight.util.CookingPotRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;

import java.util.concurrent.CompletableFuture;

import static com.phantomwing.eastersdelight.datagen.ModRecipeProvider.MEDIUM_EXP;
import static com.phantomwing.eastersdelight.datagen.ModRecipeProvider.NORMAL_COOKING;

/**
 * Recipe provider for FDR recipe overrides. Lives in a separate built-in resource pack
 * (registered as {@code eastersdelight:farmersdelight_overrides}) because Fabric Loader
 * sorts regular mod packs alphabetically by mod ID — and {@code eastersdelight} loads
 * <em>before</em> {@code farmersdelight}, so files we put under {@code data/farmersdelight/…}
 * in our normal datagen output get overridden by FDR's own bundled recipes at runtime.
 * Built-in resource packs registered through Fabric Resource Loader sit above the regular
 * mod-pack stack, so files inside them take precedence.
 *
 * <p>Anything writing to {@code data/farmersdelight/…} (recipe overrides, advancement
 * overrides) belongs here. {@code data/eastersdelight/…} stays in {@link ModRecipeProvider}.
 */
public class ModFarmersDelightOverrideRecipeProvider extends RecipeProvider {
    public ModFarmersDelightOverrideRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput output) {
        // Baked Cod Stew — override FDR's recipe so boiled/dyed eggs work as the egg ingredient.
        CookingPotRecipeBuilder.cookingPotRecipe(vectorwing.farmersdelight.common.registry.ModItems.BAKED_COD_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.Items.FOODS_RAW_COD)
                .addIngredient(CommonTags.FOODS_POTATO)
                .addIngredient(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
                .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.Items.CROPS_TOMATO)
                .unlockedByAnyIngredient(Items.COD, Items.POTATO, vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(), Items.EGG)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        // Noodle Soup — mirrors FDR's recipe (pasta + egg + dried kelp + raw pork, 200 ticks,
        // exp 1.0) but swaps the raw c:eggs ingredient for our NOODLE_SOUP_INGREDIENTS tag so
        // boiled/dyed eggs count too. Bowl container included to match the other branches.
        CookingPotRecipeBuilder.cookingPotRecipe(vectorwing.farmersdelight.common.registry.ModItems.NOODLE_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.Items.FOODS_PASTA)
                .addIngredient(ModTags.Items.NOODLE_SOUP_INGREDIENTS)
                .addIngredient(Items.DRIED_KELP)
                .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.Items.FOODS_RAW_PORK)
                .unlockedByAnyIngredient(vectorwing.farmersdelight.common.registry.ModItems.RAW_PASTA.get(), Items.DRIED_KELP, Items.PORKCHOP)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
    }
}
