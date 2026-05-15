package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DyedEggItem extends BlockItem {
    public DyedEggItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // Row 1: base color as a dye-item name (e.g. "Red Dye"). Reuses vanilla's already-translated
        // dye item names so we don't have to ship 16 colour translations per language.
        DyeColor baseColor = stack.get(DataComponents.BASE_COLOR);
        if (baseColor != null) {
            tooltipComponents.add(DyeItem.byColor(baseColor).getDescription().copy().withStyle(ChatFormatting.GRAY));
        }

        // Row 2 (only when patterned): "{pattern} ({pattern_dye})" — putting the dye-item name in
        // parens dodges every color-noun agreement issue across languages (gender, case, word order)
        // because vanilla already ships a fully-translated dye item name per locale. The format
        // string is still a translation key so locales can swap bracketing style if they want.
        EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);
        DyeColor patternColor = stack.get(ModDataComponents.PATTERN_COLOR);
        if (pattern != null && patternColor != null) {
            Component patternName = Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern." + pattern.getName());
            Component patternDye = DyeItem.byColor(patternColor).getDescription();
            tooltipComponents.add(Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern.format", patternName, patternDye).withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
