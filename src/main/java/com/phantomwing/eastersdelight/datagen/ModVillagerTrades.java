package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Data-driven Egg Bunny villager trades — the 26.1 replacement for fabric-api's removed
 * {@code TradeOfferHelper}. Trades are no longer registered at runtime; instead each trade is a
 * {@link VillagerTrade} JSON in {@code data/eastersdelight/villager_trade/} grouped by per-level
 * tags ({@code data/eastersdelight/tags/villager_trade/egg_bunny/level_<N>.json}). Companion
 * provider {@link ModVillagerTradeSetsProvider} emits a {@code TradeSet} JSON per level pointing
 * at each tag, and {@link ModVillagers} wires those TradeSet keys into the profession's
 * {@code tradeSetsByLevel} map.
 *
 * <p>Original randomized "Dyed Egg" offers (from the 1.21.11 branch) are unrolled into a curated
 * set of fixed color combos per level since JSON trades cannot draw a random pattern/color.
 */
public class ModVillagerTrades extends FabricDynamicRegistryProvider {
    public static final float PRICE_MULTIPLIER = 0.2f;
    public static final int MAX_USES = 16;
    public static final int PATTERN_COUNT = 8;
    public static final int DYED_EGG_COUNT = 4;
    public static final int EMERALD_COST_PATTERN = 1;
    public static final int EMERALD_COST_DYED_EGG = 2;

