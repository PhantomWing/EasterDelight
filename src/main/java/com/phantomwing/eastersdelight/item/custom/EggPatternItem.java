package com.phantomwing.eastersdelight.item.custom;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EggPatternItem extends Item {
    public EggPatternItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        CompoundTag tag = stack.getOrCreateTag();
        String patternName = tag.getString(ModDataComponents.EGG_PATTERN);

        if (!patternName.isEmpty()) {
            // Lang key path migrated from `eastersdelight.tooltip.egg_pattern.X` to the conventional
            // `tooltip.eastersdelight.egg_pattern.X`. Gray styling applied here instead of via inline
            // §7/§r in the translation values.
            tooltipComponents.add(Component.translatable("tooltip." + EastersDelight.MOD_ID + ".egg_pattern." + patternName)
                    .withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
