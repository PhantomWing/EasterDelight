package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
        String patternName = tag.getString(ModDataComponents.EGG_PATTERN);

        if (!patternName.isEmpty()) {
            MutableComponent patternTooltip = Component.translatable(EastersDelight.MOD_ID + ".tooltip.egg_pattern." + patternName);

            tooltipComponents.add(patternTooltip);
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