    public ModVillagerTrades(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    // Level 1: STRIPES / STRIPES_2 / STRIPES_3 patterns + dyed eggs
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_1_PATTERN_STRIPES = trade("egg_bunny/1/pattern_stripes");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_1_PATTERN_STRIPES_2 = trade("egg_bunny/1/pattern_stripes_2");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_1_PATTERN_STRIPES_3 = trade("egg_bunny/1/pattern_stripes_3");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_1_DYED_EGG_RED_WHITE_STRIPES = trade("egg_bunny/1/dyed_egg_red_white_stripes");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_1_DYED_EGG_YELLOW_PINK_STRIPES_2 = trade("egg_bunny/1/dyed_egg_yellow_pink_stripes_2");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_1_DYED_EGG_LIGHT_BLUE_WHITE_STRIPES_3 = trade("egg_bunny/1/dyed_egg_light_blue_white_stripes_3");

    // Level 2: DIPPED / SPLIT / BLOCKS patterns + dyed eggs
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_2_PATTERN_DIPPED = trade("egg_bunny/2/pattern_dipped");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_2_PATTERN_SPLIT = trade("egg_bunny/2/pattern_split");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_2_PATTERN_BLOCKS = trade("egg_bunny/2/pattern_blocks");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_2_DYED_EGG_MAGENTA_CYAN_DIPPED = trade("egg_bunny/2/dyed_egg_magenta_cyan_dipped");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_2_DYED_EGG_LIME_YELLOW_SPLIT = trade("egg_bunny/2/dyed_egg_lime_yellow_split");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_2_DYED_EGG_ORANGE_BLUE_BLOCKS = trade("egg_bunny/2/dyed_egg_orange_blue_blocks");

    // Level 3: PETALS / WAVES patterns + dyed eggs
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_3_PATTERN_PETALS = trade("egg_bunny/3/pattern_petals");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_3_PATTERN_WAVES = trade("egg_bunny/3/pattern_waves");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_3_DYED_EGG_LIGHT_BLUE_PINK_PETALS = trade("egg_bunny/3/dyed_egg_light_blue_pink_petals");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_3_DYED_EGG_CYAN_WHITE_WAVES = trade("egg_bunny/3/dyed_egg_cyan_white_waves");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_3_DYED_EGG_PURPLE_YELLOW_WAVES = trade("egg_bunny/3/dyed_egg_purple_yellow_waves");

    // Level 4: HEART / DOTS patterns + dyed eggs
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_4_PATTERN_HEART = trade("egg_bunny/4/pattern_heart");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_4_PATTERN_DOTS = trade("egg_bunny/4/pattern_dots");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_4_DYED_EGG_PINK_RED_HEART = trade("egg_bunny/4/dyed_egg_pink_red_heart");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_4_DYED_EGG_RED_PINK_HEART = trade("egg_bunny/4/dyed_egg_red_pink_heart");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_4_DYED_EGG_WHITE_BLACK_DOTS = trade("egg_bunny/4/dyed_egg_white_black_dots");

    // Level 5: CREEPER pattern + dyed eggs
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_5_PATTERN_CREEPER = trade("egg_bunny/5/pattern_creeper");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_5_DYED_EGG_LIME_GREEN_CREEPER = trade("egg_bunny/5/dyed_egg_lime_green_creeper");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_5_DYED_EGG_GREEN_LIME_CREEPER = trade("egg_bunny/5/dyed_egg_green_lime_creeper");
    public static final ResourceKey<VillagerTrade> EGG_BUNNY_5_DYED_EGG_WHITE_LIME_CREEPER = trade("egg_bunny/5/dyed_egg_white_lime_creeper");

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // ---- Level 1 (XP 2) ----
        entries.add(EGG_BUNNY_1_PATTERN_STRIPES, patternTrade(EggPattern.STRIPES, 2));
        entries.add(EGG_BUNNY_1_PATTERN_STRIPES_2, patternTrade(EggPattern.STRIPES_2, 2));
        entries.add(EGG_BUNNY_1_PATTERN_STRIPES_3, patternTrade(EggPattern.STRIPES_3, 2));
        entries.add(EGG_BUNNY_1_DYED_EGG_RED_WHITE_STRIPES, dyedEggTrade(DyeColor.RED, EggPattern.STRIPES, DyeColor.WHITE, 2));
        entries.add(EGG_BUNNY_1_DYED_EGG_YELLOW_PINK_STRIPES_2, dyedEggTrade(DyeColor.YELLOW, EggPattern.STRIPES_2, DyeColor.PINK, 2));
        entries.add(EGG_BUNNY_1_DYED_EGG_LIGHT_BLUE_WHITE_STRIPES_3, dyedEggTrade(DyeColor.LIGHT_BLUE, EggPattern.STRIPES_3, DyeColor.WHITE, 2));

        // ---- Level 2 (XP 5) ----
        entries.add(EGG_BUNNY_2_PATTERN_DIPPED, patternTrade(EggPattern.DIPPED, 5));
        entries.add(EGG_BUNNY_2_PATTERN_SPLIT, patternTrade(EggPattern.SPLIT, 5));
        entries.add(EGG_BUNNY_2_PATTERN_BLOCKS, patternTrade(EggPattern.BLOCKS, 5));
        entries.add(EGG_BUNNY_2_DYED_EGG_MAGENTA_CYAN_DIPPED, dyedEggTrade(DyeColor.MAGENTA, EggPattern.DIPPED, DyeColor.CYAN, 5));
        entries.add(EGG_BUNNY_2_DYED_EGG_LIME_YELLOW_SPLIT, dyedEggTrade(DyeColor.LIME, EggPattern.SPLIT, DyeColor.YELLOW, 5));
        entries.add(EGG_BUNNY_2_DYED_EGG_ORANGE_BLUE_BLOCKS, dyedEggTrade(DyeColor.ORANGE, EggPattern.BLOCKS, DyeColor.BLUE, 5));

        // ---- Level 3 (XP 10) ----
        entries.add(EGG_BUNNY_3_PATTERN_PETALS, patternTrade(EggPattern.PETALS, 10));
        entries.add(EGG_BUNNY_3_PATTERN_WAVES, patternTrade(EggPattern.WAVES, 10));
        entries.add(EGG_BUNNY_3_DYED_EGG_LIGHT_BLUE_PINK_PETALS, dyedEggTrade(DyeColor.LIGHT_BLUE, EggPattern.PETALS, DyeColor.PINK, 10));
        entries.add(EGG_BUNNY_3_DYED_EGG_CYAN_WHITE_WAVES, dyedEggTrade(DyeColor.CYAN, EggPattern.WAVES, DyeColor.WHITE, 10));
        entries.add(EGG_BUNNY_3_DYED_EGG_PURPLE_YELLOW_WAVES, dyedEggTrade(DyeColor.PURPLE, EggPattern.WAVES, DyeColor.YELLOW, 10));

        // ---- Level 4 (XP 15) ----
        entries.add(EGG_BUNNY_4_PATTERN_HEART, patternTrade(EggPattern.HEART, 15));
        entries.add(EGG_BUNNY_4_PATTERN_DOTS, patternTrade(EggPattern.DOTS, 15));
        entries.add(EGG_BUNNY_4_DYED_EGG_PINK_RED_HEART, dyedEggTrade(DyeColor.PINK, EggPattern.HEART, DyeColor.RED, 15));
        entries.add(EGG_BUNNY_4_DYED_EGG_RED_PINK_HEART, dyedEggTrade(DyeColor.RED, EggPattern.HEART, DyeColor.PINK, 15));
        entries.add(EGG_BUNNY_4_DYED_EGG_WHITE_BLACK_DOTS, dyedEggTrade(DyeColor.WHITE, EggPattern.DOTS, DyeColor.BLACK, 15));

        // ---- Level 5 (XP 30) ----
        entries.add(EGG_BUNNY_5_PATTERN_CREEPER, patternTrade(EggPattern.CREEPER, 30));
        entries.add(EGG_BUNNY_5_DYED_EGG_LIME_GREEN_CREEPER, dyedEggTrade(DyeColor.LIME, EggPattern.CREEPER, DyeColor.GREEN, 30));
        entries.add(EGG_BUNNY_5_DYED_EGG_GREEN_LIME_CREEPER, dyedEggTrade(DyeColor.GREEN, EggPattern.CREEPER, DyeColor.LIME, 30));
        entries.add(EGG_BUNNY_5_DYED_EGG_WHITE_LIME_CREEPER, dyedEggTrade(DyeColor.WHITE, EggPattern.CREEPER, DyeColor.LIME, 30));
    }

