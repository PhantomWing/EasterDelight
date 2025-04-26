package com.phantomwing.eastersdelight.food;

import net.minecraft.component.type.FoodComponent;

public class FoodValues {
    // Basic foods
    public static final FoodComponent BOILED_EGG = (new FoodComponent .Builder())
            .nutrition(4).saturationModifier(0.3F).build();
    public static final FoodComponent  EGG_SLICE = (new FoodComponent .Builder())
            .nutrition(2).saturationModifier(0.3F).build();
    public static final FoodComponent  CHOCOLATE_EGG = (new FoodComponent .Builder())
            .nutrition(5).saturationModifier(0.4F).build();
}
