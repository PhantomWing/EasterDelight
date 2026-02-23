package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
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
        if (stack.has(ModDataComponents.EGG_PATTERN)) {
            EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);

            if (pattern == null) {
                return;
            }

            MutableComponent patternTooltip = Component.translatable(EastersDelight.MOD_ID + ".tooltip.egg_pattern." + pattern.getName());

            tooltipAdder.accept(patternTooltip);
        }

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
