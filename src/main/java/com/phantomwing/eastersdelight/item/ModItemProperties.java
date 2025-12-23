package com.phantomwing.eastersdelight.item;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.utils.EggUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class ModItemProperties {
    static class UnclampedItemPropertyFunction<T extends StringRepresentable> implements ClampedItemPropertyFunction {
        String componentType;
        ToIntFunction<T> toIntFunction;
        ToTFunction<T> toTFunction;

        public UnclampedItemPropertyFunction(String componentType, ToTFunction<T> toTFunction, ToIntFunction<T> toIntFunction) {
            this.componentType = componentType;
            this.toIntFunction = toIntFunction;
            this.toTFunction = toTFunction;
        }

        @Override
        public float call(@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            return unclampedCall(itemStack, clientLevel, livingEntity, i);
        }

        @Override
        public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            CompoundTag compoundTag = itemStack.getOrCreateTag();
            String colorName = compoundTag.getString(componentType);
            T color = this.toTFunction.byName(colorName, null);
            return color != null ? (float) toIntFunction.applyAsInt(color) : -1f;
        }
    }

    public static final ResourceLocation BASE_COLOR = new ResourceLocation(EastersDelight.MOD_ID, EggUtils.BASE_COLOR);
    public static final ResourceLocation PATTERN_COLOR = new ResourceLocation(EastersDelight.MOD_ID, EggUtils.PATTERN_COLOR);
    public static final ResourceLocation EGG_PATTERN = new ResourceLocation(EastersDelight.MOD_ID, EggUtils.EGG_PATTERN);

    public static void register() {
        EastersDelight.LOGGER.info("Registering item properties for " + EastersDelight.MOD_ID);

        registerEasterEggProperties();
        registerEggPatternProperties();
    }

    private static void registerEasterEggProperties() {
        ItemProperties.register(ModItems.DYED_EGG.get(), BASE_COLOR, new UnclampedItemPropertyFunction<>(EggUtils.BASE_COLOR, DyeColor::byName, DyeColor::getId));
        ItemProperties.register(ModItems.DYED_EGG.get(), PATTERN_COLOR, new UnclampedItemPropertyFunction<>(EggUtils.PATTERN_COLOR, DyeColor::byName, DyeColor::getId));
        ItemProperties.register(ModItems.DYED_EGG.get(), EGG_PATTERN, new UnclampedItemPropertyFunction<>(EggUtils.EGG_PATTERN, EggPattern::byName, EggPattern::getId));
    }

    private static void registerEggPatternProperties() {
        ItemProperties.register(ModItems.EGG_PATTERN.get(), EGG_PATTERN, new UnclampedItemPropertyFunction<>(EggUtils.EGG_PATTERN, EggPattern::byName, EggPattern::getId));
    }
}
