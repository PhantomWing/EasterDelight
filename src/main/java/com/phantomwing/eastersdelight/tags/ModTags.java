package com.phantomwing.eastersdelight.tags;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Item tags
    public static class Items {
        public static final TagKey<Item> PAINTABLE_EGGS = tag("paintable_eggs");
        public static final TagKey<Item> BAKED_COD_STEW_INGREDIENTS = tag("baked_cod_stew_ingredients");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name));
        }
    }
}