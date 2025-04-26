package com.phantomwing.eastersdelight.tags;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    // Block tags
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(EastersDelight.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> PAINTABLE_EGGS = tag("paintable_eggs");
        public static final TagKey<Item> BAKED_COD_STEW_INGREDIENTS = tag("baked_cod_stew_ingredients");

        private static TagKey<Item> tag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(EastersDelight.MOD_ID, name));
        }
    }
}