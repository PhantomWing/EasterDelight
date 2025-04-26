package com.phantomwing.eastersdelight.item;

import com.google.common.collect.Sets;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import com.phantomwing.eastersdelight.food.FoodValues;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashSet;

public class ModItems {
    public static final int EGG_STACK_SIZE = 16;
    public static final int BOWL_STACK_SIZE = 16;
    public static final int BOTTLE_STACK_SIZE = 16;

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
    public static Item.Settings baseItem() {
        return new Item.Settings();
    }

    public static Item.Settings bottleItem() {
        return baseItem().recipeRemainder(Items.GLASS_BOTTLE).maxCount(BOTTLE_STACK_SIZE);
    }

    public static Item.Settings bowlItem() {
        return baseItem().recipeRemainder(Items.BOWL).maxCount(BOWL_STACK_SIZE);
    }

    public static Item.Settings feastItem() {
        return baseItem().recipeRemainder(Items.BOWL).maxCount(1);
    }

    // Registry functions
    private static Item registerWithTab(String name, Item item) {
        Item registeredItem = Registry.register(Registries.ITEM, Identifier.of(EastersDelight.MOD_ID, name), item);

        CREATIVE_TAB_ITEMS.add(registeredItem);

        return registeredItem;
    }

    private static Item registerBlockWithTab(Block block) {
        String name = Registries.BLOCK.getId(block).getPath();
        Item item = Registry.register(Registries.ITEM, Identifier.of(EastersDelight.MOD_ID, name),
                new BlockItem(block, baseItem()));

        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    private static Item registerBlockWithTab(Block block, Item.Settings settings) {
        String name = Registries.BLOCK.getId(block).getPath();
        Item item = Registry.register(Registries.ITEM, Identifier.of(EastersDelight.MOD_ID, name),
                new BlockItem(block, settings));

        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    public static void registerModItems() {
        EastersDelight.LOGGER.info("Registering items for " + EastersDelight.MOD_ID);
    }
}
