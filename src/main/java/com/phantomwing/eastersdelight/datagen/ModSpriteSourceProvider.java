package com.phantomwing.eastersdelight.datagen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.*;
import java.util.stream.Collectors;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper, EastersDelight.MOD_ID);
    }

    @Override
    protected void addSources() {
        addEasterEggsToBlocksAtlas();
    }

    // Expand the existing Blocks atlas with different permutations of the easter eggs.
    private void addEasterEggsToBlocksAtlas() {
        // Generate a list of texture locations to add to the atlas.
        List<ResourceLocation> textures = new ArrayList<>();
        textures.add(getItemTexture("dyed_egg"));
        for (EggPattern pattern : EggPattern.values()) {
            textures.add(getPatternTexture(pattern));
        }

        // Gather a map of color palettes to use when generating the atlas.
        ResourceLocation colorKey = new ResourceLocation(EastersDelight.MOD_ID, "dyed_egg/color_palettes/color_key");
        Map<String, ResourceLocation> colorPalettes = Arrays.stream(DyeColor.values())
                .collect(Collectors.toMap(DyeColor::getName, color -> new ResourceLocation(EastersDelight.MOD_ID, "dyed_egg/color_palettes/" + color.getName())));

        // Add a new source to the Blocks atlas.
        atlas(BLOCKS_ATLAS).addSource(new PalettedPermutations(textures, colorKey, colorPalettes));
    }

    private ResourceLocation getItemTexture(String name) {
        return new ResourceLocation(EastersDelight.MOD_ID, "item/" + name);
    }

    private ResourceLocation getPatternTexture(EggPattern pattern) {
        return new ResourceLocation(EastersDelight.MOD_ID, "dyed_egg/patterns/" + pattern.getName());
    }
}
