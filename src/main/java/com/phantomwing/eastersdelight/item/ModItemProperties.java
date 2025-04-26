package com.phantomwing.eastersdelight.item;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;



public class ModItemProperties {
    static class UnclampedItemPropertyFunction<T> implements ClampedItemPropertyFunction {
        DataComponentType<T> componentType;
        ToIntFunction<T> toIntFunction;

        public UnclampedItemPropertyFunction(DataComponentType<T> componentType, ToIntFunction<T> toIntFunction) {
            this.componentType = componentType;
            this.toIntFunction = toIntFunction;
        }

        @Override
        public float call(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            return unclampedCall(itemStack, clientLevel, livingEntity, i);
        }

        @Override
        public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            T color = itemStack.get(componentType);
            return color != null ? (float) toIntFunction.applyAsInt(color) : -1f;
        }
    }

    public static final ResourceLocation BASE_COLOR = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "base_color");
    public static final ResourceLocation PATTERN_COLOR = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "pattern_color");
    public static final ResourceLocation EGG_PATTERN = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "egg_pattern");

    public static void register() {
        EastersDelight.LOGGER.info("Registering item properties for " + EastersDelight.MOD_ID);

        registerEasterEggProperties();
        registerEggPatternProperties();
    }

    private static void registerEasterEggProperties() {
        ItemProperties.register(ModItems.DYED_EGG, BASE_COLOR, new UnclampedItemPropertyFunction<>(DataComponents.BASE_COLOR, DyeColor::getId));
        ItemProperties.register(ModItems.DYED_EGG, PATTERN_COLOR, new UnclampedItemPropertyFunction<>(ModDataComponents.PATTERN_COLOR, DyeColor::getId));
        ItemProperties.register(ModItems.DYED_EGG, EGG_PATTERN, new UnclampedItemPropertyFunction<>(ModDataComponents.EGG_PATTERN, EggPattern::getId));
    }

    private static void registerEggPatternProperties() {
        ItemProperties.register(ModItems.EGG_PATTERN, EGG_PATTERN, new UnclampedItemPropertyFunction<>(ModDataComponents.EGG_PATTERN, EggPattern::getId));
    }

}
