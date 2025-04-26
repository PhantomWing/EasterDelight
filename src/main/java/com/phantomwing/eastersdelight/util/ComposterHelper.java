package com.phantomwing.eastersdelight.util;

import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;

public class ComposterHelper {
    private static void registerCompostableItems (float chance, ItemConvertible ...items) {
        for (ItemConvertible item : items) {
            ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item, chance);
        }
    }

    public static void register() {
        // 85% chance
        registerCompostableItems(0.85f,
                ModItems.BOILED_EGG,
                ModItems.EGG_SLICE,
                ModItems.BUNNY_COOKIE
        );
    }
}
