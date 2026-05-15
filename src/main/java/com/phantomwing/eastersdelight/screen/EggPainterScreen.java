package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EggPainterScreen extends ItemCombinerScreen<EggPainterMenu> {
    private static final Identifier TEXTURE = getGUITexture(BuiltInRegistries.BLOCK.getKey(ModBlocks.EGG_PAINTER).getPath());
    private static final Identifier EMPTY_SLOT_EGG = getIconTexture("empty_slot_egg");
    private static final Identifier EMPTY_SLOT_COLOR = getIconTexture("empty_slot_color");
    private static final Identifier EMPTY_SLOT_PATTERN = getIconTexture("empty_slot_pattern");

    private final CyclingSlotBackground eggIcon = new CyclingSlotBackground(EggPainterMenu.EGG_SLOT);
    private final CyclingSlotBackground baseColorIcon = new CyclingSlotBackground(EggPainterMenu.BASE_COLOR_SLOT);
    private final CyclingSlotBackground patternIcon = new CyclingSlotBackground(EggPainterMenu.PATTERN_SLOT);
    private final CyclingSlotBackground patternColorIcon = new CyclingSlotBackground(EggPainterMenu.PATTERN_COLOR_SLOT);

    private static final Component MISSING_EGG_TOOLTIP = Component.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_egg_tooltip");
    private static final Component MISSING_BASE_COLOR_TOOLTIP = Component.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_base_color_tooltip");
    private static final Component MISSING_PATTERN_TOOLTIP = Component.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_pattern_tooltip");
    private static final Component MISSING_PATTERN_COLOR_TOOLTIP = Component.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_pattern_color_tooltip");

    public EggPainterScreen(EggPainterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    protected void extractErrorIcon(@NotNull GuiGraphicsExtractor guiGraphicsExtractor, int x, int y) {
        // We do not have an error state.
    }

    @Override
    public void containerTick() {
        super.containerTick();

        // Determine default icons when slots are empty.
        this.eggIcon.tick(List.of(EMPTY_SLOT_EGG));
        this.baseColorIcon.tick(List.of(EMPTY_SLOT_COLOR));
        this.patternIcon.tick(List.of(EMPTY_SLOT_PATTERN));
        this.patternColorIcon.tick(List.of(EMPTY_SLOT_COLOR));
    }

    // 26.1: render/renderBg overrides were removed from Screen/AbstractContainerScreen. Background
    // is now drawn via extractBackground (the parent ItemCombinerScreen already renders our menu
    // texture); we extend it to draw the empty-slot icons. Tooltips are queued via
    // setTooltipForNextFrame from within the same extract pass.
    @Override
    public void extractBackground(@NotNull GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphicsExtractor, mouseX, mouseY, partialTick);

        // Render default icons when slots are empty.
        this.eggIcon.extractRenderState(this.menu, guiGraphicsExtractor, partialTick, this.leftPos, this.topPos);
        this.baseColorIcon.extractRenderState(this.menu, guiGraphicsExtractor, partialTick, this.leftPos, this.topPos);
        this.patternIcon.extractRenderState(this.menu, guiGraphicsExtractor, partialTick, this.leftPos, this.topPos);
        this.patternColorIcon.extractRenderState(this.menu, guiGraphicsExtractor, partialTick, this.leftPos, this.topPos);

        // Queue tooltips for empty input slots.
        if (this.hoveredSlot != null) {
            ItemStack hoveredItem = this.hoveredSlot.getItem();
            if (hoveredItem.isEmpty()) {
                Component tooltip = null;
                if (this.hoveredSlot.index == EggPainterMenu.EGG_SLOT) {
                    tooltip = MISSING_EGG_TOOLTIP;
                } else if (this.hoveredSlot.index == EggPainterMenu.BASE_COLOR_SLOT) {
                    tooltip = MISSING_BASE_COLOR_TOOLTIP;
                } else if (this.hoveredSlot.index == EggPainterMenu.PATTERN_SLOT) {
                    tooltip = MISSING_PATTERN_TOOLTIP;
                } else if (this.hoveredSlot.index == EggPainterMenu.PATTERN_COLOR_SLOT) {
                    tooltip = MISSING_PATTERN_COLOR_TOOLTIP;
                }

                if (tooltip != null) {
                    guiGraphicsExtractor.setTooltipForNextFrame(this.font, tooltip, mouseX, mouseY);
                }
            }
        }
    }

    private static Identifier getGUITexture(String textureName) {
        return Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID,
                "textures/gui/container/" + textureName + ".png"
        );
    }

    private static Identifier getIconTexture(String textureName) {
        return Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID,
                "container/egg_painter/" + textureName
        );
    }
}
