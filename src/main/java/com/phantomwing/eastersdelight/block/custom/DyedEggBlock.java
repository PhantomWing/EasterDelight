package com.phantomwing.eastersdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class DyedEggBlock extends AbstractEggBlock {
    public static final EnumProperty<@NotNull DyeColor> BASE_COLOR = EnumProperty.create("base_color", DyeColor.class);
    public static final BooleanProperty PATTERNED = BooleanProperty.create("patterned");
    public static final EnumProperty<@NotNull EggPattern> EGG_PATTERN = EnumProperty.create("egg_pattern", EggPattern.class);
    public static final EnumProperty<@NotNull DyeColor> PATTERN_COLOR = EnumProperty.create("pattern_color", DyeColor.class);

    public static final MapCodec<DyedEggBlock> CODEC = simpleCodec(DyedEggBlock::new);

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
        builder.add(FACING, BASE_COLOR, PATTERNED, EGG_PATTERN, PATTERN_COLOR);
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);

        drops.add(getEggStack(state));

        return drops;
    }

    @Override
    protected @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean includeData) {
        return getEggStack(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack item = context.getItemInHand();

        BlockState state = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection());

        if (item.has(DataComponents.BASE_COLOR)) {
            state = state.setValue(BASE_COLOR, Objects.requireNonNull(item.get(DataComponents.BASE_COLOR)));
        }

        boolean hasPattern = item.has(ModDataComponents.EGG_PATTERN);
        if (hasPattern) {
            state = state.setValue(PATTERNED, true).setValue(EGG_PATTERN, item.get(ModDataComponents.EGG_PATTERN));

            if (item.has(ModDataComponents.PATTERN_COLOR)) {
                state = state.setValue(PATTERN_COLOR, item.get(ModDataComponents.PATTERN_COLOR));
            }
        } else {
            state = state.setValue(PATTERNED, false);
        }

        return state;
    }

    @Override
    protected @NotNull ItemStack getEggStack(@NotNull BlockState state) {
        ItemStack eggItem = new ItemStack(ModItems.DYED_EGG);
        eggItem.set(DataComponents.BASE_COLOR, state.getValue(BASE_COLOR));

        if (state.getValue(PATTERNED)) {
            eggItem.set(ModDataComponents.EGG_PATTERN, state.getValue(EGG_PATTERN));
            eggItem.set(ModDataComponents.PATTERN_COLOR, state.getValue(PATTERN_COLOR));
        }

        return eggItem;
    }
}
