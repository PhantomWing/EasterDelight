package com.phantomwing.eastersdelight.ui;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.EasterEggItem;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EastersDelight.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB =
            CREATIVE_MODE_TABS.register(EastersDelight.MOD_ID + "_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.EGG))
                    .title(Component.translatable(("itemGroup." + EastersDelight.MOD_ID)))
                    .displayItems((parameters, output) -> {
                        // Add items to this tab.
                        ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> {
                            if (item.get() instanceof EasterEggItem) {
                                // Add one of each easter egg combination.
                                registerEasterEggs(output, item);
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

    private static void registerEasterEggs(CreativeModeTab.Output output, Supplier<Item> item) {
        for (DyeColor baseColor : DyeColor.values()) {
            // Add a base color egg.
            ItemStack baseStack = new ItemStack(item.get());
            baseStack.set(DataComponents.BASE_COLOR, baseColor);
            output.accept(baseStack);

            for (DyeColor patternColor : DyeColor.values()) {
                if (baseColor == patternColor) {
                    continue;
                }

                for (EggPattern pattern : EggPattern.values()) {
                    // Add a patterned egg.
                    ItemStack patternStack = new ItemStack(item.get());
                    patternStack.set(DataComponents.BASE_COLOR, baseColor);
                    patternStack.set(ModDataComponents.PATTERN_COLOR, patternColor);
                    patternStack.set(ModDataComponents.EGG_PATTERN, pattern);
                    output.accept(patternStack);
                }
            }
        }
    }

    private static void registerEggPatterns(CreativeModeTab.Output output, Supplier<Item> item) {
        for (EggPattern pattern : EggPattern.values()) {
            // Add an egg pattern item.
            ItemStack patternStack = new ItemStack(item.get());
            patternStack.set(ModDataComponents.EGG_PATTERN, pattern);
            output.accept(patternStack);
        }
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
