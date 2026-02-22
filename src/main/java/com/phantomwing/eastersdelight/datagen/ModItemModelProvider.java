package com.phantomwing.eastersdelight.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.itemProperties.EggPatternProperty;
import com.phantomwing.eastersdelight.itemProperties.ModItemProperties;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import com.phantomwing.eastersdelight.util.ItemUtils;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.CompassAngle;
import net.minecraft.client.renderer.item.properties.numeric.CompassAngleState;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
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

        List<RangeSelectItemModel.Entry> list = new ArrayList<>();

        // For each egg pattern, generate a separate item model that will be used as the override model.
        for (EggPattern pattern : EggPattern.values()) {
            // Generate the item model for this specific pattern.
            ResourceLocation patternItemLoc = ItemUtils.getItemResourceLocationWithPrefix(item, pattern.getName());
            ModelTemplates.FLAT_ITEM.create(patternItemLoc,
                    TextureMapping.layer0(ItemUtils.getItemResourceLocationWithSuffix(item, pattern.getName())), g.modelOutput);

            // Add override for this pattern, which will be added to the base item.
            ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(patternItemLoc);
            list.add(ItemModelUtils.override(unbaked, pattern.getId()));
        }

        g.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new EggPatternProperty(), EggPattern.values().length, list));
    }

    private static void easterEggItem(ItemModelGenerators g, Item item) {
        // Check if item is of the correct type.
        if (!(item instanceof DyedEggItem)) {
            return;
        }

        // For each override defined above, generate a separate item model that will be used as the override model.
        for (DyeColor baseColor : DyeColor.values()) {
            // First, generate an override for the base color variant without any pattern.
            ResourceLocation baseItemLoc = ItemUtils.getItemResourceLocationWithPrefix(item, baseColor.getName());
            ModelTemplates.FLAT_ITEM.create(baseItemLoc,
                    TextureMapping.layer0(ItemUtils.getItemResourceLocationWithSuffix(item, baseColor.getName())), g.modelOutput);

            // Loop through all other dye colors, which can be applied as a pattern.
            for (DyeColor patternColor : DyeColor.values()) {
                // If base and pattern color are the same, skip this iteration.
                if (baseColor == patternColor) {
                    continue;
                }

                // Loop through all other dye colors, which can be applied as a pattern.
                for (EggPattern pattern : EggPattern.values()) {
                    // First, generate an override for the base color variant without any pattern.
                    ResourceLocation patternItemLoc = ItemUtils.getItemResourceLocation(baseColor.getName() + "_" + ItemUtils.getName(item) + "_" + pattern.getName() + "_" + patternColor.getName());
                    ModelTemplates.TWO_LAYERED_ITEM.create(patternItemLoc,
                            TextureMapping.layered(
                                ItemUtils.getItemResourceLocationWithSuffix(item, baseColor.getName()),
                                ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "dyed_egg/patterns/" + pattern.getName() + "_" + patternColor.getName())
                            ), g.modelOutput);
                }
            }
        }
    }

    // A simple item with a model generated from its sprite.
    private static void simpleItem(ItemModelGenerators generator, Item item) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), generator.modelOutput);
    }

    // For blocks that appear as a block in-world but as an item in-hand
    private static void simpleBlock2D(ItemModelGenerators generator, Block block) {
        simpleItem(generator, block.asItem());
    }
}
