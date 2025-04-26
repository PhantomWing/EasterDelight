package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalInt;

public class EggPainterScreenHandler extends ForgingScreenHandler {
    public static final int EGG_SLOT = 0;
    public static final int BASE_COLOR_SLOT = 1;
    public static final int PATTERN_SLOT = 2;
    public static final int PATTERN_COLOR_SLOT = 3;
    public static final int RESULT_SLOT = 4;

    private final World level;

    public EggPainterScreenHandler(int containerId, PlayerInventory playerInventory) {
        this(containerId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public EggPainterScreenHandler(int containerId, PlayerInventory playerInventory, ScreenHandlerContext access) {
        super(ModScreenHandlers.EGG_PAINTER, containerId, playerInventory, access);
        this.level = playerInventory.player.getWorld();
    }

    protected @NotNull ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create()
                .input(EGG_SLOT, 49, 20, (item) -> item.isIn(ModTags.Items.PAINTABLE_EGGS)) // Must be a paintable egg
                .input(BASE_COLOR_SLOT, 31, 49, (item) -> item.getItem() instanceof DyeItem) // Base color
                .input(PATTERN_SLOT, 49, 49, (item) -> item.isOf(ModItems.EGG_PATTERN)) // Egg pattern
                .input(PATTERN_COLOR_SLOT, 67, 49, (item) -> item.getItem() instanceof DyeItem) // Pattern color
                .output(RESULT_SLOT, 125, 49)
                .build();
    }

    protected boolean canUse(BlockState state) {
        return state.isOf(ModBlocks.EGG_PAINTER);
    }

    protected boolean canTakeOutput(@NotNull PlayerEntity player, boolean hasStack) {
        return hasRequiredInputs();
    }

    protected void onTakeOutput(@NotNull PlayerEntity player, ItemStack stack) {
        stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
        this.output.unlockLastRecipe(player, this.getRelevantItems());

        this.shrinkStackInSlot(EGG_SLOT);
        this.shrinkStackInSlot(BASE_COLOR_SLOT);

        // Check if a pattern + pattern color have been provided.
        if (hasPatternInputs()) {
            this.shrinkStackInSlot(PATTERN_SLOT);
            this.shrinkStackInSlot(PATTERN_COLOR_SLOT);
        }

        this.context.run((level, blockPos) -> {
            level.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, blockPos, 0);
        });
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(
            this.input.getStack(EGG_SLOT),
            this.input.getStack(BASE_COLOR_SLOT),
            this.input.getStack(PATTERN_SLOT),
            this.input.getStack(PATTERN_COLOR_SLOT)
        );
    }

    private void shrinkStackInSlot(int index) {
        ItemStack itemstack = this.input.getStack(index);
        if (!itemstack.isEmpty()) {
            itemstack.decrement(1);
            this.input.setStack(index, itemstack);
        }
    }

    @Override
    public void updateResult() {
        // Check if the base input is valid.
        if (hasRequiredInputs()) {
            // Create the easter egg item.
            ItemStack itemstack = new ItemStack(ModItems.DYED_EGG);

            // Apply a base color.
            DyeItem baseDye = (DyeItem)this.input.getStack(BASE_COLOR_SLOT).getItem();
            itemstack.set(DataComponentTypes.BASE_COLOR, baseDye.getColor());

            // Check if the pattern input is valid.
            if (hasPatternInputs())
            {
                // Apply the egg pattern.
                EggPattern eggPattern = this.input.getStack(PATTERN_SLOT).get(ModDataComponents.EGG_PATTERN);
                itemstack.set(ModDataComponents.EGG_PATTERN, eggPattern);

                // Apply the pattern color
                DyeItem patternDye = (DyeItem)this.input.getStack(PATTERN_COLOR_SLOT).getItem();
                itemstack.set(ModDataComponents.PATTERN_COLOR, patternDye.getColor());
            }

            // Show the painted item in the result slot.
            if (itemstack.isItemEnabled(this.level.getEnabledFeatures())) {
                this.output.setStack(0, itemstack);
            }
        } else {
            // Input is invalid: Clear the result slot.
            this.output.setStack(0, ItemStack.EMPTY);
        }
    }

    private boolean hasRequiredInputs() {
        // Egg and Color slot are required.
        return !this.input.getStack(EGG_SLOT).isEmpty()
                && !this.input.getStack(BASE_COLOR_SLOT).isEmpty();
    }

    private boolean hasPatternInputs() {
        return !this.input.getStack(PATTERN_SLOT).isEmpty()  // We should have a pattern
                && !this.input.getStack(PATTERN_COLOR_SLOT).isEmpty()  // And a pattern color
                && !this.input.getStack(PATTERN_COLOR_SLOT).isOf(this.input.getStack(BASE_COLOR_SLOT).getItem()); // Which should be different from the base color
    }

    public int getSlotFor(@NotNull ItemStack stack) {
        return this.getQuickMoveSlot(stack).orElse(EGG_SLOT);
    }

    public boolean canInsertIntoSlot(@NotNull ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    public boolean isValidIngredient(@NotNull ItemStack stack) {
        return this.getQuickMoveSlot(stack).isPresent();
    }

    private OptionalInt getQuickMoveSlot(ItemStack stack) {
        if (stack.isIn(ModTags.Items.PAINTABLE_EGGS))
        {
            return OptionalInt.of(EGG_SLOT);
        } else if (stack.isOf(ModItems.EGG_PATTERN)) {
            return OptionalInt.of(PATTERN_SLOT);
        } else if (stack.getItem() instanceof DyeItem) {
            if (!this.getSlot(BASE_COLOR_SLOT).hasStack() || this.getSlot(BASE_COLOR_SLOT).getStack().isOf(stack.getItem())) {
                return OptionalInt.of(BASE_COLOR_SLOT);
            }
            return OptionalInt.of(PATTERN_COLOR_SLOT);
        }

        return OptionalInt.empty();
    }
}
