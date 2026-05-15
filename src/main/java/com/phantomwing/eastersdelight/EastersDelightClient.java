package com.phantomwing.eastersdelight;

import com.phantomwing.eastersdelight.block.BlockColorHandler;
import com.phantomwing.eastersdelight.screen.EggPainterScreen;
import net.fabricmc.api.ClientModInitializer;
import com.phantomwing.eastersdelight.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

public class EastersDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.EGG_PAINTER, EggPainterScreen::new);

        BlockColorHandler.registerBlockColors();

        // 26.1: BlockRenderLayerMap was removed from fabric-api. For blocks with custom JSON
        // models that need cutout rendering (like our DyedEggBlock), the render type can be
        // declared in the model JSON via the "render_type" field.
    }
}
