package com.phantomwing.eastersdelight;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    public static ForgeConfigSpec COMMON_CONFIG;

    // Villager trades
    public static final String ENABLE_VILLAGER_TRADES_ID = "enable_villager_trades";
    public static ForgeConfigSpec.BooleanValue ENABLE_VILLAGER_TRADES;

    // Creative mode
    public static final String ENABLE_DYED_EGGS_IN_CREATIVE_MODE_ID = "enable_dyed_eggs_in_creative_mode";
    public static ForgeConfigSpec.BooleanValue ENABLE_DYED_EGGS_IN_CREATIVE_MODE;


    public static boolean getBooleanConfigurationValue(String id) {
        return switch (id) {
            case ENABLE_VILLAGER_TRADES_ID -> Configuration.ENABLE_VILLAGER_TRADES.get();
            case ENABLE_DYED_EGGS_IN_CREATIVE_MODE_ID -> Configuration.ENABLE_DYED_EGGS_IN_CREATIVE_MODE.get();
            default -> false;
        };
    }

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        // General settings
        ENABLE_VILLAGER_TRADES = COMMON_BUILDER.comment("Should villagers trade Easter's Delight items? (May reduce chances of other trades appearing)").define(ENABLE_VILLAGER_TRADES_ID, true);
        ENABLE_DYED_EGGS_IN_CREATIVE_MODE = COMMON_BUILDER.comment("Should Dyed Egg variations be included in the Creative Mode tab?").define(ENABLE_DYED_EGGS_IN_CREATIVE_MODE_ID, true);

        // Build config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
