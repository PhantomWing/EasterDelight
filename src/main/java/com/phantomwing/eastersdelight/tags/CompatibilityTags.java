package com.phantomwing.eastersdelight.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import vectorwing.farmersdelight.FarmersDelight;

/**
 * References to tags belonging to other mods, which Easter's Delight innately supports.
 * These tags are used by other mods for their own mechanics.
 */
public class CompatibilityTags
{
    public static final String FORGE = "forge";
    public static final String FARMERS_DELIGHT = FarmersDelight.MODID;

    // Create
    public static final String CREATE = "create";
    public static final TagKey<Item> CREATE_UPRIGHT_ON_BELT = externalItemTag(CREATE, "upright_on_belt");

    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(modId, path));
    }

    private static TagKey<Block> externalBlockTag(String modId, String path) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(modId, path));
    }
}