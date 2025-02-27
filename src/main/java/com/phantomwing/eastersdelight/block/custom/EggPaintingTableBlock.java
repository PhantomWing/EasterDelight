package com.phantomwing.eastersdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.phantomwing.eastersdelight.block.entity.EggPaintingTableBlockEntity;
import com.phantomwing.eastersdelight.block.entity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EggPaintingTableBlock extends BaseEntityBlock {
    public static final MapCodec<EggPaintingTableBlock> CODEC = simpleCodec(EggPaintingTableBlock::new);

    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);

    public EggPaintingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof EggPaintingTableBlockEntity) {
                ((EggPaintingTableBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof EggPaintingTableBlockEntity eggPaintingTableBlockEntity) {
                player.openMenu(eggPaintingTableBlockEntity, pos);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new EggPaintingTableBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntity) {
        if(level.isClientSide()) {
            return null;
        }

        return createTickerHelper(blockEntity, ModBlockEntityTypes.EGG_PAINTING_TABLE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
