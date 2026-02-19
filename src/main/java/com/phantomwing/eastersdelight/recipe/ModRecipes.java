package com.phantomwing.eastersdelight.recipe;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class ModRecipes {
    public static final RecipeSerializer<DyeEggRecipe> DYE_EGG_RECIPE =
            registerRecipe("dye_paintable_egg", new SimpleCraftingRecipeSerializer<>(DyeEggRecipe::new));

    private static <T extends Recipe<?>> RecipeSerializer<T> registerRecipe(String name, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name), serializer);
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering recipes for " + EastersDelight.MOD_ID);
    }
}
