package com.phantomwing.eastersdelight.itemGroup;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab MOD_TAB =
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "item_group"),
                    FabricItemGroup.builder().icon(ModItemGroups::getIconItem)
                    .title(Component.translatable(("itemGroup." + EastersDelight.MOD_ID)))
                    .displayItems((displayContext, entries) -> {
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
                                entries.accept(item);
                            }
                        });
                    })
                    .build());

    private static ItemStack getIconItem() {
        ItemStack iconItemStack = new ItemStack(ModItems.DYED_EGG);
        iconItemStack.set(DataComponents.BASE_COLOR, DyeColor.LIME);
        iconItemStack.set(ModDataComponents.PATTERN_COLOR, DyeColor.WHITE);
        iconItemStack.set(ModDataComponents.EGG_PATTERN, EggPattern.STRIPES_2);

        return iconItemStack;
    }

    private static void registerEasterEggs(CreativeModeTab.Output output, Item item) {
        for (DyeColor baseColor : DyeColor.values()) {
            // Add a base color egg.
            ItemStack baseStack = new ItemStack(item);
            baseStack.set(DataComponents.BASE_COLOR, baseColor);
            output.accept(baseStack);

            for (DyeColor patternColor : DyeColor.values()) {
                if (baseColor == patternColor) {
                    continue;
                }

                for (EggPattern pattern : EggPattern.values()) {
                    // Add a patterned egg.
                    ItemStack patternStack = new ItemStack(item);
                    patternStack.set(DataComponents.BASE_COLOR, baseColor);
                    patternStack.set(ModDataComponents.PATTERN_COLOR, patternColor);
                    patternStack.set(ModDataComponents.EGG_PATTERN, pattern);
                    output.accept(patternStack);
                }
            }
        }
    }

    private static void registerEggPatterns(CreativeModeTab.Output output, Item item) {
        for (EggPattern pattern : EggPattern.values()) {
            // Add an egg pattern item.
            ItemStack patternStack = new ItemStack(item);
            patternStack.set(ModDataComponents.EGG_PATTERN, pattern);
            output.accept(patternStack);
        }
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering item group for " + EastersDelight.MOD_ID);
    }
}
