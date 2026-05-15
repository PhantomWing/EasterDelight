package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class DyedEggItem extends BlockItem {
    public DyedEggItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        // Row 1: base color as a dye-item name (e.g. "Red Dye"). 26.1 removed DyeItem.byColor(),
        // so we build the vanilla dye item's translation key directly — every dye follows the
        // `item.minecraft.<color>_dye` pattern and is already localized in every language.
        DyeColor baseColor = stack.get(DataComponents.BASE_COLOR);
        if (baseColor != null) {
            tooltipAdder.accept(Component.translatable("item.minecraft." + baseColor.getName() + "_dye")
                    .withStyle(ChatFormatting.GRAY));
        }

        // Row 2 (only when patterned): "{pattern} ({pattern_dye})" — putting the dye-item name in
        // parens dodges every color-noun agreement issue across languages (gender, case, word order)
        // because vanilla already ships a fully-translated dye item name per locale. The format
        // string is still a translation key so locales can swap bracketing style if they want.
        EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);
        DyeColor patternColor = stack.get(ModDataComponents.PATTERN_COLOR);
        if (pattern != null && patternColor != null) {
            Component patternName = Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern." + pattern.getName());
            Component patternDye = Component.translatable("item.minecraft." + patternColor.getName() + "_dye");
            tooltipAdder.accept(Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern.format", patternName, patternDye)
                    .withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
