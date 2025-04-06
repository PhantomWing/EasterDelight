package com.phantomwing.eastersdelight.item;

import com.google.common.collect.Sets;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import com.phantomwing.eastersdelight.food.FoodValues;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {
    public static final int EGG_STACK_SIZE = 16;
    public static final int BOWL_STACK_SIZE = 16;
    public static final int BOTTLE_STACK_SIZE = 16;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EastersDelight.MOD_ID);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Blocks
    public static final RegistryObject<Item> EGG_PAINTER = registerBlockWithTab(ModBlocks.EGG_PAINTER);

    // Eggs
    public static final RegistryObject<Item> BOILED_EGG = registerWithTab("boiled_egg", () -> new Item(
            baseItem().food(FoodValues.BOILED_EGG)));
    public static final RegistryObject<Item> EGG_SLICE = registerWithTab("egg_slice", () -> new Item(
            baseItem().food(FoodValues.EGG_SLICE)));

    // Food
    public static final RegistryObject<Item> CHOCOLATE_EGG = registerWithTab("chocolate_egg", () -> new Item(
            baseItem().food(FoodValues.CHOCOLATE_EGG)));
    public static final RegistryObject<Item> BUNNY_COOKIE = registerWithTab("bunny_cookie", () -> new Item(
            baseItem().food(vectorwing.farmersdelight.common.FoodValues.COOKIES)));

    // Patterns
    public static final RegistryObject<Item> EGG_PATTERN = registerWithTab("egg_pattern", () -> new EggPatternItem(
            baseItem()));

    // Dyed eggs
    public static final RegistryObject<Item> DYED_EGG = registerWithTab("dyed_egg", () -> new DyedEggItem(
            baseItem().food(FoodValues.BOILED_EGG)));


    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    public static Item.Properties bottleItem() {
        return baseItem().craftRemainder(Items.GLASS_BOTTLE).stacksTo(BOTTLE_STACK_SIZE);
    }

    public static Item.Properties bowlItem() {
        return baseItem().craftRemainder(Items.BOWL).stacksTo(BOWL_STACK_SIZE);
    }

    public static Item.Properties feastItem() {
        return baseItem().craftRemainder(Items.BOWL).stacksTo(1);
    }

    // Registry functions
    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static RegistryObject<Item> registerBlockWithTab(RegistryObject<Block> block) {
        return registerWithTab(block.getId().getPath().replaceFirst(EastersDelight.MOD_ID + ":", ""), () -> new BlockItem(block.get(), baseItem()));
    }

    public static RegistryObject<Item> registerBlockWithTab(RegistryObject<Block> block,  net.minecraft.world.item.Item.Properties properties) {
        return registerWithTab(block.getId().getPath().replaceFirst(EastersDelight.MOD_ID + ":", ""), () -> new BlockItem(block.get(), properties));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
