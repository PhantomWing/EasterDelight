package com.phantomwing.eastersdelight.food;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {
    // Basic foods
    public static final FoodProperties BOILED_EGG = food(4, 0.3f);
    public static final FoodProperties EGG_SLICE = food(2, 0.3f);
    public static final FoodProperties CHOCOLATE_EGG = food(5, 0.4f);

    private static FoodProperties food(int nutrition, float saturation) {
        return (new FoodProperties.Builder())
                .nutrition(nutrition)
                .saturationModifier(saturation)
                .build();
    }
}
