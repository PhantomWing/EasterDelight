package com.phantomwing.eastersdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.phantomwing.eastersdelight.block.entity.DyedEggBlockEntity;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DyedEggBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DyeColor> BASE_COLOR = EnumProperty.create("base_color", DyeColor.class);
    public static final BooleanProperty PATTERNED = BooleanProperty.create("patterned");
    public static final EnumProperty<EggPattern> EGG_PATTERN = EnumProperty.create("egg_pattern", EggPattern.class);
    public static final EnumProperty<DyeColor> PATTERN_COLOR = EnumProperty.create("pattern_color", DyeColor.class);

    public static final MapCodec<DyedEggBlock> CODEC = simpleCodec(DyedEggBlock::new);

    protected static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 5, 10);

    public @NotNull MapCodec<DyedEggBlock> codec() {
        return CODEC;
    }

    public DyedEggBlock(Properties properties) {
        super(properties);

        // Set default state properties.
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BASE_COLOR, DyeColor.WHITE)
                .setValue(PATTERNED, false)
                .setValue(EGG_PATTERN, EggPattern.STRIPES)
                .setValue(PATTERN_COLOR, DyeColor.WHITE)
        );
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DyedEggBlockEntity(pos, state);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(BASE_COLOR);
        builder.add(PATTERNED);
        builder.add(EGG_PATTERN);
        builder.add(PATTERN_COLOR);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        ItemStack item = super.getCloneItemStack(level, pos, state);

        item.set(DataComponents.BASE_COLOR, state.getValue(BASE_COLOR));

        if (state.getValue(PATTERNED)) {
            item.set(ModDataComponents.EGG_PATTERN, state.getValue(EGG_PATTERN));
            item.set(ModDataComponents.PATTERN_COLOR, state.getValue(PATTERN_COLOR));
        }

        return item;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack item = context.getItemInHand();

        BlockState state = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(BASE_COLOR, item.get(DataComponents.BASE_COLOR));

        EggPattern pattern = item.get(ModDataComponents.EGG_PATTERN);
        if (pattern != null) {
            state = state.setValue(PATTERNED, true)
                    .setValue(EGG_PATTERN, item.get(ModDataComponents.EGG_PATTERN))
                    .setValue(PATTERN_COLOR, item.get(ModDataComponents.PATTERN_COLOR));
        } else {
            state = state.setValue(PATTERNED, false);
        }

        return state;
    }
}
