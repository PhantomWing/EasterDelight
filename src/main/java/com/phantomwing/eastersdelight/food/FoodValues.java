package com.phantomwing.eastersdelight.food;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {
    // Basic foods
    public static final FoodProperties BOILED_EGG = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.3F).build();
    public static final FoodProperties EGG_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties CHOCOLATE_EGG = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.4F).build();
}
