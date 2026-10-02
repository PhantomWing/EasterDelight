package com.phantomwing.eastersdelight.util;

import com.phantomwing.eastersdelight.item.ModItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ComposterHelper {
    /**
     * 26.3 replaced {@code ComposterBlock.COMPOSTABLES} with the COMPOSTABLE item component, which
     * carries a layer provider rather than a raw chance. Vanilla's named providers are binomial
     * "one layer at N%" rolls, so they line up 1:1 with the chances used before: MEDIUM_HIGH is 85%.
     */
    private static void registerCompostableItems(DefaultItemComponentEvents.ModifyContext context,
                                                 ResourceKey<ContextIntProvider> layers, ItemLike... items) {
        Compostable compostable = new Compostable(layers);
        for (ItemLike item : items) {
            context.modify(item.asItem(), builder -> builder.set(DataComponents.COMPOSTABLE, compostable));
        }
    }

    public static void register() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            // 85% chance
            registerCompostableItems(context, ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH,
                    ModItems.BOILED_EGG,
                    ModItems.BOILED_BROWN_EGG,
                    ModItems.BOILED_BLUE_EGG,
                    ModItems.EGG_SLICE,
                    ModItems.BUNNY_COOKIE
            );
        });
    }
}
