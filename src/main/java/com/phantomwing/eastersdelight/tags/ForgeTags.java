package com.phantomwing.eastersdelight.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeTags {
    public static final TagKey<Item> MILK = itemTag("milk");
    public static final TagKey<Item> VEGETABLES_POTATO = itemTag("vegetables/potato");
    public static final TagKey<Item> COOKIES = itemTag("cookies");

    public static final TagKey<Item> COOKED_EGGS = itemTag("cooked_eggs");
    public static final TagKey<Item> BOILED_EGGS = itemTag("boiled_eggs");

    public static final TagKey<Item> TOOLS_KNIVES = itemTag("tools/knives");

    private static TagKey<Block> blockTag(String path) {
        return BlockTags.create(new ResourceLocation("forge", path));
    }

    private static TagKey<Item> itemTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }
}
