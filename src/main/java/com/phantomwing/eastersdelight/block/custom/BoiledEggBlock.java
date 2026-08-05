package com.phantomwing.eastersdelight.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * A placed boiled egg. Carries no color or pattern state, so one class serves every shell color and
 * the block simply drops itself.
 */
public class BoiledEggBlock extends AbstractEggBlock {
    public static final MapCodec<BoiledEggBlock> CODEC = simpleCodec(BoiledEggBlock::new);

    public @NotNull MapCodec<BoiledEggBlock> codec() {
        return CODEC;
    }

    public BoiledEggBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NotNull ItemStack getEggStack(@NotNull BlockState state) {
        return new ItemStack(this);
    }
}
