package com.phantomwing.eastersdelight.itemGroup;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab MOD_TAB =
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,new ResourceLocation(EastersDelight.MOD_ID, "item_group"),
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

        CompoundTag compoundTag = iconItemStack.getOrCreateTag();

        compoundTag.putString(ModDataComponents.BASE_COLOR, DyeColor.LIME.getName());
        compoundTag.putString(ModDataComponents.PATTERN_COLOR, DyeColor.WHITE.getName());
        compoundTag.putString(ModDataComponents.EGG_PATTERN, EggPattern.STRIPES_2.getName());

        iconItemStack.setTag(compoundTag);

        return iconItemStack;
    }

    private static void registerEasterEggs(CreativeModeTab.Output output, Item item) {
        for (DyeColor baseColor : DyeColor.values()) {
            // Add a base color egg.
            ItemStack baseStack = new ItemStack(item);
            CompoundTag compoundTag = baseStack.getOrCreateTag();

            compoundTag.putString(ModDataComponents.BASE_COLOR, baseColor.getName());
            baseStack.setTag(compoundTag);

            output.accept(baseStack);

            for (DyeColor patternColor : DyeColor.values()) {
                if (baseColor == patternColor) {
                    continue;
                }

                for (EggPattern pattern : EggPattern.values()) {
                    // Add a patterned egg.
                    ItemStack patternStack = new ItemStack(item);
                    CompoundTag patternTag = patternStack.getOrCreateTag();

                    patternTag.putString(ModDataComponents.BASE_COLOR, baseColor.getName());
                    patternTag.putString(ModDataComponents.PATTERN_COLOR, patternColor.getName());
                    patternTag.putString(ModDataComponents.EGG_PATTERN, pattern.getName());

                    patternStack.setTag(patternTag);
                    output.accept(patternStack);
                }
            }
        }
    }

    private static void registerEggPatterns(CreativeModeTab.Output output, Item item) {
        for (EggPattern pattern : EggPattern.values()) {
            // Add an egg pattern item.
            ItemStack patternStack = new ItemStack(item);
            CompoundTag compoundTag = patternStack.getOrCreateTag();

            compoundTag.putString(ModDataComponents.EGG_PATTERN, pattern.getName());

            patternStack.setTag(compoundTag);
            output.accept(patternStack);
        }
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering item group for " + EastersDelight.MOD_ID);
    }
}
