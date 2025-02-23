package com.phantomwing.eastersdelight;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Configuration {
    public static ModConfigSpec COMMON_CONFIG;

    // Villager trades
    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public static ModConfigSpec.BooleanValue ENABLE_VILLAGER_TRADES;

    // Wandering Trader trades
    public static final String ENABLE_WANDERING_TRADER_TRADES_ID = "enable_wandering_trader_trades";
    public static ModConfigSpec.BooleanValue ENABLE_WANDERING_TRADER_TRADES;


    public static boolean getBooleanConfigurationValue(String id) {
        return switch (id) {
            case ENABLE_VILLAGER_TRADES_ID -> Configuration.ENABLE_VILLAGER_TRADES.get();
            case ENABLE_WANDERING_TRADER_TRADES_ID -> Configuration.ENABLE_WANDERING_TRADER_TRADES.get();
            default -> false;
        };
    }

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        // General settings
        ENABLE_VILLAGER_TRADES = COMMON_BUILDER.comment("Should villagers trade Easter's Delight items? (May reduce chances of other trades appearing)").define(ENABLE_VILLAGER_TRADES_ID, true);
        ENABLE_WANDERING_TRADER_TRADES = COMMON_BUILDER.comment("Should the Wandering Trader sell Easter's Delight items?").define(ENABLE_WANDERING_TRADER_TRADES_ID, true);

        // Build config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
