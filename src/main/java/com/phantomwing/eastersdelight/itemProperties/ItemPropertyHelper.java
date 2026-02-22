package com.phantomwing.eastersdelight.itemProperties;

import com.phantomwing.eastersdelight.EastersDelight;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class ItemPropertyHelper {

    public static void register() {
        EastersDelight.LOGGER.info("Registering item properties for " + EastersDelight.MOD_ID);

        // Dyed Egg properties: base color, pattern color, and pattern type.
        //SelectItemModelProperties.register(ModItems.DYED_EGG, ModItemProperties.BASE_COLOR, new UnclampedItemPropertyFunction<>(DataComponents.BASE_COLOR, DyeColor::getId));
        //FabricModelPredicateProviderRegistry.register(ModItems.DYED_EGG, ModItemProperties.PATTERN_COLOR, new UnclampedItemPropertyFunction<>(ModDataComponents.PATTERN_COLOR, DyeColor::getId));
        //FabricModelPredicateProviderRegistry.register(ModItems.DYED_EGG, ModItemProperties.EGG_PATTERN, new UnclampedItemPropertyFunction<>(ModDataComponents.EGG_PATTERN, EggPattern::getId));

        // Egg Pattern item property: pattern type.
        //FabricModelPredicateProviderRegistry.register(ModItems.EGG_PATTERN, ModItemProperties.EGG_PATTERN, new UnclampedItemPropertyFunction<>(ModDataComponents.EGG_PATTERN, EggPattern::getId));
    }
}
