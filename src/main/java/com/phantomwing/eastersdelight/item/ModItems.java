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
import java.util.function.Supplier;

public class ModItems {
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Blocks
    public static final Supplier<Item> EGG_PAINTER = registerBlockWithTab(ModBlocks.EGG_PAINTER);

    // Eggs
    public static final Supplier<Item> BOILED_EGG = registerWithTab("boiled_egg", () -> new Item(
            baseItem().food(FoodValues.BOILED_EGG)));
    public static final Supplier<Item> EGG_SLICE = registerWithTab("egg_slice", () -> new Item(
            baseItem().food(FoodValues.EGG_SLICE)));

    // Food
    public static final Supplier<Item> CHOCOLATE_EGG = registerWithTab("chocolate_egg", () -> new Item(
            baseItem().food(FoodValues.CHOCOLATE_EGG)));
    public static final Supplier<Item> BUNNY_COOKIE = registerWithTab("bunny_cookie", () -> new Item(
            baseItem().food(vectorwing.farmersdelight.common.FoodValues.COOKIES)));

    // Patterns
    public static final Supplier<Item> EGG_PATTERN = registerWithTab("egg_pattern", () -> new EggPatternItem(
            baseItem()));

    // Dyed eggs
    public static final Supplier<Item> DYED_EGG = registerWithTab("dyed_egg", () -> new DyedEggItem(
            baseItem().food(FoodValues.BOILED_EGG)));


    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    // Registry functions
    private static Supplier<Item> registerWithTab(final String name, final Supplier<Item> item) {
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EastersDelight.MOD_ID, name), item.get());
        CREATIVE_TAB_ITEMS.add(() -> registeredItem);

        return () -> registeredItem;
    }

    private static Supplier<Item> registerBlockWithTab(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EastersDelight.MOD_ID, name), new BlockItem(block, baseItem()));

        CREATIVE_TAB_ITEMS.add(() -> registeredItem);

        return () -> registeredItem;
    }

    public static void registerModItems() {
        EastersDelight.LOGGER.info("Registering items for " + EastersDelight.MOD_ID);
    }
}
