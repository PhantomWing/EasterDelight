package com.phantomwing.eastersdelight.villager;

import com.google.common.collect.ImmutableSet;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final PointOfInterestType EGG_BUNNY_POI = Registry.register(Registries.POINT_OF_INTEREST_TYPE, Identifier.of(EastersDelight.MOD_ID, "egg_bunny_poi"),
            new PointOfInterestType(ImmutableSet.copyOf(ModBlocks.EGG_PAINTER.getStateManager().getStates()), 1, 1));

    public static final VillagerProfession EGG_BUNNY_PROFESSION = Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(EastersDelight.MOD_ID, "egg_bunny"),
            new VillagerProfession("egg_bunny",
                    holder -> holder.value() == EGG_BUNNY_POI,
                    poiTypeHolder -> poiTypeHolder.value() == EGG_BUNNY_POI,
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.ENTITY_RABBIT_AMBIENT));

    public static void register() {
        EastersDelight.LOGGER.info("Registering villager professions for " + EastersDelight.MOD_ID);
    }
}
