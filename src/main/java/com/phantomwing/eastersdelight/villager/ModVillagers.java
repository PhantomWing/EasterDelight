package com.phantomwing.eastersdelight.villager;

import com.google.common.collect.ImmutableSet;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.block.Block;

public class ModVillagers {
    public static final ResourceKey<PoiType> EGG_BUNNY_POI_KEY = registerPoiKey("egg_bunny_poi");
    public static final PoiType EGG_BUNNY_POI = registerPOI("egg_bunny_poi", ModBlocks.EGG_PAINTER);

    public static final ResourceKey<VillagerProfession> EGG_BUNNY_PROFESSION_KEY =
            ResourceKey.create(Registries.VILLAGER_PROFESSION, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, "egg_bunny"));

    // ----- Data-driven trade plumbing -----
    // 26.1: VillagerProfession.tradeSetsByLevel maps villager level -> ResourceKey<TradeSet>.
    // Each TradeSet (data/eastersdelight/trade_set/egg_bunny/level_<N>.json) references a tag
    // (#eastersdelight:egg_bunny/level_<N>) whose member VillagerTrade entries are written by
    // com.phantomwing.eastersdelight.datagen.ModVillagerTrades.

    public static final ResourceKey<TradeSet> EGG_BUNNY_LEVEL_1_TRADE_SET = tradeSetKey("egg_bunny/level_1");
    public static final ResourceKey<TradeSet> EGG_BUNNY_LEVEL_2_TRADE_SET = tradeSetKey("egg_bunny/level_2");
    public static final ResourceKey<TradeSet> EGG_BUNNY_LEVEL_3_TRADE_SET = tradeSetKey("egg_bunny/level_3");
    public static final ResourceKey<TradeSet> EGG_BUNNY_LEVEL_4_TRADE_SET = tradeSetKey("egg_bunny/level_4");
    public static final ResourceKey<TradeSet> EGG_BUNNY_LEVEL_5_TRADE_SET = tradeSetKey("egg_bunny/level_5");

    public static final TagKey<VillagerTrade> EGG_BUNNY_LEVEL_1_TAG = tradeTag("egg_bunny/level_1");
    public static final TagKey<VillagerTrade> EGG_BUNNY_LEVEL_2_TAG = tradeTag("egg_bunny/level_2");
    public static final TagKey<VillagerTrade> EGG_BUNNY_LEVEL_3_TAG = tradeTag("egg_bunny/level_3");
    public static final TagKey<VillagerTrade> EGG_BUNNY_LEVEL_4_TAG = tradeTag("egg_bunny/level_4");
    public static final TagKey<VillagerTrade> EGG_BUNNY_LEVEL_5_TAG = tradeTag("egg_bunny/level_5");

    public static final VillagerProfession EGG_BUNNY_PROFESSION = registerProfession("egg_bunny", EGG_BUNNY_POI_KEY);

    private static VillagerProfession registerProfession(String name, ResourceKey<PoiType> type) {
        Int2ObjectMap<ResourceKey<TradeSet>> tradeSetsByLevel = new Int2ObjectOpenHashMap<>();
        tradeSetsByLevel.put(1, EGG_BUNNY_LEVEL_1_TRADE_SET);
        tradeSetsByLevel.put(2, EGG_BUNNY_LEVEL_2_TRADE_SET);
        tradeSetsByLevel.put(3, EGG_BUNNY_LEVEL_3_TRADE_SET);
        tradeSetsByLevel.put(4, EGG_BUNNY_LEVEL_4_TRADE_SET);
        tradeSetsByLevel.put(5, EGG_BUNNY_LEVEL_5_TRADE_SET);
        // 26.x hands the profession its display name directly instead of deriving one from the
        // registry id, so this has to be a translatable component: a literal would render the raw
        // "egg_bunny" and no lang key would ever be consulted.
        Component displayName = Component.translatable("entity.minecraft.villager." + EastersDelight.MOD_ID + "." + name);

        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, name),
                new VillagerProfession(displayName, entry -> entry.is(type), entry -> entry.is(type),
                        ImmutableSet.<Item>of(), ImmutableSet.<Block>of(), SoundEvents.VILLAGER_WORK_CARTOGRAPHER, tradeSetsByLevel));
    }

    private static PoiType registerPOI(String name, Block block) {
        return PoiHelper.register(Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, name),
                1, 1, block);
    }

    private static ResourceKey<PoiType> registerPoiKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, name));
    }

    private static ResourceKey<TradeSet> tradeSetKey(String path) {
        return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, path));
    }

    private static TagKey<VillagerTrade> tradeTag(String path) {
        return TagKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, path));
    }

    public static void register() {
        EastersDelight.LOGGER.info("Registering villager professions for " + EastersDelight.MOD_ID);
    }
}
