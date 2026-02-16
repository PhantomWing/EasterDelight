package com.phantomwing.eastersdelight;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = EastersDelight.MOD_ID)
public class EastersDelightConfig implements ConfigData {
    private static final String ENABLE_DYED_EGGS_IN_CREATIVE_MODE_ID  = "enable_dyed_eggs_in_creative_mode";
    public boolean enable_dyed_eggs_in_creative_mode = true;

    public static EastersDelightConfig get() {
        return AutoConfig.getConfigHolder(EastersDelightConfig.class).getConfig();
    }

    public static void register() {
        AutoConfig.register(EastersDelightConfig.class, GsonConfigSerializer::new);
    }

    public static boolean shouldAddDyedEggsToCreativeMode() {
        EastersDelightConfig config = EastersDelightConfig.get();
        return config.enable_dyed_eggs_in_creative_mode;
    }
}