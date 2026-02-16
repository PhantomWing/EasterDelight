package com.phantomwing.eastersdelight.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.FarmersDelight;

/**
 * References to tags belonging to other mods, which Easter's Delight innately supports.
 * These tags are used by other mods for their own mechanics.
 */
public class CompatibilityTags
{
    public static final String FORGE = "forge";
    public static final String FARMERS_DELIGHT = FarmersDelight.MODID;
    public static final String SUPPLEMENTARIES = "supplementaries";


    // Create
    public static final String CREATE = "create";
    public static final TagKey<Item> CREATE_UPRIGHT_ON_BELT = externalItemTag(CREATE, "upright_on_belt");

    public static final TagKey<Item> SUPPLEMENTARIES_COOKIES = externalItemTag(SUPPLEMENTARIES, "cookies");


    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modId, path));
    }

    private static TagKey<Block> externalBlockTag(String modId, String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modId, path));
    }
}