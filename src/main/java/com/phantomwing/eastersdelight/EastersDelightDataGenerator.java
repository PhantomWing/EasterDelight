package com.phantomwing.eastersdelight;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.resources.Identifier;
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

        // Built-in resource pack for FDR recipe overrides. Lives in a separate pack because
        // Fabric Loader's regular mod-pack ordering is alphabetical by mod ID, and
        // "eastersdelight" loads before "farmersdelight" — so any data/farmersdelight/… file
        // in our main datagen output gets clobbered by FDR's own bundled file at runtime.
        // Built-in packs registered via Fabric Resource Loader sit above the regular mod-pack
        // stack, so files inside them take precedence.
        FabricDataGenerator.Pack overridesPack = fabricDataGenerator.createBuiltinResourcePack(
                Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, "farmersdelight_overrides"));
        overridesPack.addProvider(ModFarmersDelightOverrideRecipeProvider::new);
    }
}
