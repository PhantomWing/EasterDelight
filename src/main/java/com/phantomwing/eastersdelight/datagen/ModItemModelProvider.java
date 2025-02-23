package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.item.ModItemProperties;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.item.custom.EasterEggItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EastersDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleItem(ModItems.BOILED_EGG);
        easterEggItem(ModItems.EASTER_EGG);

        // Blocks
    }

    private void easterEggItem(DeferredItem<Item> item) {
        // Check if item is of the correct type.
        if (!(item.get() instanceof EasterEggItem)) {
            return;
        }

        // Generate the base item model.
        simpleItem(item);

        // Loop through all dye colors.
        for (DyeColor baseColor : DyeColor.values()) {
            String colorPrefix = baseColor.getName() + "_";
            String colorSuffix = "_" + baseColor.getName();
            ResourceLocation baseModel = getItemResourceLocation(item, colorPrefix);
            ResourceLocation baseTexture = getItemResourceLocation(item, "", colorSuffix);

            // Generate an item model with just the base color (no pattern).
            markAsGenerated(baseTexture);
            getBuilder(colorPrefix + getItemName(item))
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", baseTexture);

            // Add a model predicate for this base color texture variant.
            this.withExistingParent(getItemName(item), baseModel())
                    .override()
                    .model(new ModelFile.UncheckedModelFile(baseModel))
                    .predicate(ModItemProperties.BASE_COLOR, baseColor.getId())
                    .end();

            // Loop through all other dye colors, which can be applied as a pattern.
            for (DyeColor patternColor : DyeColor.values()) {
                // If base and pattern color are the same, skip this iteration.
                if (baseColor == patternColor) {
                    continue;
                }

                // Loop through all other dye colors, which can be applied as a pattern.
                for (EggPattern pattern : EggPattern.values()) {
                    String patternName = pattern.getName();
                    String patternColorSuffix = "_" + patternColor.getName();
                    String patternSuffix = "_" + patternName + patternColorSuffix;
                    ResourceLocation patternModel = getItemResourceLocation(item, colorPrefix, patternSuffix);
                    ResourceLocation patternTexture = getPatternResourceLocation(patternName, patternColorSuffix);

                    // This will avoid an IllegalArgumentException.
                    existingFileHelper.trackGenerated(patternTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                    // Generate an egg painted in the base color, with an additional pattern overlay
                    getBuilder(colorPrefix + getItemName(item) + patternSuffix)
                            .parent(new ModelFile.UncheckedModelFile("item/generated"))
                            .texture("layer0", baseTexture)
                            .texture("layer1", patternTexture);

                    // Add a model predicate for this pattern texture variant.
                    this.withExistingParent(getItemName(item), baseModel())
                            .override()
                            .model(new ModelFile.UncheckedModelFile(patternModel))
                            .predicate(ModItemProperties.BASE_COLOR, baseColor.getId())
                            .predicate(ModItemProperties.PATTERN_COLOR, patternColor.getId())
                            .predicate(ModItemProperties.EGG_PATTERN, pattern.getId())
                            .end();
                }
            }
        }
    }

    private ResourceLocation baseModel() {
        return ResourceLocation.withDefaultNamespace("item/generated");
    }

    // As we generate textures dynamically at runtime, we tell the ExistingFileHelper that this texture exists to avoid an IllegalArgumentException.
    private void markAsGenerated(ResourceLocation texture) {
        existingFileHelper.trackGenerated(texture, PackType.CLIENT_RESOURCES, ".png", "textures");
    }

    // A simple item with a model generated from its sprite.
    private void simpleItem(DeferredItem<Item> item) {
        withExistingParent(getItemName(item), baseModel())
                .texture("layer0", getItemResourceLocation(item));
    }

    // For blocks like stairs/slabs that have multiple models, but need a single model in inventory.
    private void simpleBlock(DeferredBlock<Block> item) {
        this.withExistingParent(EastersDelight.MOD_ID + ":" + getItemName(item), getBlockResourceLocation(item));
    }

    // For blocks that appear as a block in-world but as an item in-hand
    private void simpleBlock2D(DeferredBlock<Block> item) {
        withExistingParent(getItemName(item), baseModel())
                .texture("layer0", getBlockResourceLocation(item));
    }

    private String getItemName(DeferredItem<Item> item) {
        return item.getId().getPath();
    }

    private String getItemName(DeferredBlock<Block> item) {
        return item.getId().getPath();
    }

    private ResourceLocation getItemResourceLocation(DeferredItem<Item> item) {
        return getItemResourceLocation(item, "");
    }

    private ResourceLocation getItemResourceLocation(DeferredItem<Item> item, String prefix) {
        return getItemResourceLocation(item, prefix, "");
    }

    private ResourceLocation getItemResourceLocation(DeferredItem<Item> item, String prefix, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "item/" + prefix + getItemName(item) + suffix);
    }

    private ResourceLocation getPatternResourceLocation(String patternName, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/patterns/" + patternName + suffix);
    }

    private ResourceLocation getBlockResourceLocation(DeferredBlock<Block> item) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "block/" + getItemName(item));
    }
}
