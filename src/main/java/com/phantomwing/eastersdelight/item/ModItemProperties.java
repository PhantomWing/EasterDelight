package com.phantomwing.eastersdelight.item;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class ModItemProperties {
    public static final ResourceLocation BASE_COLOR = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "base_color");
    public static final ResourceLocation PATTERN_COLOR = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "pattern_color");
    public static final ResourceLocation EGG_PATTERN = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "egg_pattern");

    public static void register() {
        registerEasterEggProperties();
        registerEggPatternProperties();
    }

    private static void registerEasterEggProperties() {
        ItemProperties.register(ModItems.DYED_EGG.get(), BASE_COLOR, (stack, world, entity, seed) -> {
            DyeColor color = stack.get(DataComponents.BASE_COLOR);
            return color != null ? color.getId() : -1f;
        });

        ItemProperties.register(ModItems.DYED_EGG.get(), PATTERN_COLOR, (stack, world, entity, seed) -> {
            DyeColor color = stack.get(ModDataComponents.PATTERN_COLOR);
            return color != null ? color.getId() : -1f;
        });

        ItemProperties.register(ModItems.DYED_EGG.get(), EGG_PATTERN, (stack, world, entity, seed) -> {
            EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);
            return pattern != null ? pattern.getId() : -1f;
        });
    }

    private static void registerEggPatternProperties() {
        ItemProperties.register(ModItems.EGG_PATTERN.get(), EGG_PATTERN, (stack, world, entity, seed) -> {
            EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);
            return pattern != null ? pattern.getId() : -1f;
        });
    }

}
