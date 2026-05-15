package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class EggPatternItem extends Item {
    public EggPatternItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        EggPattern pattern = stack.get(ModDataComponents.EGG_PATTERN);

        if (pattern != null) {
            // Lang key path migrated from `eastersdelight.tooltip.egg_pattern.X` to the conventional
            // `tooltip.eastersdelight.egg_pattern.X`. Gray styling applied here instead of via inline
            // §7/§r in the translation values.
            tooltipAdder.accept(Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern." + pattern.getName())
                    .withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
