package com.phantomwing.eastersdelight;

import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.screen.ModMenuTypes;
import com.phantomwing.eastersdelight.itemGroup.ModItemGroups;
import com.phantomwing.eastersdelight.villager.ModVillagerTrades;
import com.phantomwing.eastersdelight.villager.ModVillagers;
import com.phantomwing.eastersdelight.util.ComposterHelper;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EastersDelight implements ModInitializer {
    public static final String MOD_ID = "eastersdelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Items
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModDataComponents.register();

        // UI
        ModMenuTypes.register();

        // Villagers
        ModVillagers.register();
        ModVillagerTrades.register();

        // UI
        ModItemGroups.register();

        ComposterHelper.register();
    }
}
