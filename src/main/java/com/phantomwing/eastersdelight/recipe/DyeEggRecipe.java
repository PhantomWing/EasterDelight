package com.phantomwing.eastersdelight.recipe;

import com.mojang.serialization.MapCodec;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DyeEggRecipe extends CustomRecipe {
    // 26.1: CustomRecipe is parameterless and uses static codec-driven serializers instead
    // of the old `new CustomRecipe.Serializer<>(constructor)` factory.
    public static final DyeEggRecipe INSTANCE = new DyeEggRecipe();
    public static final MapCodec<DyeEggRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DyeEggRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DyeEggRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public DyeEggRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput inv, @NotNull Level level) {
        ItemStack dyeStack = ItemStack.EMPTY;
        ItemStack paintableEggStack = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getItem(i);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof DyeItem) {
                    if (!dyeStack.isEmpty()) {
                        return false;
                    }

                    dyeStack = stack;
                } else if (stack.is(ModTags.Items.PAINTABLE_EGGS)) {

                    if (!paintableEggStack.isEmpty()) {
                        return false;
                    }

                    paintableEggStack = stack;
                } else {
                    return false;
                }
            }
        }

        return !dyeStack.isEmpty() && !paintableEggStack.isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput inv) {
        ItemStack dyeStack = ItemStack.EMPTY;
        ItemStack paintableEggStack = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getItem(i);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof DyeItem) {
                    dyeStack = stack.copy();
                    dyeStack.setCount(1);
                } else if (stack.is(ModTags.Items.PAINTABLE_EGGS)) {
                    paintableEggStack = stack.copy();
                    paintableEggStack.setCount(1);
                }
            }
        }

        if (paintableEggStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack stack = new ItemStack(ModItems.DYED_EGG);
            // 26.1: DyeItem no longer exposes getDyeColor(); the color now lives in the DYE data
            // component on the dye item's stack.
            DyeColor color = dyeStack.get(DataComponents.DYE);
            if (color != null) {
                stack.set(DataComponents.BASE_COLOR, color);
            }

            return stack;
        }
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipes.DYE_EGG_RECIPE;
    }
}
