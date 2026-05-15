package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyedEggItem extends BlockItem {
    public DyedEggItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        CompoundTag tag = stack.getOrCreateTag();

        // Row 1: base color as a dye-item name (e.g. "Red Dye"). Reuses vanilla's already-translated
        // dye item names so we don't have to ship 16 colour translations per language.
        String baseColorKey = tag.getString(ModDataComponents.BASE_COLOR);
        if (!baseColorKey.isEmpty()) {
            DyeColor baseColor = DyeColor.byName(baseColorKey, DyeColor.WHITE);
            tooltipComponents.add(DyeItem.byColor(baseColor).getDescription().copy().withStyle(ChatFormatting.GRAY));
        }

        // Row 2 (only when patterned): "{pattern} ({pattern_dye})" — putting the dye-item name in
        // parens dodges every color-noun agreement issue across languages (gender, case, word order)
        // because vanilla already ships a fully-translated dye item name per locale. The format
        // string is still a translation key so locales can swap bracketing style if they want.
        String patternKey = tag.getString(ModDataComponents.EGG_PATTERN);
        String patternColorKey = tag.getString(ModDataComponents.PATTERN_COLOR);
        if (!patternKey.isEmpty() && !patternColorKey.isEmpty()) {
            DyeColor patternColor = DyeColor.byName(patternColorKey, DyeColor.WHITE);
            Component patternName = Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern." + patternKey);
            Component patternDye = DyeItem.byColor(patternColor).getDescription();
            tooltipComponents.add(Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern.format", patternName, patternDye).withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
