package com.phantomwing.eastersdelight.datagen;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.villager.ModVillagers;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Writes the Egg Bunny's per-level {@link TradeSet} JSONs at
 * {@code data/eastersdelight/trade_set/egg_bunny/level_<N>.json}. Each TradeSet just points at the
 * matching {@code #eastersdelight:egg_bunny/level_<N>} tag of {@code minecraft:villager_trade}.
 *
 * <p>This is written as a hand-rolled {@link DataProvider} (rather than going through
 * {@code FabricDynamicRegistryProvider}) because the {@code TradeSet} codec's {@link
 * net.minecraft.core.HolderSet} field rejects a {@link net.minecraft.core.HolderSet#emptyNamed
 * synthetic Named HolderSet} that wraps a tag we are creating in the same datagen run — the
 * codec checks {@code canSerializeIn} on the owner, which fails because the registry ops use a
 * different owner instance than the standalone lookup we have access to here. The TradeSet JSON
 * schema is tiny and stable (see {@code data/minecraft/trade_set/farmer/level_1.json}), so we
 * just emit the three fields directly.
 */
public class ModVillagerTradeSetsProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModVillagerTradeSetsProvider(FabricPackOutput output) {
        this.pathProvider = output.createRegistryElementsPathProvider(net.minecraft.core.registries.Registries.TRADE_SET);
    }

    @Override
    public CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        emit(cachedOutput, futures, ModVillagers.EGG_BUNNY_LEVEL_1_TRADE_SET, "egg_bunny/level_1");
        emit(cachedOutput, futures, ModVillagers.EGG_BUNNY_LEVEL_2_TRADE_SET, "egg_bunny/level_2");
        emit(cachedOutput, futures, ModVillagers.EGG_BUNNY_LEVEL_3_TRADE_SET, "egg_bunny/level_3");
        emit(cachedOutput, futures, ModVillagers.EGG_BUNNY_LEVEL_4_TRADE_SET, "egg_bunny/level_4");
        emit(cachedOutput, futures, ModVillagers.EGG_BUNNY_LEVEL_5_TRADE_SET, "egg_bunny/level_5");
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private void emit(CachedOutput cachedOutput, List<CompletableFuture<?>> futures,
                      ResourceKey<TradeSet> key, String path) {
        JsonObject json = new JsonObject();
        // `amount` = how many trades from the pool a villager gets when leveling up. 2 matches
        // vanilla per-level pool sizes (see data/minecraft/trade_set/farmer/level_1.json).
        json.add("amount", new JsonPrimitive(2.0f));
        // `random_sequence` namespaces the per-villager RNG used to pick offers from the pool.
        json.add("random_sequence", new JsonPrimitive(
                Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, "trade_set/" + path).toString()));
        // `trades` is a HolderSet — a `#namespace:tag` ref serializes as the bare tag string.
        json.add("trades", new JsonPrimitive(
                "#" + Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, path).toString()));

        Path out = pathProvider.json(key.identifier());
        futures.add(DataProvider.saveStable(cachedOutput, json, out));
    }

    @Override
    public @NotNull String getName() {
        return "Easter Delight Villager Trade Sets";
    }
}
