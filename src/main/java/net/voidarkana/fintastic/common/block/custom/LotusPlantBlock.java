package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.item.FintyItems;
import net.voidarkana.fintastic.util.FintyTags;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;

public class LotusPlantBlock extends DoublePlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 1, 5);
    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 0, 4);

    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public LotusPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOWERS, 0).setValue(AMOUNT, 1).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return this.mayPlaceOnWater(pState, pLevel, pPos) || this.mayPlaceOnLand(pState, pLevel, pPos);
    }

    protected boolean mayPlaceOnLand(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BlockTags.DIRT) || pState.is(BlockTags.SAND)){
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = pLevel.getBlockState(pPos.relative(direction));
                FluidState fluidstate = pLevel.getFluidState(pPos.relative(direction));

                if (pState.canBeHydrated(pLevel, pPos, fluidstate, pPos.relative(direction))
                        || blockstate1.is(Blocks.FROSTED_ICE)) {
                    return pLevel.getFluidState(pPos.above()).is(Fluids.EMPTY);
                }
            }
        }
        return false;
    }

    protected boolean mayPlaceOnWater(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP) && !pState.is(Blocks.MAGMA_BLOCK)
                && pLevel.getBlockState(pPos.above(2)).getFluidState().is(Fluids.EMPTY)
                && pLevel.getBlockState(pPos.above()).getFluidState().is(Fluids.WATER);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = super.getStateForPlacement(pContext);
        if (blockstate == null)
            return null;

        return copyWaterloggedFrom(pContext.getLevel(), pContext.getClickedPos(), blockstate.setValue(FACING, pContext.getHorizontalDirection().getOpposite()));
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return super.canSurvive(pState, pLevel, pPos);
        } else {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);

            return this.mayPlaceOn(blockstate, pLevel, blockpos);
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState.setValue(FACING, pFacingState.getValue(FACING))
                    .setValue(AMOUNT, pFacingState.getValue(AMOUNT))
                    .setValue(FLOWERS, pFacingState.getValue(FLOWERS)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if ((pState.getValue(HALF) == DoubleBlockHalf.LOWER && pLevel.getBlockState(pPos.below()).is(FintyTags.Blocks.LOTUS_GROWABLE))
        || (pState.getValue(HALF) == DoubleBlockHalf.UPPER && pLevel.getBlockState(pPos.below(2)).is(FintyTags.Blocks.LOTUS_GROWABLE))){

            int i = pState.getValue(FLOWERS);
            int j = pState.getValue(AMOUNT);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, true) && pRandom.nextInt(3)>0) {

                if (j<5 || i<this.getMaxFlowers(pState)) {
                    this.performBonemeal(pLevel, pRandom, pPos, pState);
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        if (pState.getValue(HALF) == DoubleBlockHalf.UPPER){
            return false;
        }else{
            return true;
        }
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return false;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = DoublePlantBlock.copyWaterloggedFrom(pLevel, blockpos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, pState.getValue(FACING)));
            pLevel.setBlock(blockpos, blockstate, 3);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (pState.getValue(AMOUNT)<5 && pPlayer.getItemInHand(pHand).is(FintyBlocks.LOTUS.get().asItem())) {
            if (pLevel instanceof ServerLevel serverLevel){
                BlockPos blockpos = pPos.below();
                if (pState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pPos.above();
                }
                if (!pPlayer.isCreative())
                    pPlayer.getItemInHand(pHand).shrink(1);
                BlockState blockState = pLevel.getBlockState(blockpos);

                serverLevel.setBlock(pPos, pState.setValue(AMOUNT, pState.getValue(AMOUNT)+1), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(AMOUNT, blockState.getValue(AMOUNT)+1), 3);
            }
            pLevel.playSound(null, pPos, this.soundType.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else if (pState.getValue(FLOWERS)<this.getMaxFlowers(pState) && pPlayer.getItemInHand(pHand).is(FintyBlocks.LOTUS_FLOWER.get().asItem())) {
            if (pLevel instanceof ServerLevel serverLevel){
                BlockPos blockpos = pPos.below();
                if (pState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pPos.above();
                }
                if (!pPlayer.isCreative())
                    pPlayer.getItemInHand(pHand).shrink(1);
                BlockState blockState = pLevel.getBlockState(blockpos);

                serverLevel.setBlock(pPos, pState.setValue(FLOWERS, Math.min(this.getMaxFlowers(pState), pState.getValue(FLOWERS)+1)), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(FLOWERS, Math.min(this.getMaxFlowers(blockState), blockState.getValue(FLOWERS)+1)), 3);
            }
            pLevel.playSound(null, pPos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else if (pState.getValue(FLOWERS)>0 && pPlayer.getItemInHand(pHand).is(Items.SHEARS)) {
            if (pLevel instanceof ServerLevel serverLevel){
                BlockPos blockpos = pPos.below();
                if (pState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pPos.above();
                }
                BlockState blockState = pLevel.getBlockState(blockpos);

                serverLevel.setBlock(pPos, pState.setValue(FLOWERS, pState.getValue(FLOWERS)-1), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(FLOWERS, blockState.getValue(FLOWERS)-1), 3);
            }
            pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }else if (pState.getValue(AMOUNT)>1 && pPlayer.getItemInHand(pHand).is(Items.SHEARS)) {
            if (pLevel instanceof ServerLevel serverLevel){
                BlockPos blockpos = pPos.below();
                if (pState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    blockpos = pPos.above();
                }
                BlockState blockState = pLevel.getBlockState(blockpos);

                serverLevel.setBlock(pPos, pState.setValue(AMOUNT, pState.getValue(AMOUNT)-1), 3);
                serverLevel.setBlock(blockpos, blockState.setValue(AMOUNT, blockState.getValue(AMOUNT)-1), 3);
            }
            pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            popResource(pLevel, pPos, new ItemStack(FintyBlocks.LOTUS_PAD.get()));

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else if (pState.getValue(HALF) == DoubleBlockHalf.LOWER && pPlayer.getItemInHand(pHand).is(ItemTags.SHOVELS)){
            for (int i = pState.getValue(AMOUNT); i > 0; i--){
                popResource(pLevel, pPos, new ItemStack(FintyItems.LOTUS_ROOT.get()));
            }
            pLevel.destroyBlock(pPos,false);
            pLevel.destroyBlock(pPos.above(),false);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    public boolean isValidBonemealTarget(LevelReader p_255772_, BlockPos p_154595_, BlockState p_154596_, boolean p_154597_) {
        return true;
    }

    public boolean isBonemealSuccess(Level p_222438_, RandomSource p_222439_, BlockPos p_222440_, BlockState p_222441_) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        if (pState.getValue(AMOUNT)<5 || pState.getValue(FLOWERS)<this.getMaxFlowers(pState)){
            BlockPos blockpos = pPos.below();
            if (pState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                blockpos = pPos.above();
            }
            BlockState blockState = pLevel.getBlockState(blockpos);

            if (pState.getValue(FLOWERS)<this.getMaxFlowers(pState) && pRandom.nextInt(3)==0){
                pLevel.setBlock(pPos, pState.setValue(FLOWERS, Math.min(this.getMaxFlowers(pState), pState.getValue(FLOWERS)+1)), 3);
                pLevel.setBlock(blockpos, blockState.setValue(FLOWERS, Math.min(this.getMaxFlowers(pState), pState.getValue(FLOWERS)+1)), 3);
            }else{
                pLevel.setBlock(pPos, pState.setValue(AMOUNT, Math.min(5, pState.getValue(AMOUNT)+1)), 3);
                pLevel.setBlock(blockpos, blockState.setValue(AMOUNT, Math.min(5, pState.getValue(AMOUNT)+1)), 3);
            }
        }else {
            if (pRandom.nextInt(3)==0){
                popResource(pLevel, pPos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));
            }else {
                popResource(pLevel, pPos, new ItemStack(this.asItem()));
            }
        }
    }

    int getMaxFlowers(BlockState pState){
        return switch (pState.getValue(AMOUNT)){
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

    public BlockState rotate(BlockState p_154622_, Rotation p_154623_) {
        return p_154622_.setValue(FACING, p_154623_.rotate(p_154622_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_154619_, Mirror p_154620_) {
        return p_154619_.rotate(p_154620_.getRotation(p_154619_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, WATERLOGGED, FACING, AMOUNT, FLOWERS);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable BlockEntity blockEntity, ItemStack tool) {
        for (int i = state.getValue(FLOWERS); i > 0; i--){
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
