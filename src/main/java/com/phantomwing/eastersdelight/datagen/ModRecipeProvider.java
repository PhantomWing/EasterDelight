package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import com.phantomwing.eastersdelight.tags.ModTags;
import com.phantomwing.eastersdelight.util.ItemUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.crafting.CookingPotBookCategory;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public static final int FAST_COOKING = 100;
    public static final int NORMAL_COOKING = 200;
    public static final int SLOW_COOKING = 400;
    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public @NotNull String getName() {
        return "Easter's Delight Recipes";
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            final HolderGetter<Item> holderGetter = registryLookup.lookupOrThrow(Registries.ITEM);

            @Override
            public void buildRecipes() {
                buildCraftingRecipes(output);
                buildCuttingRecipes(output);
                buildCookingRecipes(output);
                buildFarmersDelightOverrideRecipes(output);
            }

            private void buildCraftingRecipes(@NotNull RecipeOutput output) {
                // Egg Painter
                shaped(RecipeCategory.MISC, ModItems.EGG_PAINTER, 1)
                        .pattern("/E/")
                        .pattern("###")
                        .define('E', ModItems.BOILED_EGG)
                        .define('/', Items.IRON_INGOT)
                        .define('#', ItemTags.PLANKS)
                        .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModItems.BOILED_EGG))
                        .save(output, ResourceKey.create(Registries.RECIPE, ItemUtils.getIdentifier(ModItems.EGG_PAINTER)));

                // Chocolate Egg
                shapeless(RecipeCategory.FOOD, ModItems.CHOCOLATE_EGG, 8)
                        .requires(Items.COCOA_BEANS)
                        .requires(Items.COCOA_BEANS)
                        .requires(ModItems.BOILED_EGG)
                        .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModItems.BOILED_EGG))
                        .save(output);

                // Bunny Cookie
                shapeless(RecipeCategory.FOOD, ModItems.BUNNY_COOKIE, 8)
                        .requires(Items.COCOA_BEANS)
                        .requires(Items.RABBIT_HIDE)
                        .requires(Items.WHEAT)
                        .requires(Items.WHEAT)
                        .unlockedBy(getHasName(Items.RABBIT_HIDE), has(Items.RABBIT_HIDE))
                        .save(output);
            }

            private void buildCuttingRecipes(@NotNull RecipeOutput output) {
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BOILED_EGG), tagIngredient(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                        .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                        .build(output, ItemUtils.getItemIdentifier(ModItems.EGG_SLICE));

                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.DYED_EGG), tagIngredient(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                        .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                        .build(output, ItemUtils.getNameWithNamespace(ModItems.EGG_SLICE) + "_from_" + ItemUtils.getName(ModItems.DYED_EGG));
            }

            private void buildCookingRecipes(@NotNull RecipeOutput output) {
                // Boiled Egg (Can place up to six eggs in a pot to cook them)
                for (int eggCount = 1; eggCount <= 6; eggCount++) {
                    CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, ModItems.BOILED_EGG, eggCount, NORMAL_COOKING, MEDIUM_EXP, null)
                            .addIngredient(Items.EGG, eggCount)
                            .unlockedByAnyIngredient(Items.EGG)
                            .setRecipeBookCategory(CookingPotBookCategory.MISC)
                            .build(output, ItemUtils.getNameWithNamespace(ModItems.BOILED_EGG) + "_" + eggCount);
                }
            }

            private void buildFarmersDelightOverrideRecipes(@NotNull RecipeOutput output) {
                // Farmer's Delight overrides, to include Boiled Eggs
                // Baked Cod Stew
                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, vectorwing.farmersdelight.common.registry.ModItems.BAKED_COD_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                        .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.Items.FOODS_RAW_COD)
                        .addIngredient(CommonTags.FOODS_POTATO)
                        .addIngredient(ModTags.Items.BAKED_COD_STEW_INGREDIENTS)
                        .addIngredient(vectorwing.farmersdelight.common.tag.CommonTags.Items.CROPS_TOMATO)
                        .unlockedByAnyIngredient(Items.COD, Items.POTATO, vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get(), Items.EGG)
                        .setRecipeBookCategory(CookingPotBookCategory.MEALS)
                        .save(output);
            }

            private Ingredient tagIngredient(TagKey<Item> tag) {
                return Ingredient.of(holderGetter.getOrThrow(tag));
            }
        };
    }
}
