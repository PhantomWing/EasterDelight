package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.utils.EggUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyedEggItem extends Item {
    public DyedEggItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        EggPattern pattern = EggUtils.getPatternFromStack(stack);

        if (pattern != null) {
            MutableComponent patternTooltip = Component.translatable(EastersDelight.MOD_ID + ".tooltip.egg_pattern." + pattern.getName());

            DyeColor patternColor = EggUtils.getPatternColorFromStack(stack);
            if (patternColor != null) {
                MutableComponent patternColorTooltip = Component.translatable("color.minecraft." + patternColor.getName());
                patternTooltip.append(" (" + patternColorTooltip.getString() + ")");
            }

            tooltipComponents.add(patternTooltip);
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
