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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import net.voidarkana.fintastic.common.block.YAFMBlocks;
import net.voidarkana.fintastic.common.worldgen.YAFMConfiguredFeatures;

import javax.annotation.Nullable;

public class AlgaeLiveRockBlock extends CoralBlock implements BonemealableBlock {

    public AlgaeLiveRockBlock(Block pDeadBlock, Properties pProperties) {
        super(pDeadBlock, pProperties);
    }

    private static boolean canHaveAlgae(BlockState pState, LevelReader pReader, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState blockstate = pReader.getBlockState(blockpos);
        int i = LightEngine.getLightBlockInto(pReader, pState, pPos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(pReader, blockpos));
        return i < pReader.getMaxLightLevel();
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!canHaveAlgae(pState, pLevel, pPos)) {
            pLevel.setBlockAndUpdate(pPos, pLevel.getBlockState(pPos).is(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get())
                    ? YAFMBlocks.LIVE_ROCK.get().defaultBlockState() : YAFMBlocks.POROUS_LIVE_ROCK.get().defaultBlockState() );
        }
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!canHaveAlgae(pState, pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 60 + pLevel.getRandom().nextInt(40));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (!canHaveAlgae(pContext.getLevel().getBlockState(pContext.getClickedPos()),
                pContext.getLevel(), pContext.getClickedPos())) {
            pContext.getLevel().scheduleTick(pContext.getClickedPos(), this, 60 + pContext.getLevel().getRandom().nextInt(40));
        }

        return this.defaultBlockState();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        BlockPos blockpos = pPos.above();
        ChunkGenerator chunkgenerator = pLevel.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockstate.is(YAFMBlocks.RED_ALGAE_LIVE_ROCK.get())) {
            this.place(registry, YAFMConfiguredFeatures.RED_ALGAE_VEGETATION_BONEMEAL, pLevel, chunkgenerator, pRandom, blockpos);

        } else if (blockstate.is(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get())) {
            this.place(registry, YAFMConfiguredFeatures.GREEN_ALGAE_VEGETATION_BONEMEAL, pLevel, chunkgenerator, pRandom, blockpos);
        }
    }

    private void place(Registry<ConfiguredFeature<?, ?>> pFeatureRegistry, ResourceKey<ConfiguredFeature<?, ?>> pFeatureKey,
                       ServerLevel pLevel, ChunkGenerator pChunkGenerator, RandomSource pRandom, BlockPos pPos) {
        pFeatureRegistry.getHolder(pFeatureKey).ifPresent((holder) -> {
            holder.value().place(pLevel, pChunkGenerator, pRandom, pPos);
        });
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(Items.SHEARS)) {
            if(pState.is(YAFMBlocks.RED_ALGAE_LIVE_ROCK.get())){

                if (!pPlayer.isCreative() && pPlayer instanceof ServerPlayer player){
                    stack.hurt(1, pPlayer.getRandom(), player);
                }

                popResource(pLevel, pPos, new ItemStack(YAFMBlocks.RED_ALGAE_CARPET.get(), 1));

                pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

                pLevel.setBlockAndUpdate(pPos, YAFMBlocks.POROUS_LIVE_ROCK.get().defaultBlockState());

                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }

            if(pState.is(YAFMBlocks.GREEN_ALGAE_LIVE_ROCK.get())){

                if (!pPlayer.isCreative() && pPlayer instanceof ServerPlayer player){
                    stack.hurt(1, pPlayer.getRandom(), player);
                }

                popResource(pLevel, pPos, new ItemStack(YAFMBlocks.GREEN_ALGAE_CARPET.get(), 1));

                pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);

                pLevel.setBlockAndUpdate(pPos, YAFMBlocks.LIVE_ROCK.get().defaultBlockState());

                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

}
