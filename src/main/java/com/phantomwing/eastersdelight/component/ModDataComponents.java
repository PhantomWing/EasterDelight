package com.phantomwing.eastersdelight.component;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DataComponentType<DyeColor> PATTERN_COLOR =
            register("pattern_color", builder -> builder.persistent(DyeColor.CODEC).networkSynchronized(DyeColor.STREAM_CODEC).cacheEncoding());

    public static final DataComponentType<EggPattern> EGG_PATTERN =
            register("egg_pattern", builder -> builder.persistent(EggPattern.CODEC).networkSynchronized(EggPattern.STREAM_CODEC).cacheEncoding());

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name),
                builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering Data Component Types for " + EastersDelight.MOD_ID);
    }
}
