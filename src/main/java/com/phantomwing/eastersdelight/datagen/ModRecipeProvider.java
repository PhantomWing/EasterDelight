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
        // FDR recipe overrides moved to ModFarmersDelightOverrideRecipeProvider, which is
        // attached to a built-in resource pack so its files take precedence over FDR's
        // (Fabric Loader's regular mod-pack alphabetical ordering would otherwise let FDR win).
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
            CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BOILED_EGG, eggCount, NORMAL_COOKING, MEDIUM_EXP, null)
                    .addIngredient(Items.EGG, eggCount)
                    .unlockedByAnyIngredient(Items.EGG)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                    .save(output, ItemUtils.getNameWithNamespace(ModItems.BOILED_EGG) + "_" + eggCount);
        }
    }

}
