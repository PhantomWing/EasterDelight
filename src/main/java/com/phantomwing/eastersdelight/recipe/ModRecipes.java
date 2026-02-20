package com.phantomwing.eastersdelight.recipe;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, EastersDelight.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeEggRecipe>> DYE_EGG_RECIPE =
            registerRecipe("dye_paintable_egg", () -> new SimpleCraftingRecipeSerializer<>(DyeEggRecipe::new));

    private static <T extends Recipe<?>> DeferredHolder<RecipeSerializer<?>, RecipeSerializer<T>> registerRecipe(String name, Supplier<? extends RecipeSerializer<T>> supplier) {
        return RECIPE_SERIALIZERS.register(name, supplier);
    }

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
