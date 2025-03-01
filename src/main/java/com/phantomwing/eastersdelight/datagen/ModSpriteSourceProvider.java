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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EastersDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void gather() {
        addEasterEggsToBlocksAtlas();
    }

    // Expand the existing Blocks atlas with different permutations of the easter eggs.
    private void addEasterEggsToBlocksAtlas() {
        // Generate a list of texture locations to add to the atlas.
        List<ResourceLocation> textures = new ArrayList<>();
        textures.add(getItemTexture("easter_egg"));
        for (EggPattern pattern : EggPattern.values()) {
            textures.add(getPatternTexture(pattern));
        }

        // Gather a map of color palettes to use when generating the atlas.
        ResourceLocation colorKey = ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/color_palettes/color_key");
        Map<String, ResourceLocation> colorPalettes = Arrays.stream(DyeColor.values())
                .collect(Collectors.toMap(DyeColor::getName, color -> ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/color_palettes/" + color.getName())));

        // Add a new source to the Blocks atlas.
        atlas(BLOCKS_ATLAS).addSource(new PalettedPermutations(textures, colorKey, colorPalettes));
    }

    private ResourceLocation getItemTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "item/" + name);
    }

    private ResourceLocation getPatternTexture(EggPattern pattern) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "easter_egg/patterns/" + pattern.getName());
    }
}
