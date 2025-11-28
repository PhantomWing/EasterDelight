package com.phantomwing.eastersdelight.item;

import com.google.common.collect.Sets;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import com.phantomwing.eastersdelight.food.FoodValues;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.LinkedHashSet;

public class ModItems {
    public static LinkedHashSet<Item> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Blocks
    public static final Item EGG_PAINTER = registerBlockWithTab(ModBlocks.EGG_PAINTER);

    // Eggs
    public static final Item BOILED_EGG = registerWithTab("boiled_egg", new Item(
            baseItem().food(FoodValues.BOILED_EGG)));
    public static final Item EGG_SLICE = registerWithTab("egg_slice", new Item(
            baseItem().food(FoodValues.EGG_SLICE)));

    // Food
    public static final Item CHOCOLATE_EGG = registerWithTab("chocolate_egg", new Item(
            baseItem().food(FoodValues.CHOCOLATE_EGG)));
    public static final Item BUNNY_COOKIE = registerWithTab("bunny_cookie", new Item(
            baseItem().food(vectorwing.farmersdelight.common.FoodValues.COOKIES)));

    // Patterns
    public static final Item EGG_PATTERN = registerWithTab("egg_pattern", new EggPatternItem(
            baseItem()));

    // Dyed eggs
    public static final Item DYED_EGG = registerWithTab("dyed_egg", new DyedEggItem(
            baseItem().food(FoodValues.BOILED_EGG)));


    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    // Registry functions
    private static Item registerWithTab(String name, Item item) {
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EastersDelight.MOD_ID, name), item);

        CREATIVE_TAB_ITEMS.add(registeredItem);

        return registeredItem;
    }

    private static Item registerBlockWithTab(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EastersDelight.MOD_ID, name),
                new BlockItem(block, baseItem()));

        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    public static void registerModItems() {
        EastersDelight.LOGGER.info("Registering items for " + EastersDelight.MOD_ID);
    }
}
