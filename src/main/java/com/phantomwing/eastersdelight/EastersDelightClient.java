package com.phantomwing.eastersdelight;

import com.phantomwing.eastersdelight.screen.EggPainterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import com.phantomwing.eastersdelight.item.ModModelPredicates;
import com.phantomwing.eastersdelight.screen.ModScreenHandlers;

@Environment(EnvType.CLIENT)
public class EastersDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.register();

        HandledScreens.register(ModScreenHandlers.EGG_PAINTER, EggPainterScreen::new);
    }
}
