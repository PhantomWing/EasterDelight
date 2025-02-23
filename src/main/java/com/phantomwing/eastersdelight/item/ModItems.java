package com.phantomwing.eastersdelight.item;

import com.google.common.collect.Sets;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.item.custom.EasterEggItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.phantomwing.eastersdelight.food.FoodValues;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {
    public static final int EGG_STACK_SIZE = 16;
    public static final int BOWL_STACK_SIZE = 16;
    public static final int BOTTLE_STACK_SIZE = 16;

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EastersDelight.MOD_ID);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Boiled eggs
    public static final DeferredItem<Item> BOILED_EGG = registerWithTab("boiled_egg", () -> new Item(
            baseItem().food(FoodValues.BOILED_EGG)));

    // Base easter eggs
    public static final DeferredItem<Item> EASTER_EGG = registerWithTab("easter_egg", () -> new EasterEggItem(
            baseItem().component(DataComponents.BASE_COLOR, DyeColor.WHITE).food(FoodValues.BOILED_EGG)));

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
    public static DeferredItem<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        DeferredItem<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static DeferredItem<Item> registerBlockWithTab(DeferredBlock<Block> block) {
        return registerWithTab(block.getRegisteredName().replaceFirst(EastersDelight.MOD_ID + ":", ""), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static DeferredItem<Item> registerBlockWithTab(DeferredBlock<Block> block, Item.Properties properties) {
        return registerWithTab(block.getRegisteredName().replaceFirst(EastersDelight.MOD_ID + ":", ""), () -> new BlockItem(block.get(), properties));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
