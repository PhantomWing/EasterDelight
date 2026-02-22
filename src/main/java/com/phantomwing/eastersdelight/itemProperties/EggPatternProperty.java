package com.phantomwing.eastersdelight.itemProperties;

import com.mojang.serialization.MapCodec;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Environment(EnvType.CLIENT)
public record EggPatternProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<EggPatternProperty> MAP_CODEC = MapCodec.unit(new EggPatternProperty());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        EggPattern pattern = itemStack.get(ModDataComponents.EGG_PATTERN);

        return pattern != null ? pattern.getId() : -1f;
    }

    @Override
    public @NotNull MapCodec<EggPatternProperty> type() {
        return MAP_CODEC;
    }
}