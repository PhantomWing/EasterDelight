package com.phantomwing.eastersdelight.recipe;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, EastersDelight.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DyeEggRecipe>> DYE_EGG_RECIPE =
            registerRecipe("dye_paintable_egg", () -> new SimpleCraftingRecipeSerializer<>(DyeEggRecipe::new));

    private static <T extends Recipe<?>> RegistryObject<RecipeSerializer<T>> registerRecipe(String name, Supplier<? extends RecipeSerializer<T>> supplier) {
        return RECIPE_SERIALIZERS.register(name, supplier);
    }

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}