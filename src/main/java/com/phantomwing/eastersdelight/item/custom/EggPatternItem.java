package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EggPatternItem extends Item {
    public EggPatternItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);

        if (pattern != null) {
            MutableComponent patternTooltip = Component.translatable(EastersDelight.MOD_ID + ".tooltip.egg_pattern." + pattern.getName());

            tooltipComponents.add(patternTooltip);
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
