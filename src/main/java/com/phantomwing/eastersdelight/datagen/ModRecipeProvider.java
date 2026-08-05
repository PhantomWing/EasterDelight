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
                // FDR recipe overrides moved to ModFarmersDelightOverrideRecipeProvider, attached to
                // a built-in resource pack so its files take precedence over FDR's bundled recipes
                // (Fabric Loader's regular mod-pack alphabetical order would otherwise let FDR win
                // since "eastersdelight" < "farmersdelight").
            }

            private void buildCraftingRecipes(@NotNull RecipeOutput output) {
                // Egg Painter
                shaped(RecipeCategory.MISC, ModItems.EGG_PAINTER, 1)
                        .pattern("/E/")
                        .pattern("###")
                        .define('E', ModTags.Items.BOILED_EGGS)
                        .define('/', Items.IRON_INGOT)
                        .define('#', ItemTags.PLANKS)
                        .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModTags.Items.BOILED_EGGS))
                        .save(output, ResourceKey.create(Registries.RECIPE, ItemUtils.getIdentifier(ModItems.EGG_PAINTER)));

                // Chocolate Egg
                shapeless(RecipeCategory.FOOD, ModItems.CHOCOLATE_EGG, 8)
                        .requires(Items.COCOA_BEANS)
                        .requires(Items.COCOA_BEANS)
                        .requires(ModTags.Items.BOILED_EGGS)
                        .unlockedBy(getHasName(ModItems.BOILED_EGG), has(ModTags.Items.BOILED_EGGS))
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
                // One recipe per shell color rather than a tag ingredient, so each shows up
                // separately in the recipe viewer.
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BOILED_EGG), tagIngredient(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                        .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                        .build(output, ItemUtils.getItemIdentifier(ModItems.EGG_SLICE));

                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BOILED_BROWN_EGG), tagIngredient(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                        .addResult(Items.BONE_MEAL)
                        .build(output, ItemUtils.getNameWithNamespace(ModItems.EGG_SLICE) + "_from_" + ItemUtils.getName(ModItems.BOILED_BROWN_EGG));

                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BOILED_BLUE_EGG), tagIngredient(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                        .addResult(Items.BONE_MEAL)
                        .build(output, ItemUtils.getNameWithNamespace(ModItems.EGG_SLICE) + "_from_" + ItemUtils.getName(ModItems.BOILED_BLUE_EGG));

                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.DYED_EGG), tagIngredient(CommonTags.TOOLS_KNIFE), ModItems.EGG_SLICE, 2)
                        .addResult(Items.BONE_MEAL) // Eggshells can be used in the form of Bone Meal
                        .build(output, ItemUtils.getNameWithNamespace(ModItems.EGG_SLICE) + "_from_" + ItemUtils.getName(ModItems.DYED_EGG));
            }

            private void buildCookingRecipes(@NotNull RecipeOutput output) {
                // Boiled Egg (Can place up to six eggs in a pot to cook them). One chain per shell
                // color, so a brown egg boils into a brown boiled egg rather than a plain one.
                boiledEggRecipes(output, Items.EGG, ModItems.BOILED_EGG);
                boiledEggRecipes(output, Items.BROWN_EGG, ModItems.BOILED_BROWN_EGG);
                boiledEggRecipes(output, Items.BLUE_EGG, ModItems.BOILED_BLUE_EGG);
            }

            private void boiledEggRecipes(@NotNull RecipeOutput output, Item rawEgg, Item boiledEgg) {
                for (int eggCount = 1; eggCount <= 6; eggCount++) {
                    CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, boiledEgg, eggCount, NORMAL_COOKING, MEDIUM_EXP, null)
                            .addIngredient(rawEgg, eggCount)
                            .unlockedByAnyIngredient(rawEgg)
                            .setRecipeBookCategory(CookingPotBookCategory.MISC)
                            .build(output, ItemUtils.getNameWithNamespace(boiledEgg) + "_" + eggCount);
                }
            }

            private Ingredient tagIngredient(TagKey<Item> tag) {
                return Ingredient.of(holderGetter.getOrThrow(tag));
            }
        };
    }
}
