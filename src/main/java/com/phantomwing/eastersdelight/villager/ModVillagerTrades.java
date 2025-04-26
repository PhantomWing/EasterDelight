package com.phantomwing.eastersdelight.villager;

import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import com.phantomwing.eastersdelight.item.ModItems;

import java.util.Arrays;
import java.util.List;

public class ModVillagerTrades {
    public static float EMERALD_MULTIPLIER = 0.2f;

    public static void register() {
        // 1: Novice
        addPatternTrades(1, 2, EggPattern.STRIPES, EggPattern.STRIPES_2, EggPattern.STRIPES_3);

        // 2: Apprentice
        addPatternTrades(2, 5, EggPattern.DIPPED, EggPattern.SPLIT, EggPattern.BLOCKS);

        // 3: Journeyman
        addPatternTrades(3, 10, EggPattern.PETALS, EggPattern.WAVES);

        // 4: Expert
        addPatternTrades(4, 15, EggPattern.HEART, EggPattern.DOTS);

        // 5: Master
        addPatternTrades(4, 30, EggPattern.CREEPER);
    }

    private static void addPatternTrades(int level, int xp, EggPattern... patterns) {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.EGG_BUNNY_PROFESSION, level,
            factories -> {
                // Generate an offer for each of the provided patterns.
                for (EggPattern pattern : patterns) {
                    factories.add((trader, random) -> getPatternOffer(pattern, xp));
                }

                // Add a random Easter Egg as a potential trade, with one of the provided patterns.
                factories.add((trader, random) -> getEasterEggOffer(random, xp, patterns));
            }
        );
    }

    private static TradeOffer getPatternOffer(EggPattern pattern, int xp) {
        TradedItem itemCost = new TradedItem(Items.EMERALD, 1);
        ItemStack eggPattern = getPatternItem(pattern, 8);

        // Return a MerchantOffer.
        return new TradeOffer(
                itemCost,
                eggPattern,
                16,
                xp,
                EMERALD_MULTIPLIER
        );
    }

    private static TradeOffer getEasterEggOffer(Random random, int xp, EggPattern... patterns) {
        TradedItem itemCost = new TradedItem(Items.EMERALD, 2);
        ItemStack itemStack = getRandomEasterEggItem(random, 4, patterns);

        // Return a MerchantOffer.
        return new TradeOffer(
                itemCost,
                itemStack,
                16,
                xp,
                EMERALD_MULTIPLIER
        );
    }

    private static ItemStack getPatternItem(EggPattern pattern, int count) {
        ItemStack patternStack = new ItemStack(ModItems.EGG_PATTERN, count);
        patternStack.set(ModDataComponents.EGG_PATTERN, pattern);

        return patternStack;
    }

    private static ItemStack getRandomEasterEggItem(Random random, int count, EggPattern... patterns) {
        // Get a random Egg Pattern, from the given pattern list.
        EggPattern pattern = patterns[random.nextInt(patterns.length)];

        // Get a random base color
        DyeColor[] colors = DyeColor.values();
        DyeColor baseColor = colors[random.nextInt(colors.length)];

        // Get a random pattern DyeColor (which must be different from the base color)
        List<DyeColor> filteredColors = Arrays.stream(colors).filter((color) -> color != baseColor).toList();
        DyeColor patternColor = filteredColors.get(random.nextInt(colors.length - 1));

        // Generate an ItemStack with the random data components.
        ItemStack eggStack = new ItemStack(ModItems.DYED_EGG, count);
        eggStack.set(DataComponentTypes.BASE_COLOR, baseColor);
        eggStack.set(ModDataComponents.EGG_PATTERN, pattern);
        eggStack.set(ModDataComponents.PATTERN_COLOR, patternColor);

        return eggStack;
    }

}
