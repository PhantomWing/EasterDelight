package com.phantomwing.eastersdelight.item;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static final Identifier BASE_COLOR = Identifier.of(EastersDelight.MOD_ID, "base_color");
    public static final Identifier PATTERN_COLOR = Identifier.of(EastersDelight.MOD_ID, "pattern_color");
    public static final Identifier EGG_PATTERN = Identifier.of(EastersDelight.MOD_ID, "egg_pattern");

    public static void register() {
        registerEasterEggProperties();
        registerEggPatternProperties();
    }

    private static void registerEasterEggProperties() {
        ModelPredicateProviderRegistry.register( ModItems.DYED_EGG, BASE_COLOR, (stack, world, entity, seed) -> {
            DyeColor color = stack.get(DataComponentTypes.BASE_COLOR);
            return color != null ? color.getId() : -1f;
        });

        ModelPredicateProviderRegistry.register(ModItems.DYED_EGG, PATTERN_COLOR, (stack, world, entity, seed) -> {
            DyeColor color = stack.get(ModDataComponents.PATTERN_COLOR);
            return color != null ? color.getId() : -1f;
        });

        ModelPredicateProviderRegistry.register(ModItems.DYED_EGG, EGG_PATTERN, (stack, world, entity, seed) -> {
            EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);
            return pattern != null ? pattern.getId() : -1f;
        });
    }

    private static void registerEggPatternProperties() {
        ModelPredicateProviderRegistry.register(ModItems.EGG_PATTERN, EGG_PATTERN, (stack, world, entity, seed) -> {
            EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);
            return pattern != null ? pattern.getId() : -1f;
        });
    }
}
