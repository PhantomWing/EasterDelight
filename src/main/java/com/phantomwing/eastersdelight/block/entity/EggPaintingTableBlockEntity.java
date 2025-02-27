package com.phantomwing.eastersdelight.block.entity;

import com.phantomwing.eastersdelight.block.inventory.EggPaintingTableItemHandler;
import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.screen.EggPaintingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EggPaintingTableBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private final ItemStackHandler inventory; // The inventory of the block.
    private final IItemHandler inputHandler;  // Handler for the input slot.
    private final IItemHandler outputHandler; // Handler for the output slot.

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1; // The total amount of slots in this block.

    public EggPaintingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.EGG_PAINTING_TABLE.get(), pos, blockState);

        this.inventory = createHandler();
        this.inputHandler = new EggPaintingTableItemHandler(inventory, Direction.UP);
        this.outputHandler = new EggPaintingTableItemHandler(inventory, Direction.DOWN);

        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int pValue) {
                switch (i) {
                    case 0 -> progress = pValue;
                    case 1 -> maxProgress = pValue;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntityTypes.EGG_PAINTING_TABLE.get(),
                (be, context) -> {
                    if (context == Direction.UP) {
                        return be.inputHandler;
                    }
                    return be.outputHandler;
                }
        );
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.eastersdelight.egg_painting_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new EggPaintingTableMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("egg_painting_table.progress", progress);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        itemHandler.deserializeNBT(registries, tag.getCompound("egg_painting_table.progress"));
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE)
        {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    protected void inventoryChanged() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.EASTER_EGG.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.BOILED_EGG.get();
        ItemStack result = new ItemStack(ModItems.EASTER_EGG.get());

        return hasCraftingItem && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }
}
