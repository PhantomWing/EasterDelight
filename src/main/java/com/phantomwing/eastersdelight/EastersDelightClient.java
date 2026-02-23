package com.phantomwing.eastersdelight;

import com.phantomwing.eastersdelight.block.BlockColorHandler;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.screen.EggPainterScreen;
import net.fabricmc.api.ClientModInitializer;
import com.phantomwing.eastersdelight.screen.ModMenuTypes;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class EastersDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.EGG_PAINTER, EggPainterScreen::new);

        BlockColorHandler.registerBlockColors();

        // Make sure dyed eggs are rendered correctly as a cutout.
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT,
                ModBlocks.DYED_EGG
        );
    }
}
