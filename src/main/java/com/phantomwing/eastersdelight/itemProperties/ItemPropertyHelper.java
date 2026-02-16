package com.phantomwing.eastersdelight.itemProperties;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class ItemPropertyHelper {
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

    public static void register() {
        EastersDelight.LOGGER.info("Registering item properties for " + EastersDelight.MOD_ID);

        // Dyed Egg properties: base color, pattern color, and pattern type.
        ItemProperties.register(ModItems.DYED_EGG, ModItemProperties.BASE_COLOR, new UnclampedItemPropertyFunction<>(DataComponents.BASE_COLOR, DyeColor::getId));
        ItemProperties.register(ModItems.DYED_EGG, ModItemProperties.PATTERN_COLOR, new UnclampedItemPropertyFunction<>(ModDataComponents.PATTERN_COLOR, DyeColor::getId));
        ItemProperties.register(ModItems.DYED_EGG, ModItemProperties.EGG_PATTERN, new UnclampedItemPropertyFunction<>(ModDataComponents.EGG_PATTERN, EggPattern::getId));

        // Egg Pattern item property: pattern type.
        ItemProperties.register(ModItems.EGG_PATTERN, ModItemProperties.EGG_PATTERN, new UnclampedItemPropertyFunction<>(ModDataComponents.EGG_PATTERN, EggPattern::getId));
    }
}
