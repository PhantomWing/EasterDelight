package com.phantomwing.eastersdelight.screen;

import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalInt;

public class EggPainterMenu extends ItemCombinerMenu {
    public static final int EGG_SLOT = 0;
    public static final int BASE_COLOR_SLOT = 1;
    public static final int PATTERN_SLOT = 2;
    public static final int PATTERN_COLOR_SLOT = 3;
    public static final int RESULT_SLOT = 4;

    private final Level level;

    public EggPainterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public EggPainterMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.EGG_PAINTER, containerId, playerInventory, access);
        this.level = playerInventory.player.level();
    }

    protected @NotNull ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(EGG_SLOT, 49, 20, (item) -> item.is(ModTags.Items.PAINTABLE_EGGS)) // Must be a paintable egg
                .withSlot(BASE_COLOR_SLOT, 31, 49, (item) -> item.getItem() instanceof DyeItem) // Base color
                .withSlot(PATTERN_SLOT, 49, 49, (item) -> item.is(ModItems.EGG_PATTERN)) // Egg pattern
                .withSlot(PATTERN_COLOR_SLOT, 67, 49, (item) -> item.getItem() instanceof DyeItem) // Pattern color
                .withResultSlot(RESULT_SLOT, 125, 49)
                .build();
    }

    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.EGG_PAINTER);
    }

    protected boolean mayPickup(@NotNull Player player, boolean hasStack) {
        return hasRequiredInputs();
    }

    protected void onTake(@NotNull Player player, ItemStack stack) {
        stack.onCraftedBy(player.level(), player, stack.getCount());
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());

        this.shrinkStackInSlot(EGG_SLOT);
        this.shrinkStackInSlot(BASE_COLOR_SLOT);

        // Check if a pattern + pattern color have been provided.
        if (hasPatternInputs()) {
            this.shrinkStackInSlot(PATTERN_SLOT);
            this.shrinkStackInSlot(PATTERN_COLOR_SLOT);
        }

        this.access.execute((level, blockPos) -> {
            level.levelEvent(1044, blockPos, 0);
        });
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(
                this.inputSlots.getItem(EGG_SLOT),
                this.inputSlots.getItem(BASE_COLOR_SLOT),
                this.inputSlots.getItem(PATTERN_SLOT),
                this.inputSlots.getItem(PATTERN_COLOR_SLOT)
        );
    }

    private void shrinkStackInSlot(int index) {
        ItemStack itemstack = this.inputSlots.getItem(index);
        if (!itemstack.isEmpty()) {
            itemstack.shrink(1);
            this.inputSlots.setItem(index, itemstack);
        }
    }

    public void createResult() {
        // Check if the base input is valid.
        if (hasRequiredInputs()) {
            // Create the easter egg item.
            ItemStack itemstack = new ItemStack(ModItems.DYED_EGG);

            // Apply a base color.
            DyeItem baseDye = (DyeItem)this.inputSlots.getItem(BASE_COLOR_SLOT).getItem();
            itemstack.set(DataComponents.BASE_COLOR, baseDye.getDyeColor());

            // Check if the pattern input is valid.
            if (hasPatternInputs())
            {
                // Apply the egg pattern.
                EggPattern eggPattern = this.inputSlots.getItem(PATTERN_SLOT).get(ModDataComponents.EGG_PATTERN);
                itemstack.set(ModDataComponents.EGG_PATTERN, eggPattern);

                // Apply the pattern color
                DyeItem patternDye = (DyeItem)this.inputSlots.getItem(PATTERN_COLOR_SLOT).getItem();
                itemstack.set(ModDataComponents.PATTERN_COLOR, patternDye.getDyeColor());
            }

            // Show the painted item in the result slot.
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultSlots.setItem(0, itemstack);
            }
        } else {
            // Input is invalid: Clear the result slot.
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        }
    }

    private boolean hasRequiredInputs() {
        // Egg and Color slot are required.
        return !this.inputSlots.getItem(EGG_SLOT).isEmpty()
                && !this.inputSlots.getItem(BASE_COLOR_SLOT).isEmpty();
    }

    private boolean hasPatternInputs() {
        return !this.inputSlots.getItem(PATTERN_SLOT).isEmpty()  // We should have a pattern
                && !this.inputSlots.getItem(PATTERN_COLOR_SLOT).isEmpty()  // And a pattern color
                && !this.inputSlots.getItem(PATTERN_COLOR_SLOT).is(this.inputSlots.getItem(BASE_COLOR_SLOT).getItem()); // Which should be different from the base color
    }

    public int getSlotToQuickMoveTo(@NotNull ItemStack stack) {
        return this.findSlotToQuickMoveTo(stack).orElse(EGG_SLOT);
    }

    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public boolean canMoveIntoInputSlots(@NotNull ItemStack stack) {
        return this.findSlotToQuickMoveTo(stack).isPresent();
    }

    private OptionalInt findSlotToQuickMoveTo(ItemStack stack) {
        if (stack.is(ModTags.Items.PAINTABLE_EGGS))
        {
            return OptionalInt.of(EGG_SLOT);
        } else if (stack.is(ModItems.EGG_PATTERN)) {
            return OptionalInt.of(PATTERN_SLOT);
        } else if (stack.getItem() instanceof DyeItem) {
            if (!this.getSlot(BASE_COLOR_SLOT).hasItem() || this.getSlot(BASE_COLOR_SLOT).getItem().is(stack.getItem())) {
                return OptionalInt.of(BASE_COLOR_SLOT);
            }
            return OptionalInt.of(PATTERN_COLOR_SLOT);
        }

        return OptionalInt.empty();
    }
}
