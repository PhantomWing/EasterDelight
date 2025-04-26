package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.EastersDelight;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<EggPainterMenu> EGG_PAINTER = registerMenuType("egg_painter", new ExtendedScreenHandlerType<>(EggPainterMenu::new, BlockPos.STREAM_CODEC));

    private static <T extends  MenuType<?>> T registerMenuType(String name, T menu) {
        return Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, name), menu) ;
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering Screen Handlers for " + EastersDelight.MOD_ID);
    }
}
