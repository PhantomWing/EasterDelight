package com.phantomwing.eastersdelight.utils;

import com.phantomwing.eastersdelight.component.EggPattern;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class EggUtils {
    public static final String BASE_COLOR = "base_color";
    public static final String PATTERN_COLOR = "pattern_color";
    public static final String EGG_PATTERN = "egg_pattern";

    public static DyeColor getBaseColorFromStack(ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        String colorName = compoundTag.getString(BASE_COLOR);

        if (colorName.isEmpty()) {
            return null;
        }

        return DyeColor.byName(colorName, DyeColor.WHITE);
    }

    public static DyeColor getPatternColorFromStack(ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        String colorName = compoundTag.getString(PATTERN_COLOR);

        if (colorName.isEmpty()) {
            return null;
        }

        return DyeColor.byName(colorName, DyeColor.WHITE);
    }

    public static EggPattern getPatternFromStack(ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        String colorName = compoundTag.getString(EGG_PATTERN);

        if (colorName.isEmpty()) {
            return null;
        }

        return EggPattern.byName(colorName, EggPattern.STRIPES);
    }
}
