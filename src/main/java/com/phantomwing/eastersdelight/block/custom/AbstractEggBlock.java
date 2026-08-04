package com.phantomwing.eastersdelight.block.custom;

import com.phantomwing.eastersdelight.item.ModItems;
import com.phantomwing.eastersdelight.tags.CommonTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipeInput;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.Optional;

/**
 * Shared behaviour for placed eggs: a small fragile block that cracks when trodden on and can be
 * cut open in place with a knife.
 */
public abstract class AbstractEggBlock extends Block {
    public static final EnumProperty<@NotNull Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 6, 10);

    /** Crushing an egg wastes some of it, so it yields less than cutting one open properly. */
    private static final int MIN_CRUSHED_SLICES = 1;
    private static final int MAX_CRUSHED_SLICES = 2;

    // Resolved lazily: ModRecipeTypes.CUTTING is a supplier Farmer's Delight fills during its own
    // init, so touching it while this class loads would depend on mod init order.
    private static RecipeManager.CachedCheck<CuttingBoardRecipeInput, CuttingBoardRecipe> cuttingCheck;

    private static RecipeManager.CachedCheck<CuttingBoardRecipeInput, CuttingBoardRecipe> cuttingCheck() {
        if (cuttingCheck == null) {
            cuttingCheck = RecipeManager.createCheck(ModRecipeTypes.CUTTING.get());
        }
        return cuttingCheck;
    }

    protected AbstractEggBlock(Properties properties) {
        super(properties);
    }

    /** This egg in item form, carrying whatever state the block holds. */
    protected abstract ItemStack getEggStack(BlockState state);

    /**
     * Cutting a placed egg with a knife runs the same Farmer's Delight cutting recipe the cutting
     * board would, so the yield stays in step with the recipe (and with any datapack that edits it)
     * instead of being duplicated here.
     */
    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!stack.is(CommonTags.TOOLS_KNIFE)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hit);
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        CuttingBoardRecipeInput input = new CuttingBoardRecipeInput(getEggStack(state), stack);
        Optional<RecipeHolder<CuttingBoardRecipe>> match = cuttingCheck().getRecipeFor(input, serverLevel);
        if (match.isEmpty()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hit);
        }

        CuttingBoardRecipe recipe = match.get().value();
        for (ItemStack result : recipe.rollResults(serverLevel.getRandom(), 0)) {
            popResource(level, pos, result);
        }

        SoundEvent sound = recipe.getSoundEvent().orElse(SoundEvents.TURTLE_EGG_BREAK);
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 0.8F, 0.9F + level.getRandom().nextFloat() * 0.2F);

        stack.hurtAndBreak(1, player, hand);
        level.destroyBlock(pos, false);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            this.destroyEgg(level, state, pos, entity, 100);
        }

        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (!(entity instanceof Zombie)) {
            this.destroyEgg(level, state, pos, entity, 3);
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    private void destroyEgg(Level level, BlockState state, BlockPos pos, Entity entity, int chance) {
        if (state.is(this) && level instanceof ServerLevel serverLevel) {
            RandomSource random = level.getRandom();
            if (this.canDestroyEgg(serverLevel, entity) && random.nextInt(chance) == 0) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);

                int slices = random.nextIntBetweenInclusive(MIN_CRUSHED_SLICES, MAX_CRUSHED_SLICES);
                popResource(level, pos, new ItemStack(ModItems.EGG_SLICE, slices));

                level.destroyBlock(pos, false);
            }
        }
    }

    private boolean canDestroyEgg(ServerLevel level, Entity entity) {
        if (!(entity instanceof Turtle) && !(entity instanceof Bat)) {
            if (!(entity instanceof LivingEntity)) {
                return false;
            } else {
                return entity instanceof Player || level.getGameRules().get(GameRules.MOB_GRIEFING);
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
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        BlockPos floorPos = pos.below();
        return canSupportRigidBlock(level, floorPos) || canSupportCenter(level, floorPos, Direction.UP);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighborPos, @NotNull BlockState neighborState, @NotNull RandomSource random) {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}
