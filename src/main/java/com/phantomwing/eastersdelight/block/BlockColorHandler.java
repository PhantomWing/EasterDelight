package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.DyeColor;

public class BlockColorHandler
{
    public static void registerBlockColors() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            if (tintIndex == 0) {
                DyeColor baseColor = state.getValue(DyedEggBlock.BASE_COLOR);
                return baseColor.getTextureDiffuseColor();
            } else if (tintIndex == 1) {
                DyeColor patternColor = state.getValue(DyedEggBlock.PATTERN_COLOR);

                return patternColor.getTextureDiffuseColor();
            }

            return -1; // Default color (no tint)
        }, ModBlocks.DYED_EGG);
    }
}
