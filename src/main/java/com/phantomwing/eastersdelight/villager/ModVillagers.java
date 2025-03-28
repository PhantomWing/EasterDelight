package com.phantomwing.eastersdelight.villager;

import com.google.common.collect.ImmutableSet;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, EastersDelight.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, EastersDelight.MOD_ID);

    public static final Holder<PoiType> EGG_BUNNY_POI = POI_TYPES.register("egg_bunny_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.EGG_PAINTER.get()
                    .getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> EGG_BUNNY_PROFESSION = VILLAGER_PROFESSIONS.register("egg_bunny",
            () -> new VillagerProfession("egg_bunny",
                    holder -> holder.value() == EGG_BUNNY_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == EGG_BUNNY_POI.value(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.RABBIT_AMBIENT));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
