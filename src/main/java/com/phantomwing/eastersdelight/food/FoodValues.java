package com.phantomwing.eastersdelight.food;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {
    // Basic foods
    public static final FoodProperties BOILED_EGG = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.5F).build();
    public static final FoodProperties EGG_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5F).build();
}
