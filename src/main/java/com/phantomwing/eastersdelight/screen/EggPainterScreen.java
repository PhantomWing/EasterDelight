package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.block.ModBlocks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class EggPainterScreen extends ForgingScreen<EggPainterScreenHandler> {
    private static final Identifier TEXTURE = getGUITexture(Registries.BLOCK.getId(ModBlocks.EGG_PAINTER).getPath());
    private static final Identifier EMPTY_SLOT_EGG = getIconTexture("empty_slot_egg");
    private static final Identifier EMPTY_SLOT_COLOR = getIconTexture("empty_slot_color");
    private static final Identifier EMPTY_SLOT_PATTERN = getIconTexture("empty_slot_pattern");

    private final CyclingSlotIcon eggIcon = new CyclingSlotIcon(EggPainterScreenHandler.EGG_SLOT);
    private final CyclingSlotIcon baseColorIcon = new CyclingSlotIcon(EggPainterScreenHandler.BASE_COLOR_SLOT);
    private final CyclingSlotIcon patternIcon = new CyclingSlotIcon(EggPainterScreenHandler.PATTERN_SLOT);
    private final CyclingSlotIcon patternColorIcon = new CyclingSlotIcon(EggPainterScreenHandler.PATTERN_COLOR_SLOT);

    private static final MutableText MISSING_EGG_TOOLTIP = Text.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_egg_tooltip");
    private static final MutableText MISSING_BASE_COLOR_TOOLTIP = Text.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_base_color_tooltip");
    private static final MutableText MISSING_PATTERN_TOOLTIP = Text.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_pattern_tooltip");
    private static final MutableText MISSING_PATTERN_COLOR_TOOLTIP = Text.translatable(EastersDelight.MOD_ID + ".container.egg_painter.missing_pattern_color_tooltip");

    public EggPainterScreen(EggPainterScreenHandler menu, PlayerInventory playerInventory, Text title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();

        // Determine default icons when slots are empty.
        this.eggIcon.updateTexture(List.of(EMPTY_SLOT_EGG));
        this.baseColorIcon.updateTexture(List.of(EMPTY_SLOT_COLOR));
        this.patternIcon.updateTexture(List.of(EMPTY_SLOT_PATTERN));
        this.patternColorIcon.updateTexture(List.of(EMPTY_SLOT_COLOR));
    }

    @Override
    public void render(@NotNull DrawContext context, int mouseX, int mouseY, float partialTick) {
        super.render(context, mouseX, mouseY, partialTick);
        this.renderTooltips(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);

        this.eggIcon.render(this.handler, context, delta, this.x, this.y);
        this.baseColorIcon.render(this.handler, context, delta, this.x, this.y);
        this.patternIcon.render(this.handler, context, delta, this.x, this.y);
        this.patternColorIcon.render(this.handler, context, delta, this.x, this.y);
    }

    @Override
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {
        // We do not have an error state.
    }

    private void renderTooltips(DrawContext context, int mouseX, int mouseY) {
        Optional<Text> optional = Optional.empty();

        if (this.focusedSlot != null) {
            ItemStack hoveredItem = this.focusedSlot.getStack();
            if (hoveredItem.isEmpty()) {
                if (this.focusedSlot.id == EggPainterScreenHandler.EGG_SLOT) {
                    optional = Optional.of(MISSING_EGG_TOOLTIP);
                }
                else if (this.focusedSlot.id == EggPainterScreenHandler.BASE_COLOR_SLOT) {
                    optional = Optional.of(MISSING_BASE_COLOR_TOOLTIP);
                }
                else if (this.focusedSlot.id == EggPainterScreenHandler.PATTERN_SLOT) {
                    optional = Optional.of(MISSING_PATTERN_TOOLTIP);
                }
                else if (this.focusedSlot.id == EggPainterScreenHandler.PATTERN_COLOR_SLOT) {
                    optional = Optional.of(MISSING_PATTERN_COLOR_TOOLTIP);
                }
            }
        }

        optional.ifPresent(text -> context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(text, 115), mouseX, mouseY));
    }

    private static Identifier getGUITexture(String textureName) {
        return Identifier.of(EastersDelight.MOD_ID,
                "textures/gui/" + textureName + ".png"
        );
    }

    private static Identifier getIconTexture(String textureName) {
        return Identifier.of(EastersDelight.MOD_ID,
                "item/" + textureName
        );
    }
}
