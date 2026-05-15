package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.villager.ModVillagers;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.trading.VillagerTrade;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Wires the data-driven Egg Bunny {@link VillagerTrade} entries into per-level tags
 * ({@code data/eastersdelight/tags/villager_trade/egg_bunny/level_<N>.json}). The matching
 * {@link net.minecraft.world.item.trading.TradeSet} JSONs (written by
 * {@link ModVillagerTrades}) point at these tags via their {@code trades} field, and the
 * profession's {@code tradeSetsByLevel} map then drives villager level-ups.
 *
 * <p>Unlike vanilla professions there is no pre-existing {@code VillagerTradeTags.EGG_BUNNY_*}
 * constant — the tag keys are declared in {@link ModVillagers}.
 */
public class ModVillagerTradeTagsProvider extends FabricTagsProvider<VillagerTrade> {
    public ModVillagerTradeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.VILLAGER_TRADE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        builder(ModVillagers.EGG_BUNNY_LEVEL_1_TAG)
                .addOptional(ModVillagerTrades.EGG_BUNNY_1_PATTERN_STRIPES)
                .addOptional(ModVillagerTrades.EGG_BUNNY_1_PATTERN_STRIPES_2)
                .addOptional(ModVillagerTrades.EGG_BUNNY_1_PATTERN_STRIPES_3)
                .addOptional(ModVillagerTrades.EGG_BUNNY_1_DYED_EGG_RED_WHITE_STRIPES)
                .addOptional(ModVillagerTrades.EGG_BUNNY_1_DYED_EGG_YELLOW_PINK_STRIPES_2)
                .addOptional(ModVillagerTrades.EGG_BUNNY_1_DYED_EGG_LIGHT_BLUE_WHITE_STRIPES_3);

        builder(ModVillagers.EGG_BUNNY_LEVEL_2_TAG)
                .addOptional(ModVillagerTrades.EGG_BUNNY_2_PATTERN_DIPPED)
                .addOptional(ModVillagerTrades.EGG_BUNNY_2_PATTERN_SPLIT)
                .addOptional(ModVillagerTrades.EGG_BUNNY_2_PATTERN_BLOCKS)
                .addOptional(ModVillagerTrades.EGG_BUNNY_2_DYED_EGG_MAGENTA_CYAN_DIPPED)
                .addOptional(ModVillagerTrades.EGG_BUNNY_2_DYED_EGG_LIME_YELLOW_SPLIT)
                .addOptional(ModVillagerTrades.EGG_BUNNY_2_DYED_EGG_ORANGE_BLUE_BLOCKS);

        builder(ModVillagers.EGG_BUNNY_LEVEL_3_TAG)
                .addOptional(ModVillagerTrades.EGG_BUNNY_3_PATTERN_PETALS)
                .addOptional(ModVillagerTrades.EGG_BUNNY_3_PATTERN_WAVES)
                .addOptional(ModVillagerTrades.EGG_BUNNY_3_DYED_EGG_LIGHT_BLUE_PINK_PETALS)
                .addOptional(ModVillagerTrades.EGG_BUNNY_3_DYED_EGG_CYAN_WHITE_WAVES)
                .addOptional(ModVillagerTrades.EGG_BUNNY_3_DYED_EGG_PURPLE_YELLOW_WAVES);

        builder(ModVillagers.EGG_BUNNY_LEVEL_4_TAG)
                .addOptional(ModVillagerTrades.EGG_BUNNY_4_PATTERN_HEART)
                .addOptional(ModVillagerTrades.EGG_BUNNY_4_PATTERN_DOTS)
                .addOptional(ModVillagerTrades.EGG_BUNNY_4_DYED_EGG_PINK_RED_HEART)
                .addOptional(ModVillagerTrades.EGG_BUNNY_4_DYED_EGG_RED_PINK_HEART)
                .addOptional(ModVillagerTrades.EGG_BUNNY_4_DYED_EGG_WHITE_BLACK_DOTS);

        builder(ModVillagers.EGG_BUNNY_LEVEL_5_TAG)
                .addOptional(ModVillagerTrades.EGG_BUNNY_5_PATTERN_CREEPER)
                .addOptional(ModVillagerTrades.EGG_BUNNY_5_DYED_EGG_LIME_GREEN_CREEPER)
                .addOptional(ModVillagerTrades.EGG_BUNNY_5_DYED_EGG_GREEN_LIME_CREEPER)
                .addOptional(ModVillagerTrades.EGG_BUNNY_5_DYED_EGG_WHITE_LIME_CREEPER);
    }
}
