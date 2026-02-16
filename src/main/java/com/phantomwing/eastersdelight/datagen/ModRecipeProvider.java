package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import com.phantomwing.eastersdelight.util.CookingPotRecipeBuilder;
import com.phantomwing.eastersdelight.util.CuttingBoardRecipeBuilder;
import com.phantomwing.eastersdelight.util.ItemUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public static final int FAST_COOKING = 100;
    public static final int NORMAL_COOKING = 200;
    public static final int SLOW_COOKING = 400;
    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput output) {
        buildCraftingRecipes(output);
        buildCuttingRecipes(output);
        buildCookingRecipes(output);
        buildFarmersDelightOverrideRecipes(output);
    }

    private void buildCraftingRecipes(@NotNull RecipeOutput output) {
        // Egg Painter
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EGG_PAINTER, 1)
                .pattern("/E/")
                .pattern("###")
                .define('E', ModItems.BOILED_EGG)
                .define('/', Items.IRON_INGOT)
                .define('#', ItemTags.PLANKS)
                .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModItems.BOILED_EGG))
                .save(output, ItemUtils.getResourceLocation(ModItems.EGG_PAINTER));

        // Chocolate Egg
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHOCOLATE_EGG, 8)
                .requires(Items.COCOA_BEANS)
                .requires(Items.COCOA_BEANS)
                .requires(ModItems.BOILED_EGG)
                .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModItems.BOILED_EGG))
                .save(output);

        // Bunny Cookie
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BUNNY_COOKIE, 8)
                .requires(Items.COCOA_BEANS)
                .requires(Items.RABBIT_HIDE)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(Items.RABBIT_HIDE), has(Items.RABBIT_HIDE))
                .save(output);
    }

    private void buildCuttingRecipes(@NotNull RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BOILED_EGG), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                .build(output, ItemUtils.getResourceLocation(ModItems.EGG_SLICE));
    }

    private void buildCookingRecipes(@NotNull RecipeOutput output) {
        // Boiled Egg (Can place up to six eggs in a pot to cook them)
        for (int eggCount = 1; eggCount <= 6; eggCount++)
        {
            CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BOILED_EGG, eggCount, NORMAL_COOKING, MEDIUM_EXP)
                    .addIngredient(Items.EGG, eggCount)
                    .unlockedByAnyIngredient(Items.EGG)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                    .save(output, ItemUtils.getNameWithNamespace(ModItems.BOILED_EGG) + "_" + eggCount);
        }
    }

    private void buildFarmersDelightOverrideRecipes(@NotNull RecipeOutput output) {
        // Farmer's Delight overrides, to include Boiled Eggs
        // Baked Cod Stew
        CookingPotRecipeBuilder.cookingPotRecipe(vectorwing.farmersdelight.common.registry.ModItems.BAKED_COD_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.FOODS_RAW_COD)
                .addIngredient(CommonTags.FOODS_POTATO)
                .addIngredient(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
                .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.CROPS_TOMATO)
                .unlockedByAnyIngredient(Items.COD, Items.POTATO, vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(), Items.EGG)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
    }
}
