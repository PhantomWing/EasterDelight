package com.phantomwing.eastersdelight.ui;

import com.phantomwing.eastersdelight.Configuration;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EastersDelight.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register(EastersDelight.MOD_ID + "_tab", () -> CreativeModeTab.builder()
                    .icon(ModCreativeModTab::getIconItem)
                    .title(Component.translatable(("itemGroup." + EastersDelight.MOD_ID)))
                    .displayItems((parameters, output) -> {
                        // Add items to this tab.
                        ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> {
                            if (item.get() instanceof DyedEggItem) {
                                // Add one of each easter egg combination.
                                if (Configuration.ENABLE_DYED_EGGS_IN_CREATIVE_MODE.get()) {
                                    registerEasterEggs(output, item);
                                }
                            } else if (item.get() instanceof EggPatternItem) {
                                // Add one of each egg pattern
                                registerEggPatterns(output, item);
                            } else {
                                // Add item as normal.
                                output.accept(item.get());
                            }
                        });
                    })
                    .build());

    private static ItemStack getIconItem() {
        ItemStack iconItemStack = new ItemStack(ModItems.DYED_EGG.get());

        CompoundTag compoundTag = iconItemStack.getOrCreateTag();

        compoundTag.putString(ModDataComponents.BASE_COLOR, DyeColor.LIME.getName());
        compoundTag.putString(ModDataComponents.PATTERN_COLOR, DyeColor.WHITE.getName());
        compoundTag.putString(ModDataComponents.EGG_PATTERN, EggPattern.STRIPES_2.getName());

        iconItemStack.setTag(compoundTag);

        return iconItemStack;
    }

    private static void registerEasterEggs(CreativeModeTab.Output output, Supplier<Item> item) {
        for (DyeColor baseColor : DyeColor.values()) {
            // Add a base color egg.
            ItemStack baseStack = new ItemStack(item.get());
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
                    ItemStack patternStack = new ItemStack(item.get());
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

    private static void registerEggPatterns(CreativeModeTab.Output output, Supplier<Item> item) {
        for (EggPattern pattern : EggPattern.values()) {
            // Add an egg pattern item.
            ItemStack patternStack = new ItemStack(item.get());
            CompoundTag compoundTag = patternStack.getOrCreateTag();

            compoundTag.putString(ModDataComponents.EGG_PATTERN, pattern.getName());

            patternStack.setTag(compoundTag);
            output.accept(patternStack);
        }
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
