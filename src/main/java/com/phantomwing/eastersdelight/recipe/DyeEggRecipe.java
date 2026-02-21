package com.phantomwing.eastersdelight.recipe;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DyeEggRecipe extends CustomRecipe {
    public DyeEggRecipe(CraftingBookCategory category) {
        super(category);
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
    public @NotNull ItemStack assemble(CraftingInput inv, HolderLookup.@NotNull Provider provider) {
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
            stack.set(DataComponents.BASE_COLOR, ((DyeItem) dyeStack.getItem()).getDyeColor());

            return stack;
        }
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipes.DYE_EGG_RECIPE;
    }
}