package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.item.DyeColor;

import java.util.List;

public class BlockColorHandler
{
    // 26.1: ColorProviderRegistry was replaced by BlockColorRegistry, which takes a list of
    // BlockTintSource entries (one per tint index) instead of a single multi-index handler.
    private static final BlockTintSource BASE_COLOR_TINT = state -> {
        DyeColor baseColor = state.getValue(DyedEggBlock.BASE_COLOR);
        return baseColor.getTextureDiffuseColor();
    };

    private static final BlockTintSource PATTERN_COLOR_TINT = state -> {
        DyeColor patternColor = state.getValue(DyedEggBlock.PATTERN_COLOR);
        return patternColor.getTextureDiffuseColor();
    };

    public static void registerBlockColors() {
        BlockColorRegistry.register(List.of(BASE_COLOR_TINT, PATTERN_COLOR_TINT), ModBlocks.DYED_EGG);
    }
}
