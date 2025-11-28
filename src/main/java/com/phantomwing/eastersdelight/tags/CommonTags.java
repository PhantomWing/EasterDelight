package com.phantomwing.eastersdelight.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CommonTags {
    public static final TagKey<Item> FOODS_MILK = commonItemTag("foods/milk");
    public static final TagKey<Item> FOODS_DOUGH = commonItemTag("foods/dough");
    public static final TagKey<Item> FOODS_PASTA = commonItemTag("foods/pasta");
    public static final TagKey<Item> FOODS_RAW_BEEF = commonItemTag("foods/raw_beef");
    public static final TagKey<Item> FOODS_RAW_CHICKEN = commonItemTag("foods/raw_chicken");
    public static final TagKey<Item> FOODS_LEAFY_GREEN = commonItemTag("foods/leafy_green");
    public static final TagKey<Item> FOODS_POTATO = commonItemTag("foods/potato");
    public static final TagKey<Item> FOODS_CARROT = commonItemTag("foods/carrot");
    public static final TagKey<Item> FOODS_ONION = commonItemTag("foods/onion");
    public static final TagKey<Item> FOODS_TOMATO = commonItemTag("foods/tomato");
    public static final TagKey<Item> FOODS_BELL_PEPPER = commonItemTag("foods/bell_pepper");
    public static final TagKey<Item> FOODS_COOKIE = commonItemTag("foods/cookie");

    public static final TagKey<Item> FOODS_COOKED_EGG = commonItemTag("foods/cooked_egg");
    public static final TagKey<Item> FOODS_BOILED_EGG = commonItemTag("foods/boiled_egg");

    public static final TagKey<Item> TOOLS_KNIFE = commonItemTag("tools/knife");

    private static TagKey<Block> commonBlockTag(String path) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("c", path));
    }

    private static TagKey<Item> commonItemTag(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", path));
    }
}
