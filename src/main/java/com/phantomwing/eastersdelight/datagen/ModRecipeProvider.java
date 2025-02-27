package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.recipe.CookingRecipes;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public static final float FOOD_COOKING_EXP = 0.35f;

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        buildCraftingRecipes(output);
        buildCuttingRecipes(output);
        buildCookingRecipes(output);
    }

    private void buildCraftingRecipes(@NotNull RecipeOutput output) {
        // Egg Painting Mill
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EGG_PAINTER, 1)
                .pattern("/E/")
                .pattern("###")
                .define('E', ModItems.BOILED_EGG)
                .define('/', Items.STICK)
                .define('#', ItemTags.PLANKS)
                .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModItems.BOILED_EGG))
                .save(output, ModItems.EGG_PAINTER.getId());
    }

    private void buildCuttingRecipes(@NotNull RecipeOutput output) {
    }

    private void buildCookingRecipes(@NotNull RecipeOutput output) {
        // Boiled Egg
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BOILED_EGG, 1, CookingRecipes.NORMAL_COOKING, CookingRecipes.MEDIUM_EXP)
                .addIngredient(Items.EGG)
                .unlockedByAnyIngredient(Items.EGG)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output, ModItems.BOILED_EGG.getId());
    }

    protected static void oneToOne(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void horizontalRecipe(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("###")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void twoBytwo(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("##")
                .pattern("##")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void storageItemRecipes(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike storageItem) {
        // From item to storageItem
        ShapedRecipeBuilder.shaped(category, storageItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, storageItem));

        // From storageItem to item
        ShapelessRecipeBuilder.shapeless(category, item, 9)
                .requires(storageItem)
                .unlockedBy(getHasName(storageItem), has(storageItem))
                .save(recipeOutput, getRecipeName(storageItem, item));
    }

    protected static void foodCookingRecipes(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        foodSmelting(recipeOutput, material, result, experience, 200);
        foodSmoking(recipeOutput, material, result, experience, 100); // Smoking is twice as fast
        foodCampfireCooking(recipeOutput, material, result, experience, 600); // Campfire cooking takes three times longer
    }

    protected static void foodSmelting(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void foodSmoking(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, EastersDelight.MOD_ID + ":" + getItemName(result) + "_from_smoking");
    }

    protected static void foodCampfireCooking(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, EastersDelight.MOD_ID + ":" + getItemName(result) + "_from_campfire_cooking");
    }

    protected static void pieRecipes(@NotNull RecipeOutput recipeOutput, @NotNull DeferredItem<Item> pieBlock, @NotNull DeferredItem<Item> sliceItem, Ingredient topping) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, pieBlock, 1)
                .pattern("TTT")
                .pattern("MMM")
                .pattern("SCS")
                .define('T', topping)
                .define('M', CommonTags.FOODS_MILK)
                .define('S', Items.SUGAR)
                .define('C', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy(getHasName(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()), has(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, pieBlock, 1)
                .pattern("##")
                .pattern("##")
                .define('#', sliceItem)
                .unlockedBy(getHasName(sliceItem), has(sliceItem))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, getItemName(pieBlock) + "_from_slices"));
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return EastersDelight.MOD_ID + ":" + getConversionRecipeName(result, item);
    }

    private static Ingredient vegetablesPatch() {
        return DifferenceIngredient.of(Ingredient.of(Tags.Items.FOODS_VEGETABLE), Ingredient.of(Items.MELON_SLICE));
    }

    private static Ingredient stuffedBellPepperFilling() {
        return DifferenceIngredient.of(Ingredient.of(vectorwing.farmersdelight.common.tag.ModTags.CABBAGE_ROLL_INGREDIENTS), Ingredient.of(CommonTags.FOODS_BELL_PEPPER));
    }
}
