package com.phantomwing.eastersdelight.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.item.custom.DyedEggItem;
import com.phantomwing.eastersdelight.itemProperties.ModItemProperties;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.EggPatternItem;
import com.phantomwing.eastersdelight.util.ItemUtils;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

        // Add a model for the base egg_pattern item, containing all the overrides with their predicates.
        ModelTemplates.FLAT_ITEM.create(ItemUtils.getItemResourceLocation(item), TextureMapping.layer0(item), g.output,
                (resLoc, map) -> {
                    JsonObject jsonObject = ModelTemplates.FLAT_ITEM.createBaseTemplate(resLoc, map);
                    JsonArray overridesArray = new JsonArray();

                    for (EggPattern pattern : EggPattern.values()) {
                        ResourceLocation patternModelLoc = ItemUtils.getItemResourceLocationWithPrefix(item, pattern.getName()); // e.g. eastersdelight:item/dipped_egg_pattern

                        // Prepare override object for the base egg_pattern item.
                        JsonObject predicateObject = new JsonObject();
                        predicateObject.addProperty(ModItemProperties.EGG_PATTERN.toString(), (float) pattern.getId());

                        JsonObject overrideObject = new JsonObject();
                        overrideObject.addProperty("model", patternModelLoc.toString());
                        overrideObject.add("predicate", predicateObject);
                        overridesArray.add(overrideObject);
                    }

                    jsonObject.add("overrides", overridesArray);
                    return jsonObject;
                });

        // For each egg pattern, generate a separate item model that will be used as the override model.
        for (EggPattern pattern : EggPattern.values()) {
            // Generate the item model for this specific pattern.
            ResourceLocation patternItemLoc = ItemUtils.getItemResourceLocationWithPrefix(item, pattern.getName());
            ModelTemplates.FLAT_ITEM.create(patternItemLoc,
                    TextureMapping.layer0(ItemUtils.getItemResourceLocationWithSuffix(item, pattern.getName())), g.output);
        }
    }

    private static void easterEggItem(ItemModelGenerators g, Item item) {
        // Check if item is of the correct type.
        if (!(item instanceof DyedEggItem)) {
            return;
        }

        // Add a model predicate for this base color texture variant.
        ModelTemplates.FLAT_ITEM.create(ItemUtils.getItemResourceLocation(item), TextureMapping.layer0(item), g.output,
            (resLoc, map) -> {
                JsonObject jsonObject = ModelTemplates.FLAT_ITEM.createBaseTemplate(resLoc, map);
                JsonArray overridesArray = new JsonArray();

                // Loop through all dye colors.
                for (DyeColor baseColor : DyeColor.values()) {
                    // First, add an override for the base color variant without any pattern.
                    JsonObject basePredicateObject = new JsonObject();
                    basePredicateObject.addProperty(ModItemProperties.BASE_COLOR.toString(), (float) baseColor.getId());

                    JsonObject baseOverrideObject = new JsonObject();
                    baseOverrideObject.addProperty("model", ItemUtils.getItemResourceLocationWithPrefix(item, baseColor.getName()).toString());
                    baseOverrideObject.add("predicate", basePredicateObject);
                    overridesArray.add(baseOverrideObject);

                    // Loop through all other dye colors, which can be applied as a pattern.
                    for (DyeColor patternColor : DyeColor.values()) {
                        // If base and pattern color are the same, skip this iteration.
                        if (baseColor == patternColor) {
                            continue;
                        }

                        // Loop through all other dye colors, which can be applied as a pattern.
                        for (EggPattern pattern : EggPattern.values()) {
                            // Add an override for this combination of base color and pattern.
                            JsonObject predicateObject = new JsonObject();
                            predicateObject.addProperty(ModItemProperties.BASE_COLOR.toString(), (float) baseColor.getId());
                            predicateObject.addProperty(ModItemProperties.PATTERN_COLOR.toString(), (float) patternColor.getId());
                            predicateObject.addProperty(ModItemProperties.EGG_PATTERN.toString(), (float) pattern.getId());

                            JsonObject overrideObject = new JsonObject();
                            overrideObject.addProperty("model", ItemUtils.getItemResourceLocationWithPrefix(item, baseColor.getName()).toString() + "_" + pattern.getName() + "_" + patternColor.getName());
                            overrideObject.add("predicate", predicateObject);
                            overridesArray.add(overrideObject);
                        }
                    }
                }

                jsonObject.add("overrides", overridesArray);
                return jsonObject;
            }
        );

        // For each override defined above, generate a separate item model that will be used as the override model.
        for (DyeColor baseColor : DyeColor.values()) {
            // First, generate an override for the base color variant without any pattern.
            ResourceLocation baseItemLoc = ItemUtils.getItemResourceLocationWithPrefix(item, baseColor.getName());
            ModelTemplates.FLAT_ITEM.create(baseItemLoc,
                    TextureMapping.layer0(ItemUtils.getItemResourceLocationWithSuffix(item, baseColor.getName())), g.output);

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
                            ), g.output);
                }
            }
        }
    }

    // A simple item with a model generated from its sprite.
    private static void simpleItem(ItemModelGenerators generator, Item item) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), generator.output);
    }

    // For blocks that appear as a block in-world but as an item in-hand
    private static void simpleBlock2D(ItemModelGenerators generator, Block block) {
        simpleItem(generator, block.asItem());
    }
}
