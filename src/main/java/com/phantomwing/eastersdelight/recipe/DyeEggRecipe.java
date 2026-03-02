package com.phantomwing.eastersdelight.recipe;

import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DyeEggRecipe extends CustomRecipe {
    public DyeEggRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        ItemStack dyeStack = ItemStack.EMPTY;
        ItemStack paintableEggStack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
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
    public @NotNull ItemStack assemble(@Nonnull CraftingContainer inv, @NotNull RegistryAccess registryAccess) {
        ItemStack dyeStack = ItemStack.EMPTY;
        ItemStack paintableEggStack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
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
            ItemStack stack = new ItemStack(ModItems.DYED_EGG.get());
            CompoundTag tag = stack.getOrCreateTag();
            tag.putString(ModDataComponents.BASE_COLOR, ((DyeItem) dyeStack.getItem()).getDyeColor().getName());

            return stack;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.DYE_EGG_RECIPE.get();
    }
}