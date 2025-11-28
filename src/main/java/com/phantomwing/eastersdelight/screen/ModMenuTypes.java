package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<EggPainterMenu> EGG_PAINTER = registerMenuType("egg_painter", EggPainterMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> registerMenuType(String name, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, new ResourceLocation(EastersDelight.MOD_ID, name), create(factory));
    }

    static <T extends AbstractContainerMenu> MenuType<T> create(MenuType.MenuSupplier<T> factory) {
        return new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS);
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering Screen Handlers for " + EastersDelight.MOD_ID);
    }
}
