package com.phantomwing.eastersdelight.util;

import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class ComposterHelper {
    private static void registerCompostableItems (float chance, ItemLike...items) {
        for (ItemLike item : items) {
            ComposterBlock.COMPOSTABLES.put(item, chance);
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
