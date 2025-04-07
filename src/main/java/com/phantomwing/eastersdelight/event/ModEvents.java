package com.phantomwing.eastersdelight.event;

import com.phantomwing.eastersdelight.Configuration;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = EastersDelight.MOD_ID)
public class ModEvents {
    public static float EMERALD_MULTIPLIER = 0.2f;

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        // Check if trades are enabled.
        if (!Configuration.ENABLE_VILLAGER_TRADES.get()) {
            return;
        }

        // Add Easter Bunny trades
        if (event.getType() == ModVillagers.EGG_BUNNY_PROFESSION.get()) {
            // Get trades list.
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // 1: Novice
            addPatternTrades(trades, 1, 2, EggPattern.STRIPES, EggPattern.STRIPES_2, EggPattern.STRIPES_3);

            // 2: Apprentice
            addPatternTrades(trades, 2, 5, EggPattern.DIPPED, EggPattern.SPLIT, EggPattern.BLOCKS);

            // 3: Journeyman
            addPatternTrades(trades, 3, 10, EggPattern.PETALS, EggPattern.WAVES);

            // 4: Expert
            addPatternTrades(trades, 4, 15, EggPattern.HEART, EggPattern.DOTS);

            // 5: Master
            addPatternTrades(trades, 4, 30, EggPattern.CREEPER);
        }
    }

    private static void addPatternTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, int xp, EggPattern... patterns) {
        List<VillagerTrades.ItemListing> levelTrades = trades.get(level);

        // Generate an offer for each of the provided patterns.
        for (EggPattern pattern : patterns) {
            levelTrades.add((trader, random) -> getPatternOffer(pattern, xp));
        }

        // Add a random Easter Egg as a potential trade, with one of the provided patterns.
        levelTrades.add((trader, random) -> getEasterEggOffer(random, xp, patterns));
    }

    private static MerchantOffer getPatternOffer(EggPattern pattern, int xp) {
        ItemStack itemCost = new ItemStack(Items.EMERALD, 1);
        ItemStack eggPattern = getPatternItem(pattern, 8);

        // Return a MerchantOffer.
        return new MerchantOffer(
                itemCost,
                eggPattern,
                16,
                xp,
                EMERALD_MULTIPLIER
        );
    }

    private static MerchantOffer getEasterEggOffer(RandomSource random, int xp, EggPattern... patterns) {
        ItemStack itemCost = new ItemStack(Items.EMERALD, 2);
        ItemStack itemStack = getRandomEasterEggItem(random, 4, patterns);

        // Return a MerchantOffer.
        return new MerchantOffer(
                itemCost,
                itemStack,
                16,
                xp,
                EMERALD_MULTIPLIER
        );
    }

    private static ItemStack getPatternItem(EggPattern pattern, int count) {
        ItemStack patternStack = new ItemStack(ModItems.EGG_PATTERN.get(), count);

        CompoundTag tag = patternStack.getOrCreateTag();
        tag.putString(ModDataComponents.EGG_PATTERN, pattern.getName());

        return patternStack;
    }

    private static ItemStack getRandomEasterEggItem(RandomSource random, int count, EggPattern... patterns) {
        // Get a random Egg Pattern, from the given pattern list.
        EggPattern pattern = patterns[random.nextInt(patterns.length)];

        // Get a random base color
        DyeColor[] colors = DyeColor.values();
        DyeColor baseColor = colors[random.nextInt(colors.length)];

        // Get a random pattern DyeColor (which must be different from the base color)
        List<DyeColor> filteredColors = Arrays.stream(colors).filter((color) -> color != baseColor).toList();
        DyeColor patternColor = filteredColors.get(random.nextInt(colors.length - 1));

        // Generate an ItemStack with the random data components.
        ItemStack eggStack = new ItemStack(ModItems.DYED_EGG.get(), count);

        CompoundTag compoundTag = eggStack.getOrCreateTag();
        compoundTag.putString(ModDataComponents.BASE_COLOR, baseColor.getName());
        compoundTag.putString(ModDataComponents.EGG_PATTERN, pattern.getName());
        compoundTag.putString(ModDataComponents.PATTERN_COLOR, patternColor.getName());

        eggStack.setTag(compoundTag);

        return eggStack;
    }
}
