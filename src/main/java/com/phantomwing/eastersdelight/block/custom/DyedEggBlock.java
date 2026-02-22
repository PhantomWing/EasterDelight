package com.phantomwing.eastersdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.phantomwing.eastersdelight.block.ModBlocks;
import com.phantomwing.eastersdelight.component.EggPattern;
import com.phantomwing.eastersdelight.component.ModDataComponents;
import com.phantomwing.eastersdelight.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DyedEggBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DyeColor> BASE_COLOR = EnumProperty.create("base_color", DyeColor.class);
    public static final BooleanProperty PATTERNED = BooleanProperty.create("patterned");
    public static final EnumProperty<EggPattern> EGG_PATTERN = EnumProperty.create("egg_pattern", EggPattern.class);
    public static final EnumProperty<DyeColor> PATTERN_COLOR = EnumProperty.create("pattern_color", DyeColor.class);

    public static final MapCodec<DyedEggBlock> CODEC = simpleCodec(DyedEggBlock::new);

    protected static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 6, 10);

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

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            this.destroyEgg(level, state, pos, entity, 100);
        }

        super.stepOn(level, pos, state, entity);
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(entity instanceof Zombie)) {
            this.destroyEgg(level, state, pos, entity, 3);
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    private void destroyEgg(Level level, BlockState state, BlockPos pos, Entity entity, int chance) {
        if (state.is(ModBlocks.DYED_EGG) && level instanceof ServerLevel serverLevel) {
            if (this.canDestroyEgg(serverLevel, entity) && level.random.nextInt(chance) == 0) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
                level.destroyBlock(pos, false);
            }
        }
    }

    private boolean canDestroyEgg(ServerLevel level, Entity entity) {
        if (!(entity instanceof Turtle) && !(entity instanceof Bat)) {
            if (!(entity instanceof LivingEntity)) {
                return false;
            } else {
                return entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            }
        } else {
            return false;
        }
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
        builder.add(FACING, BASE_COLOR, PATTERNED, EGG_PATTERN, PATTERN_COLOR);
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);

        ItemStack eggItem = getDyedEggStack(state);
        drops.add(eggItem);

        return drops;
    }

    @Override
    protected @NotNull ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return getDyedEggStack(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack item = context.getItemInHand();

        BlockState state = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection());

        if (item.has(DataComponents.BASE_COLOR)) {
            state = state.setValue(BASE_COLOR, item.get(DataComponents.BASE_COLOR));
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
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }

    private ItemStack getDyedEggStack(BlockState state) {
        ItemStack eggItem = new ItemStack(ModItems.DYED_EGG);
        eggItem.set(DataComponents.BASE_COLOR, state.getValue(BASE_COLOR));

        if (state.getValue(PATTERNED)) {
            eggItem.set(ModDataComponents.EGG_PATTERN, state.getValue(EGG_PATTERN));
            eggItem.set(ModDataComponents.PATTERN_COLOR, state.getValue(PATTERN_COLOR));
        }

        return eggItem;
    }
}
