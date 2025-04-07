package com.phantomwing.eastersdelight.item;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class ModItemProperties {
    public static final ResourceLocation BASE_COLOR = new ResourceLocation(EastersDelight.MOD_ID, "base_color");
    public static final ResourceLocation PATTERN_COLOR = new ResourceLocation(EastersDelight.MOD_ID, "pattern_color");
    public static final ResourceLocation EGG_PATTERN = new ResourceLocation(EastersDelight.MOD_ID, "egg_pattern");

    public static void register() {
        registerEasterEggProperties();
        registerEggPatternProperties();
    }

    private static void registerEasterEggProperties() {
        ItemProperties.register(ModItems.DYED_EGG.get(), BASE_COLOR, (stack, world, entity, seed) -> {
            CompoundTag compoundTag = stack.getOrCreateTag();
            String colorName = compoundTag.getString(ModDataComponents.BASE_COLOR);

            return colorName.isEmpty() ? -1f : DyeColor.byName(colorName, DyeColor.WHITE).getId();
        });

        ItemProperties.register(ModItems.DYED_EGG.get(), PATTERN_COLOR, (stack, world, entity, seed) -> {
            CompoundTag compoundTag = stack.getOrCreateTag();
            String colorName = compoundTag.getString(ModDataComponents.PATTERN_COLOR);

            return colorName.isEmpty() ? -1f : DyeColor.byName(colorName, DyeColor.WHITE).getId();
        });

        ItemProperties.register(ModItems.DYED_EGG.get(), EGG_PATTERN, (stack, world, entity, seed) -> {
            CompoundTag compoundTag = stack.getOrCreateTag();
            String patternName = compoundTag.getString(ModDataComponents.EGG_PATTERN);

            return patternName.isEmpty() ? -1f : EggPattern.byName(patternName, EggPattern.STRIPES).getId();
        });
    }

    private static void registerEggPatternProperties() {
        ItemProperties.register(ModItems.EGG_PATTERN.get(), EGG_PATTERN, (stack, world, entity, seed) -> {
            CompoundTag tag = stack.getOrCreateTag();
            String colorName = tag.getString(ModDataComponents.EGG_PATTERN);
            EggPattern pattern = colorName.isEmpty() ? null : EggPattern.byName(tag.getString(ModDataComponents.EGG_PATTERN), EggPattern.STRIPES);

            return pattern != null ? pattern.getId() : -1f;
        });
    }

}
