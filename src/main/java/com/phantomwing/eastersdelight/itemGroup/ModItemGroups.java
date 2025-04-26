package com.phantomwing.eastersdelight.itemGroup;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MOD_TAB =
            Registry.register(Registries.ITEM_GROUP, Identifier.of(EastersDelight.MOD_ID, "item_group"),
                    FabricItemGroup.builder().icon(ModItemGroups::getIconItem)
                    .displayName(Text.translatable(("itemGroup." + EastersDelight.MOD_ID)))
                    .entries((displayContext, entries) -> {
                        // Add items to this tab.
                        ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> {
                            if (item instanceof DyedEggItem) {
                                // Add one of each easter egg combination.
                                registerEasterEggs(entries, item);
                            } else if (item instanceof EggPatternItem) {
                                // Add one of each egg pattern
                                registerEggPatterns(entries, item);
                            } else {
                                // Add item as normal.
                                entries.add(item);
                            }
                        });
                    })
                    .build());

    private static ItemStack getIconItem() {
        ItemStack iconItemStack = new ItemStack(ModItems.DYED_EGG);
        iconItemStack.set(DataComponentTypes.BASE_COLOR, DyeColor.LIME);
        iconItemStack.set(ModDataComponents.PATTERN_COLOR, DyeColor.WHITE);
        iconItemStack.set(ModDataComponents.EGG_PATTERN, EggPattern.STRIPES_2);

        return iconItemStack;
    }

    private static void registerEasterEggs(ItemGroup.Entries entries, Item item) {
        for (DyeColor baseColor : DyeColor.values()) {
            // Add a base color egg.
            ItemStack baseStack = new ItemStack(item);
            baseStack.set(DataComponentTypes.BASE_COLOR, baseColor);
            entries.add(baseStack);

            for (DyeColor patternColor : DyeColor.values()) {
                if (baseColor == patternColor) {
                    continue;
                }

                for (EggPattern pattern : EggPattern.values()) {
                    // Add a patterned egg.
                    ItemStack patternStack = new ItemStack(item);
                    patternStack.set(DataComponentTypes.BASE_COLOR, baseColor);
                    patternStack.set(ModDataComponents.PATTERN_COLOR, patternColor);
                    patternStack.set(ModDataComponents.EGG_PATTERN, pattern);
                    entries.add(patternStack);
                }
            }
        }
    }

    private static void registerEggPatterns(ItemGroup.Entries entries, Item item) {
        for (EggPattern pattern : EggPattern.values()) {
            // Add an egg pattern item.
            ItemStack patternStack = new ItemStack(item);
            patternStack.set(ModDataComponents.EGG_PATTERN, pattern);
            entries.add(patternStack);
        }
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering item group for " + EastersDelight.MOD_ID);
    }
}
