package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.ForgeTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

import static vectorwing.farmersdelight.data.recipe.CookingRecipes.MEDIUM_EXP;
import static vectorwing.farmersdelight.data.recipe.CookingRecipes.NORMAL_COOKING;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> output) {
        buildCraftingRecipes(output);
        buildCuttingRecipes(output);
        buildCookingRecipes(output);
    }

    private void buildCraftingRecipes(Consumer<FinishedRecipe> output) {
        // Egg Painter
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EGG_PAINTER.get(), 1)
                .pattern("/E/")
                .pattern("###")
                .define('E', ModItems.BOILED_EGG.get())
                .define('/', Items.IRON_INGOT)
                .define('#', ItemTags.PLANKS)
                .unlockedBy(getHasName(ModItems.BOILED_EGG.get()), has(ModItems.BOILED_EGG.get()))
                .save(output, ModItems.EGG_PAINTER.getId());

        // Chocolate Egg
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHOCOLATE_EGG.get(), 8)
                .requires(Items.COCOA_BEANS)
                .requires(Items.COCOA_BEANS)
                .requires(ModItems.BOILED_EGG.get())
                .unlockedBy(getHasName(ModItems.BOILED_EGG.get()), has(ModItems.BOILED_EGG.get()))
                .save(output);

        // Bunny Cookie
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BUNNY_COOKIE.get(), 8)
                .requires(Items.COCOA_BEANS)
                .requires(Items.RABBIT_HIDE)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(Items.RABBIT_HIDE), has(Items.RABBIT_HIDE))
                .save(output);
    }

    private void buildCuttingRecipes(Consumer<FinishedRecipe> output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BOILED_EGG.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.EGG_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                .build(output, ModItems.EGG_SLICE.getId());

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.DYED_EGG.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.EGG_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                .build(output, ModItems.EGG_SLICE.getId() + "_from_" + ModItems.DYED_EGG.getId().getPath());
    }

    private void buildCookingRecipes(Consumer<FinishedRecipe> output) {
        // Boiled Egg (Can place up to six eggs in a pot to cook them)
        for (int eggCount = 1; eggCount <= 6; eggCount++)
        {
            CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BOILED_EGG.get(), eggCount, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.EGG, eggCount)
                .unlockedByAnyIngredient(Items.EGG)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output, ModItems.BOILED_EGG.getId() + "_" + eggCount);
        }

        // Farmer's Delight overrides, to include Boiled Eggs
        // Baked Cod Stew
        CookingPotRecipeBuilder.cookingPotRecipe(vectorwing.farmersdelight.common.registry.ModItems.BAKED_COD_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.tag.ForgeTags.RAW_FISHES_COD)
                .addIngredient(ForgeTags.VEGETABLES_POTATO)
                .addIngredient(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
                .addIngredient(vectorwing.farmersdelight.common.tag.ForgeTags.CROPS_TOMATO)
                .unlockedByAnyIngredient(Items.COD, Items.POTATO, vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(), Items.EGG)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
    }
}
