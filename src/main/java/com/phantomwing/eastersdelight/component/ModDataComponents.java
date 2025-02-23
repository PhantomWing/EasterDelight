package com.phantomwing.eastersdelight.component;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(EastersDelight.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DyeColor>> PATTERN_COLOR =
            register("pattern_color", builder -> builder.persistent(DyeColor.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EggPattern>> EGG_PATTERN =
            register("egg_pattern", builder -> builder.persistent(EggPattern.CODEC));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
