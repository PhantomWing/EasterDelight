package com.phantomwing.eastersdelight.villager;

import com.google.common.collect.ImmutableSet;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, EastersDelight.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, EastersDelight.MOD_ID);

    public static final RegistryObject<PoiType> EGG_BUNNY_POI = POI_TYPES.register("egg_bunny_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.EGG_PAINTER.get()
                    .getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> EGG_BUNNY_PROFESSION = VILLAGER_PROFESSIONS.register("egg_bunny",
            () -> new VillagerProfession("egg_bunny",
                    holder -> holder.value() == EGG_BUNNY_POI.get(),
                    poiTypeHolder -> poiTypeHolder.value() == EGG_BUNNY_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.RABBIT_AMBIENT));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
