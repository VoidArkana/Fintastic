package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LotusPlantBlock extends DoublePlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 1, 5);
    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 0, 4);

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public LotusPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOWERS, 0).setValue(AMOUNT, 1).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, Boolean.FALSE).setValue(FACING, Direction.NORTH));
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return this.mayPlaceOnWater(state, level, pos) || this.mayPlaceOnLand(state, level, pos);
    }

    protected boolean mayPlaceOnLand(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.is(BlockTags.DIRT) || state.is(BlockTags.SAND)){
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = level.getBlockState(pos.relative(direction));
                FluidState fluidstate = level.getFluidState(pos.relative(direction));

                if (state.canBeHydrated(level, pos, fluidstate, pos.relative(direction))
                        || blockstate1.is(Blocks.FROSTED_ICE)) {
                    return level.getFluidState(pos.above()).is(Fluids.EMPTY);
                }
            }
        }
        return false;
    }

    protected boolean mayPlaceOnWater(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK)
                && level.getBlockState(pos.above(2)).getFluidState().is(Fluids.EMPTY)
                && level.getBlockState(pos.above()).getFluidState().is(Fluids.WATER);
    }

    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState blockstate = super.getStateForPlacement(context);
        if (blockstate == null)
            return null;

        return copyWaterloggedFrom(context.getLevel(), context.getClickedPos(), blockstate.setValue(FACING, context.getHorizontalDirection().getOpposite()));
    }

    public boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return super.canSurvive(state, level, pos);
        } else {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);

            return this.mayPlaceOn(blockstate, level, blockpos);
        }
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
            return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? state.setValue(FACING, facingState.getValue(FACING))
                    .setValue(AMOUNT, facingState.getValue(AMOUNT))
                    .setValue(FLOWERS, facingState.getValue(FLOWERS)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext useContext) {
        return false;
    }

    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide()) {
            BlockPos blockpos = pos.above();
            BlockState blockstate = DoublePlantBlock.copyWaterloggedFrom(level, blockpos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, state.getValue(FACING)));
            level.setBlock(blockpos, blockstate, 3);
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (stack.is(Items.BONE_MEAL)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else if (state.getValue(AMOUNT)<5 && stack.is(FintyBlocks.LOTUS.get().asItem())) {
            if (level instanceof ServerLevel serverLevel){
                BlockPos blockpos = pos.below();
                if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pos.above();
                }
                if (!player.isCreative())
                    stack.shrink(1);
                BlockState blockState = level.getBlockState(blockpos);

                serverLevel.setBlock(pos, state.setValue(AMOUNT, state.getValue(AMOUNT)+1), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(AMOUNT, blockState.getValue(AMOUNT)+1), 3);
            }
            level.playSound(null, pos, this.soundType.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else if (state.getValue(FLOWERS)<this.getMaxFlowers(state) && stack.is(FintyBlocks.LOTUS_FLOWER.get().asItem())) {
            if (level instanceof ServerLevel serverLevel){
                BlockPos blockpos = pos.below();
                if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pos.above();
                }
                if (!player.isCreative())
                    stack.shrink(1);
                BlockState blockState = level.getBlockState(blockpos);

                serverLevel.setBlock(pos, state.setValue(FLOWERS, Math.min(this.getMaxFlowers(state), state.getValue(FLOWERS)+1)), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(FLOWERS, Math.min(this.getMaxFlowers(blockState), blockState.getValue(FLOWERS)+1)), 3);
            }
            level.playSound(null, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else if (state.getValue(FLOWERS)>0 && stack.is(Items.SHEARS)) {
            if (level instanceof ServerLevel serverLevel){
                BlockPos blockpos = pos.below();
                if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pos.above();
                }
                BlockState blockState = level.getBlockState(blockpos);

                serverLevel.setBlock(pos, state.setValue(FLOWERS, state.getValue(FLOWERS)-1), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(FLOWERS, blockState.getValue(FLOWERS)-1), 3);
            }
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }else if (state.getValue(AMOUNT)>1 && stack.is(Items.SHEARS)) {
            if (level instanceof ServerLevel serverLevel){
                BlockPos blockpos = pos.below();
                if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pos.above();
                }
                BlockState blockState = level.getBlockState(blockpos);

                serverLevel.setBlock(pos, state.setValue(AMOUNT, state.getValue(AMOUNT)-1), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(AMOUNT, blockState.getValue(AMOUNT)-1), 3);
            }
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_PAD.get()));

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else if (state.getValue(HALF) == DoubleBlockHalf.LOWER && stack.is(ItemTags.SHOVELS)){
            for (int i = state.getValue(AMOUNT); i > 0; i--){
                popResource(level, pos, new ItemStack(FintyItems.LOTUS_ROOT.get()));
            }
            level.destroyBlock(pos,false);
            level.destroyBlock(pos.above(),false);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }else {
            return super.useItemOn(stack, state, level, pos, player, hand, hit);
        }
    }

    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(AMOUNT)<5 || state.getValue(FLOWERS)<this.getMaxFlowers(state)){
            BlockPos blockpos = pos.below();
            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                blockpos = pos.above();
            }
            BlockState blockState = level.getBlockState(blockpos);

            if (state.getValue(FLOWERS)<this.getMaxFlowers(state) && random.nextInt(3)==0){
                level.setBlock(pos, state.setValue(FLOWERS, Math.min(this.getMaxFlowers(state), state.getValue(FLOWERS)+1)), 3);
                level.setBlock(blockpos, blockState.setValue(FLOWERS, Math.min(this.getMaxFlowers(state), state.getValue(FLOWERS)+1)), 3);
            }else{
                level.setBlock(pos, state.setValue(AMOUNT, Math.min(5, state.getValue(AMOUNT)+1)), 3);
                level.setBlock(blockpos, blockState.setValue(AMOUNT, Math.min(5, state.getValue(AMOUNT)+1)), 3);
            }
        }else {
            if (random.nextInt(3)==0){
                popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));
            }else {
                popResource(level, pos, new ItemStack(this.asItem()));
            }
        }
    }

    int getMaxFlowers(BlockState state){
        return switch (state.getValue(AMOUNT)){
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 4;
            default -> 0;
        };
    }

    public static int getMaxFlowers(int amount){
        return switch (amount){
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 4;
            default -> 0;
        };
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED, FACING, AMOUNT, FLOWERS);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        for (int i = state.getValue(FLOWERS); i > 0; i--){
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
