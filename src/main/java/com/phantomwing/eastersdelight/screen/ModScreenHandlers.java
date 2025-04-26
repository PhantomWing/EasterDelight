package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.EastersDelight;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<EggPainterScreenHandler> EGG_PAINTER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(EastersDelight.MOD_ID, "egg_painter"),
                    new ExtendedScreenHandlerType<>((id, inv, data) -> new EggPainterScreenHandler(id, inv), BlockPos.PACKET_CODEC));

    public static void register() {
        EastersDelight.LOGGER.info("Registering Screen Handlers for " + EastersDelight.MOD_ID);
    }

}
