package com.phantomwing.eastersdelight.block;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.custom.DyedEggBlock;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, modid = EastersDelight.MOD_ID)
public class BlockColorHandler
{
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex == 0) {
                DyeColor baseColor = state.getValue(DyedEggBlock.BASE_COLOR);
                return baseColor.getTextureDiffuseColor();
            } else if (tintIndex == 1) {
                DyeColor patternColor = state.getValue(DyedEggBlock.PATTERN_COLOR);

                return patternColor.getTextureDiffuseColor();
            }

            return 0xFFFFFFFF;
        }, ModBlocks.DYED_EGG.get());
    }
}
