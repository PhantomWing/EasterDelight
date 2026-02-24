package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import com.phantomwing.eastersdelight.util.ItemUtils;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.ComponentContents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModItemModelProvider {
    public static void registerModels(ItemModelGenerators g) {
        // Items
        simpleItem(g, ModItems.BOILED_EGG);
        simpleItem(g, ModItems.EGG_SLICE);
        simpleItem(g, ModItems.CHOCOLATE_EGG);
        simpleItem(g, ModItems.BUNNY_COOKIE);
        easterEggItem(g, ModItems.DYED_EGG);
        eggPatternItem(g, ModItems.EGG_PATTERN);

        // Blocks
        simpleBlock2D(g, ModBlocks.EGG_PAINTER);
    }

    private static void eggPatternItem(ItemModelGenerators g, Item item) {
        // Check if item is of the correct type.
        if (!(item instanceof EggPatternItem)) {
            return;
        }

        List<SelectItemModel.SwitchCase<EggPattern>> modelCases = new ArrayList<>();

        // For each egg pattern, generate a separate item model that will be used as the override model.
        for (EggPattern pattern : EggPattern.values()) {
            // Generate the item model for this specific pattern.
            Identifier patternItemLoc = ItemUtils.getItemIdentifierWithPrefix(item, pattern.getName());
            ModelTemplates.FLAT_ITEM.create(patternItemLoc,
                    TextureMapping.layer0(ItemUtils.getItemIdentifierWithSuffix(item, pattern.getName())), g.modelOutput);

            // Add override for this pattern, which will be added to the base item.
            ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(patternItemLoc);
            modelCases.add(ItemModelUtils.when(pattern, unbaked));
        }

        // Add base item model with all cases.
        ItemModel.Unbaked fallbackModel = ItemModelUtils.plainModel(g.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        g.itemModelOutput.accept(item, ItemModelUtils.select(new ComponentContents<>(ModDataComponents.EGG_PATTERN), fallbackModel, modelCases));
    }

    private static void easterEggItem(ItemModelGenerators g, Item item) {
        // Check if item is of the correct type.
        if (!(item instanceof DyedEggItem)) {
            return;
        }

        List<SelectItemModel.SwitchCase<DyeColor>> baseCases = new ArrayList<>();

        // For each override defined above, generate a separate item model that will be used as the override model.
        for (DyeColor baseColor : DyeColor.values()) {
            // First, generate an override for the base color variant without any pattern.
            Identifier baseItemLoc = ItemUtils.getItemIdentifierWithPrefix(item, baseColor.getName());
            ModelTemplates.FLAT_ITEM.create(baseItemLoc,
                    TextureMapping.layer0(ItemUtils.getItemIdentifierWithSuffix(item, baseColor.getName())), g.modelOutput);

            List<SelectItemModel.SwitchCase<DyeColor>> patternColorCases = new ArrayList<>();
            ItemModel.Unbaked fallbackModel = ItemModelUtils.plainModel(baseItemLoc);

            // Loop through all other dye colors, which can be applied as a pattern.
            for (DyeColor patternColor : DyeColor.values()) {
                // If base and pattern color are the same, skip this iteration.
                if (baseColor == patternColor) {
                    continue;
                }

                List<SelectItemModel.SwitchCase<EggPattern>> eggPatternCases = new ArrayList<>();

                // Loop through all other dye colors, which can be applied as a pattern.
                for (EggPattern pattern : EggPattern.values()) {
                    // First, generate an override for the base color variant without any pattern.
                    Identifier patternItemLoc = ItemUtils.getItemIdentifier(baseColor.getName() + "_" + ItemUtils.getName(item) + "_" + pattern.getName() + "_" + patternColor.getName());
                    ModelTemplates.TWO_LAYERED_ITEM.create(patternItemLoc,
                            TextureMapping.layered(
                                ItemUtils.getItemIdentifierWithSuffix(item, baseColor.getName()),
                                Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, "dyed_egg/patterns/" + pattern.getName() + "_" + patternColor.getName())
                            ), g.modelOutput);

                    // Add override for this pattern, which will be added to the base item.
                    ItemModel.Unbaked model = ItemModelUtils.plainModel(patternItemLoc);
                    eggPatternCases.add(ItemModelUtils.when(pattern, model));
                }

                // Add override for this pattern, which will be added to the base item.
                ItemModel.Unbaked model = ItemModelUtils.select(new ComponentContents<>(ModDataComponents.EGG_PATTERN), fallbackModel, eggPatternCases);
                patternColorCases.add(ItemModelUtils.when(patternColor, model));
            }

            // Add override for this pattern, which will be added to the base item.
            ItemModel.Unbaked model = ItemModelUtils.select(new ComponentContents<>(ModDataComponents.PATTERN_COLOR), fallbackModel, patternColorCases);
            baseCases.add(ItemModelUtils.when(baseColor, model));
        }

        // Add base item model with all cases.
        ItemModel.Unbaked fallbackModel = ItemModelUtils.plainModel(g.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        g.itemModelOutput.accept(item, ItemModelUtils.select(new ComponentContents<>(DataComponents.BASE_COLOR), fallbackModel, baseCases));
    }

    // A simple item with a model generated from its sprite.
    private static void simpleItem(ItemModelGenerators generator, Item item) {
        generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    // For blocks that appear as a block in-world but as an item in-hand
    private static void simpleBlock2D(ItemModelGenerators generator, Block block) {
        generator.generateFlatItem(block.asItem(), ModelTemplates.FLAT_ITEM);
    }
}
