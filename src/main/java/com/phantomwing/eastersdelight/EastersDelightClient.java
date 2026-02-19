package com.phantomwing.eastersdelight;

import com.phantomwing.eastersdelight.block.BlockColorHandler;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.itemProperties.ItemPropertyHelper;
import com.phantomwing.eastersdelight.screen.EggPainterScreen;
import net.fabricmc.api.ClientModInitializer;
import com.phantomwing.eastersdelight.itemProperties.ModItemProperties;
import com.phantomwing.eastersdelight.screen.ModMenuTypes;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class EastersDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.EGG_PAINTER, EggPainterScreen::new);

        ItemPropertyHelper.register();
        BlockColorHandler.registerBlockColors();

        // Make sure dyed eggs are rendered correctly as a cutout.
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ModBlocks.DYED_EGG);
    }
}
