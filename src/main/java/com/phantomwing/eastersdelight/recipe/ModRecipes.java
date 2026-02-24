package com.phantomwing.eastersdelight.recipe;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipes {
    public static final RecipeSerializer<DyeEggRecipe> DYE_EGG_RECIPE =
            registerRecipe("dye_paintable_egg", new CustomRecipe.Serializer<>(DyeEggRecipe::new));

    private static <T extends Recipe<?>> RecipeSerializer<T> registerRecipe(String name, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, name), serializer);
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering recipes for " + EastersDelight.MOD_ID);
    }
}
