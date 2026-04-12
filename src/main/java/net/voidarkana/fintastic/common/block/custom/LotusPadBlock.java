package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
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
}
