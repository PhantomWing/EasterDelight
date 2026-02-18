package com.phantomwing.eastersdelight.block.entity;

import com.phantomwing.eastersdelight.block.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DyedEggBlockEntity extends BlockEntity {
    public DyedEggBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.DYED_EGG, pos, blockState);
    }
}
