package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EastersDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void gather() {
        // Expand the existing Blocks atlas with different permutations of the easter eggs.
        Map<String, ResourceLocation> colorPalettes = Arrays.stream(DyeColor.values())
                .collect(Collectors.toMap(DyeColor::getName, color -> ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/color_palettes/" + color.getName())));

        // Register base easter egg texture in all dye colors.
        atlas(BLOCKS_ATLAS)
                .addSource(new PalettedPermutations(
                        List.of(ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "item/easter_egg")),
                        ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/color_palettes/color_key"),
                        colorPalettes
                ));

        // Register patterned easter egg texture in all dye colors.
        for (EggPattern pattern : EggPattern.values()) {
            atlas(BLOCKS_ATLAS)
                    .addSource(new PalettedPermutations(
                            List.of(ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/patterns/" + pattern.getName())),
                            ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/color_palettes/color_key"),
                            colorPalettes
                    ));
        }
    }
}
