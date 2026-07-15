package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;
import net.voidarkana.fintastic.common.block.FintyBlocks;
import net.voidarkana.fintastic.common.worldgen.FintyConfiguredFeatures;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AlgaeLiveRockBlock extends CoralBlock implements BonemealableBlock {

    public AlgaeLiveRockBlock(Block deadBlock, Properties properties) {
        super(deadBlock, properties);
    }

    private static boolean canHaveAlgae(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = reader.getBlockState(blockpos);
        int i = LightEngine.getLightBlockInto(reader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(reader, blockpos));
        return i >= reader.getMaxLightLevel();
    }

    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (canHaveAlgae(state, level, pos)) {
            level.setBlockAndUpdate(pos, level.getBlockState(pos).is(FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get())
                    ? FintyBlocks.LIVE_ROCK.get().defaultBlockState() : FintyBlocks.POROUS_LIVE_ROCK.get().defaultBlockState() );
        }
        super.tick(state, level, pos, random);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (canHaveAlgae(state, level, currentPos)) {
            level.scheduleTick(currentPos, this, 60 + level.getRandom().nextInt(40));
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (canHaveAlgae(context.getLevel().getBlockState(context.getClickedPos()),
                context.getLevel(), context.getClickedPos())) {
            context.getLevel().scheduleTick(context.getClickedPos(), this, 60 + context.getLevel().getRandom().nextInt(40));
        }

        return this.defaultBlockState();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
        return level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockState blockstate = level.getBlockState(pos);
        BlockPos blockpos = pos.above();
        ChunkGenerator chunkgenerator = level.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockstate.is(FintyBlocks.RED_ALGAE_LIVE_ROCK.get())) {
            this.place(registry, FintyConfiguredFeatures.RED_ALGAE_VEGETATION_BONEMEAL, level, chunkgenerator, random, blockpos);

        } else if (blockstate.is(FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get())) {
            this.place(registry, FintyConfiguredFeatures.GREEN_ALGAE_VEGETATION_BONEMEAL, level, chunkgenerator, random, blockpos);
        }
    }

    private void place(Registry<ConfiguredFeature<?, ?>> featureRegistry, ResourceKey<ConfiguredFeature<?, ?>> featureKey,
                       ServerLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        featureRegistry.getHolder(featureKey).ifPresent((holder) -> holder.value().place(level, chunkGenerator, random, pos));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (stack.is(Items.SHEARS)) {
            if(state.is(FintyBlocks.RED_ALGAE_LIVE_ROCK.get())){

                if (!player.isCreative() && player instanceof ServerPlayer serverPlayer){
                    stack.hurtAndBreak(1, serverPlayer, LivingEntity.getSlotForHand(hand));
                }

                popResource(level, pos, new ItemStack(FintyBlocks.RED_ALGAE_CARPET.get(), 1));

                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

                level.setBlockAndUpdate(pos, FintyBlocks.POROUS_LIVE_ROCK.get().defaultBlockState());

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }

            if(state.is(FintyBlocks.GREEN_ALGAE_LIVE_ROCK.get())){

                if (!player.isCreative() && player instanceof ServerPlayer serverPlayer){
                    stack.hurtAndBreak(1, serverPlayer, LivingEntity.getSlotForHand(hand));
                }

                popResource(level, pos, new ItemStack(FintyBlocks.GREEN_ALGAE_CARPET.get(), 1));

                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

                level.setBlockAndUpdate(pos, FintyBlocks.LIVE_ROCK.get().defaultBlockState());

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

}
