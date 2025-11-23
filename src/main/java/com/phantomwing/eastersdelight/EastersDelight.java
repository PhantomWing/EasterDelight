package com.phantomwing.eastersdelight;

import com.mojang.logging.LogUtils;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.item.ModItemProperties;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.screen.EggPainterScreen;
import com.phantomwing.eastersdelight.screen.ModMenuTypes;
import com.phantomwing.eastersdelight.ui.ModCreativeModTab;
import com.phantomwing.eastersdelight.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.entity.animal.Parrot;
import org.slf4j.Logger;

import java.util.Collections;

@Mod(EastersDelight.MOD_ID)
public class EastersDelight {
    public static final String MOD_ID = "eastersdelight";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EastersDelight() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);

        registerManagers(eventBus);
    }

    // Register all managers to the event bus.
    private void registerManagers(IEventBus eventBus) {
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModCreativeModTab.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModVillagers.register(eventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerCompostables();
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.register();
            MenuScreens.register(ModMenuTypes.EGG_PAINTER.get(), EggPainterScreen::new);
        }
    }

    private void registerCompostables() {
        // 85% chance
        ComposterBlock.COMPOSTABLES.put(ModItems.BOILED_EGG.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.EGG_SLICE.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.BUNNY_COOKIE.get(), 0.85f);
    }
}
