package com.phantomwing.eastersdelight;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import com.phantomwing.eastersdelight.datagen.*;

public class EastersDelightDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagsProvider::new);
        pack.addProvider(ModItemTagsProvider::new);
        pack.addProvider(ModPoiTypeTagsProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);

        // Data-driven Egg Bunny villager trades (26.1 replacement for TradeOfferHelper)
        pack.addProvider(ModVillagerTrades::new);
        pack.addProvider(ModVillagerTradeTagsProvider::new);
        pack.addProvider(ModVillagerTradeSetsProvider::new);
    }
}
