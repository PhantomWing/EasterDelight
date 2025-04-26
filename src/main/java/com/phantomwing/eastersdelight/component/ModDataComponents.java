package com.phantomwing.eastersdelight.component;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final ComponentType<DyeColor> PATTERN_COLOR =
            register("pattern_color", builder -> builder.codec(DyeColor.CODEC));

    public static final ComponentType<EggPattern> EGG_PATTERN =
            register("egg_pattern", builder -> builder.codec(EggPattern.CODEC));

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(EastersDelight.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering Data Component Types for " + EastersDelight.MOD_ID);
    }
}
