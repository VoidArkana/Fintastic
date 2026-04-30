package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.sound.FintySounds;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import org.jetbrains.annotations.Nullable;

public class LotusPadBlock extends BushBlock {
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    public static final BooleanProperty FLOWER = BooleanProperty.create("flower");

    public LotusPadBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOWER, false));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.1f;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pState.getValue(FLOWER) && pPlayer.getItemInHand(pHand).is(FintyBlocks.LOTUS_FLOWER.get().asItem())) {
            if (pLevel instanceof ServerLevel serverLevel){
                serverLevel.setBlock(pPos, pState.setValue(FLOWER, true), 3);
            }
            if (!pPlayer.isCreative())
                pPlayer.getItemInHand(pHand).shrink(1);
            pLevel.playSound(null, pPos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }else if (pState.getValue(FLOWER) && pPlayer.getItemInHand(pHand).is(Tags.Items.SHEARS)){
            if (pLevel instanceof ServerLevel serverLevel){
                serverLevel.setBlock(pPos, pState.setValue(FLOWER, false), 3);
            }
            popResource(pLevel, pPos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));

            pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FLOWER);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (state.getValue(FLOWER)){
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        FluidState fluidstate = pLevel.getFluidState(pPos);
        FluidState fluidstate1 = pLevel.getFluidState(pPos.above());
        return ((fluidstate.getType() == Fluids.WATER) || pState.isFaceSturdy(pLevel, pPos, Direction.UP)) && fluidstate1.isEmpty();
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {

        if (pRandom.nextInt(1000) < FintyCommonConfig.LOTUS_PAD_FREQUENCY.get()){
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = pLevel.getBlockState(pPos.relative(direction));

                if (blockstate1.is(FintyBlocks.LOTUS_PAD.get()) || blockstate1.is(FintyBlocks.LOTUS_FLOWER.get()) || blockstate1.is(FintyBlocks.DUCKWEED.get())){
                    double d0 = (double)pPos.getX() + 0.5D;
                    double d1 = pPos.getY();
                    double d2 = (double)pPos.getZ() + 0.5D;

                     if (!pState.getValue(FLOWER) && pLevel.getBlockState(pPos.below()).getFluidState().is(Fluids.WATER) && pRandom.nextInt(4)==0){

                         pLevel.playLocalSound(d0, d1, d2, FintySounds.LOTUS_WATER.get(), SoundSource.BLOCKS, FintyCommonConfig.LOTUS_PAD_LOUDNESS.get(),
                                 (pRandom.nextFloat() - pRandom.nextFloat()) * 0.2F + 0.9F, false);

                    }else if (pState.getValue(FLOWER) && pRandom.nextInt(3)>0) {

                        if (blockstate1.is(FintyBlocks.LOTUS_FLOWER.get()) && pRandom.nextInt(pLevel.isDay() ? 2 : 3)>0){
                            pLevel.playLocalSound(d0, d1, d2, !pLevel.isDay() && pRandom.nextBoolean() ? FintySounds.LOTUS_BUG_NIGHT.get() :  FintySounds.LOTUS_BUG.get(), SoundSource.BLOCKS,
                                    FintyCommonConfig.LOTUS_PAD_LOUDNESS.get(), (pRandom.nextFloat() - pRandom.nextFloat()) * 0.2F + 0.9F, false);
                        }else {
                            pLevel.playLocalSound(d0, d1, d2, !pLevel.isDay() && pRandom.nextBoolean() ? FintySounds.LOTUS_FROG_NIGHT.get() : FintySounds.LOTUS_FROG.get(), SoundSource.BLOCKS,
                                    FintyCommonConfig.LOTUS_PAD_LOUDNESS.get(), (pRandom.nextFloat() - pRandom.nextFloat()) * 0.2F + 0.9F, false);
                        }

                    }
                    break;
                }

            }
        }


    }
}
