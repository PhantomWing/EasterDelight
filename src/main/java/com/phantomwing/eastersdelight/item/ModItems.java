package com.phantomwing.eastersdelight.item;

import com.google.common.collect.Sets;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import com.phantomwing.eastersdelight.food.FoodValues;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.function.Function;

public class ModItems {
    public static LinkedHashSet<Item> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Blocks
    public static final Item EGG_PAINTER = registerBlockWithTab(ModBlocks.EGG_PAINTER);

    // Eggs
    public static final Item BOILED_EGG = registerWithTab("boiled_egg", baseItem().food(FoodValues.BOILED_EGG), Item::new);
    public static final Item EGG_SLICE = registerWithTab("egg_slice", baseItem().food(FoodValues.EGG_SLICE), Item::new);

    // Food
    public static final Item CHOCOLATE_EGG = registerWithTab("chocolate_egg", baseItem().food(FoodValues.CHOCOLATE_EGG), Item::new);
    public static final Item BUNNY_COOKIE = registerWithTab("bunny_cookie", baseItem().food(vectorwing.farmersdelight.common.FoodValues.COOKIES), Item::new);

    // Patterns
    public static final Item EGG_PATTERN = registerWithTab("egg_pattern", baseItem(), EggPatternItem::new);

    // Dyed eggs
    public static final Item DYED_EGG = registerDyedEggWithTab(ModBlocks.DYED_EGG);

    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    public static Item.Properties foodItem(FoodProperties food) {
        return foodItem(food, null);
    }

    public static Item.Properties foodItem(FoodProperties food, @Nullable Consumable consumable) {
        return foodItem(food).component(DataComponents.CONSUMABLE, consumable != null ? consumable : Consumables.DEFAULT_FOOD);
    }

    // Registry functions
    private static Item registerWithTab(String name, Item.Properties props, Function<Item.Properties, Item> function) {
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name);
        props.setId(ResourceKey.create(Registries.ITEM, loc));

        Item item = function.apply(props);
        CREATIVE_TAB_ITEMS.add(item);

        return Registry.register(BuiltInRegistries.ITEM, loc, item);
    }

    private static Item registerBlockWithTab(Block block) {
        return registerBlockWithTab(block, baseItem());
    }

    private static Item registerBlockWithTab(Block block, Item.Properties props) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name);

        props.useBlockDescriptionPrefix();
        props.setId(ResourceKey.create(Registries.ITEM, loc));

        BlockItem item = new BlockItem(block, props);
        CREATIVE_TAB_ITEMS.add(item);

        return Registry.register(BuiltInRegistries.ITEM, loc, item);
    }

    private static Item registerDyedEggWithTab(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name);

        Item.Properties props = baseItem().food(FoodValues.BOILED_EGG);
        props.useBlockDescriptionPrefix();
        props.setId(ResourceKey.create(Registries.ITEM, loc));

        BlockItem item = new DyedEggItem(block, props);
        CREATIVE_TAB_ITEMS.add(item);

        return Registry.register(BuiltInRegistries.ITEM, loc, item);
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering items for " + EastersDelight.MOD_ID);
    }
}
