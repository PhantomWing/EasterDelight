package com.phantomwing.eastersdelight.tags;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Block tags
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name));
        }
    }

    // Item tags
    public static class Items {
        public static final TagKey<Item> PAINTABLE_EGGS = tag("paintable_eggs");
        public static final TagKey<Item> BAKED_COD_STEW_INGREDIENTS = tag("baked_cod_stew_ingredients");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name));
        }
    }
}