package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
import net.neoforged.neoforge.common.Tags;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.sound.FintySounds;
import net.voidarkana.fintastic.util.FintyCommonConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LotusPadBlock extends BushBlock {
    public static final MapCodec<LotusPadBlock> CODEC = simpleCodec(LotusPadBlock::new);

    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    public static final BooleanProperty FLOWER = BooleanProperty.create("flower");

    public LotusPadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOWER, false));
    }

    @Override
    protected @NotNull MapCodec<? extends LotusPadBlock> codec() {
        return CODEC;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AABB;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.1f;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!state.getValue(FLOWER) && stack.is(FintyBlocks.LOTUS_FLOWER.get().asItem())) {
            if (level instanceof ServerLevel serverLevel){
                serverLevel.setBlock(pos, state.setValue(FLOWER, true), 3);
            }
            if (!player.isCreative())
                stack.shrink(1);
            level.playSound(null, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }else if (state.getValue(FLOWER) && stack.is(Tags.Items.TOOLS_SHEAR)){
            if (level instanceof ServerLevel serverLevel){
                serverLevel.setBlock(pos, state.setValue(FLOWER, false), 3);
            }
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));

            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOWER);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        if (state.getValue(FLOWER)){
            popResource(level, pos, new ItemStack(FintyBlocks.LOTUS_FLOWER.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    protected boolean mayPlaceOn(@NotNull BlockState state, BlockGetter level, @NotNull BlockPos pos) {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState fluidstate1 = level.getFluidState(pos.above());
        return ((fluidstate.getType() == Fluids.WATER) || state.isFaceSturdy(level, pos, Direction.UP)) && fluidstate1.isEmpty();
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, RandomSource random) {

        if (random.nextInt(1000) < FintyCommonConfig.LOTUS_PAD_FREQUENCY.get()){
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = level.getBlockState(pos.relative(direction));

                if (blockstate1.is(FintyBlocks.LOTUS_PAD.get()) || blockstate1.is(FintyBlocks.LOTUS_FLOWER.get()) || blockstate1.is(FintyBlocks.DUCKWEED.get())){
                    double d0 = (double)pos.getX() + 0.5D;
                    double d1 = pos.getY();
                    double d2 = (double)pos.getZ() + 0.5D;

                     if (!state.getValue(FLOWER) && level.getBlockState(pos.below()).getFluidState().is(Fluids.WATER) && random.nextInt(4)==0){

                         level.playLocalSound(d0, d1, d2, FintySounds.LOTUS_WATER.get(), SoundSource.BLOCKS, FintyCommonConfig.LOTUS_PAD_LOUDNESS.get(),
                                 (random.nextFloat() - random.nextFloat()) * 0.2F + 0.9F, false);

                    }else if (state.getValue(FLOWER) && random.nextInt(3)>0) {

                        if (blockstate1.is(FintyBlocks.LOTUS_FLOWER.get()) && random.nextInt(level.isDay() ? 2 : 3)>0){
                            level.playLocalSound(d0, d1, d2, !level.isDay() && random.nextBoolean() ? FintySounds.LOTUS_BUG_NIGHT.get() :  FintySounds.LOTUS_BUG.get(), SoundSource.BLOCKS,
                                    FintyCommonConfig.LOTUS_PAD_LOUDNESS.get(), (random.nextFloat() - random.nextFloat()) * 0.2F + 0.9F, false);
                        }else {
                            level.playLocalSound(d0, d1, d2, !level.isDay() && random.nextBoolean() ? FintySounds.LOTUS_FROG_NIGHT.get() : FintySounds.LOTUS_FROG.get(), SoundSource.BLOCKS,
                                    FintyCommonConfig.LOTUS_PAD_LOUDNESS.get(), (random.nextFloat() - random.nextFloat()) * 0.2F + 0.9F, false);
                        }

                    }
                    break;
                }

            }
        }


    }
}
