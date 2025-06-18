package net.voidarkana.fintastic.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.voidarkana.fintastic.common.block.custom.AlgaeCarpetBlock;
import net.voidarkana.fintastic.common.block.custom.HornwortBlock;
import net.voidarkana.fintastic.util.YAFMTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin extends Item{

    public BoneMealItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(
            method = {"growWaterPlant"},
            cancellable = true,
            at = @At(value = "HEAD")
    )
    private static void growWaterPlant(ItemStack pStack, Level pLevel, BlockPos pPos, @Nullable Direction pClickedSide, CallbackInfoReturnable<Boolean> cir) {
        if (pLevel.getBlockState(pPos).is(Blocks.WATER) && pLevel.getFluidState(pPos).getAmount() == 8) {
            if (pLevel instanceof ServerLevel) {

                BlockPos blockpos = pPos;
                Holder<Biome> holder = pLevel.getBiome(blockpos);

                if (!holder.is(YAFMTags.Biomes.FRESHWATER_PLANT_BIOME_BLACKLIST)){

                    RandomSource randomsource = pLevel.getRandom();

                    label78:
                    for(int i = 0; i < 196; ++i) {
                        BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                        for(int j = 0; j < i / 16; ++j) {
                            blockpos = blockpos.offset(randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1);
                            if (pLevel.getBlockState(blockpos).isCollisionShapeFullBlock(pLevel, blockpos)) {
                                continue label78;
                            }
                        }

                        if (randomsource.nextInt(3) == 0) {
                            blockstate = BuiltInRegistries.BLOCK.getTag(YAFMTags.Blocks.FRESHWATER_PLANTS).flatMap((holders) -> {
                                return holders.getRandomElement(pLevel.random);
                            }).map((blockHolder) -> {
                                return blockHolder.get() instanceof HornwortBlock ?
                                        blockHolder.value().defaultBlockState()
                                                .setValue(HornwortBlock.FACING, randomsource.nextInt(4) == 0 ? Direction.NORTH : randomsource.nextInt(3) == 0 ? Direction.WEST : randomsource.nextBoolean() ? Direction.EAST : Direction.SOUTH)
                                                .setValue(HornwortBlock.AMOUNT, randomsource.nextInt(1, 5))
                                        : blockHolder.value().defaultBlockState();
                            }).orElse(blockstate);
                        }

                        if (blockstate.canSurvive(pLevel, blockpos)) {
                            BlockState blockstate1 = pLevel.getBlockState(blockpos);
                            if (blockstate1.is(Blocks.WATER) && pLevel.getFluidState(blockpos).getAmount() == 8) {
                                pLevel.setBlock(blockpos, blockstate, 3);
                            } else if (blockstate1.is(Blocks.SEAGRASS) && randomsource.nextInt(10) == 0) {
                                ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)pLevel, randomsource, blockpos, blockstate1);
                            }
                        }
                    }

                    pStack.shrink(1);
                    cir.setReturnValue(true);
                }
            }
        }

    }

}