    @Override
    public String getName() {
        return "Easter Delight Villager Trades";
    }

    /** Buy {@link #PATTERN_COUNT} pattern-stamped Egg Pattern items for 1 emerald. */
    private static VillagerTrade patternTrade(EggPattern pattern, int xp) {
        DataComponentPatch patch = DataComponentPatch.builder()
                .set(ModDataComponents.EGG_PATTERN, pattern)
                .build();
        ItemStackTemplate result = new ItemStackTemplate(ModItems.EGG_PATTERN, patch).withCount(PATTERN_COUNT);
        return new VillagerTrade(
                new TradeCost(Items.EMERALD, EMERALD_COST_PATTERN),
                Optional.empty(),
                result,
                MAX_USES, xp, PRICE_MULTIPLIER,
                Optional.empty(), List.of()
        );
    }

    /** Buy {@link #DYED_EGG_COUNT} pre-decorated Dyed Eggs (base color, pattern, pattern color) for 2 emeralds. */
    private static VillagerTrade dyedEggTrade(DyeColor baseColor, EggPattern pattern, DyeColor patternColor, int xp) {
        DataComponentPatch patch = DataComponentPatch.builder()
                .set(DataComponents.BASE_COLOR, baseColor)
                .set(ModDataComponents.EGG_PATTERN, pattern)
                .set(ModDataComponents.PATTERN_COLOR, patternColor)
                .build();
        ItemStackTemplate result = new ItemStackTemplate(ModItems.DYED_EGG, patch).withCount(DYED_EGG_COUNT);
        return new VillagerTrade(
                new TradeCost(Items.EMERALD, EMERALD_COST_DYED_EGG),
                Optional.empty(),
                result,
                MAX_USES, xp, PRICE_MULTIPLIER,
                Optional.empty(), List.of()
        );
    }

    private static ResourceKey<VillagerTrade> trade(String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, path));
    }
}
