package com.phantomwing.eastersdelight.villager;

import com.google.common.collect.ImmutableSet;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

public class ModVillagers {
    public static final PoiType EGG_BUNNY_POI = Registry.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "egg_bunny_poi"),
            new PoiType(ImmutableSet.copyOf(ModBlocks.EGG_PAINTER.getStateDefinition().getPossibleStates()), 1, 1));

    public static final VillagerProfession EGG_BUNNY_PROFESSION = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "egg_bunny"),
            new VillagerProfession("egg_bunny",
                    holder -> holder.value() == EGG_BUNNY_POI,
                    poiTypeHolder -> poiTypeHolder.value() == EGG_BUNNY_POI,
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.RABBIT_AMBIENT));

    public static void register() {
        EastersDelight.LOGGER.info("Registering villager professions for " + EastersDelight.MOD_ID);
    }
}
