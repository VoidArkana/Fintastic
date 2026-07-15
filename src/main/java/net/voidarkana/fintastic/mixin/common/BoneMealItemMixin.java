package net.voidarkana.fintastic.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.voidarkana.fintastic.common.block.custom.HornwortBlock;
import net.voidarkana.fintastic.util.FintyTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin extends Item{

    public BoneMealItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = {"growWaterPlant"},
            cancellable = true,
            at = @At(value = "HEAD")
    )
    private static void growWaterPlant(ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedSide, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (level instanceof ServerLevel) {

                BlockPos blockpos = pos;
                Holder<Biome> holder = level.getBiome(blockpos);

                if (!holder.is(FintyTags.Biomes.FRESHWATER_PLANT_BIOME_BLACKLIST)){

                    RandomSource randomsource = level.getRandom();

                    label78:
                    for(int i = 0; i < 196; ++i) {
                        BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                        for(int j = 0; j < i / 16; ++j) {
                            blockpos = blockpos.offset(randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1);
                            if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                                continue label78;
                            }
                        }

                        if (randomsource.nextInt(3) == 0) {
                            blockstate = BuiltInRegistries.BLOCK.getTag(FintyTags.Blocks.FRESHWATER_PLANTS).flatMap((holders) -> holders.getRandomElement(level.random)).map((blockHolder) -> blockHolder.value() instanceof HornwortBlock ?
                                    blockHolder.value().defaultBlockState()
                                            .setValue(HornwortBlock.FACING, randomsource.nextInt(4) == 0 ? Direction.NORTH : randomsource.nextInt(3) == 0 ? Direction.WEST : randomsource.nextBoolean() ? Direction.EAST : Direction.SOUTH)
                                            .setValue(HornwortBlock.AMOUNT, randomsource.nextInt(1, 5))
                                    : blockHolder.value().defaultBlockState()).orElse(blockstate);
                        }

                        if (blockstate.canSurvive(level, blockpos)) {
                            BlockState blockstate1 = level.getBlockState(blockpos);
                            if (blockstate1.is(Blocks.WATER) && level.getFluidState(blockpos).getAmount() == 8) {
                                level.setBlock(blockpos, blockstate, 3);
                            } else if (blockstate1.is(Blocks.SEAGRASS) && randomsource.nextInt(10) == 0) {
                                ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)level, randomsource, blockpos, blockstate1);
                            }
                        }
                    }

                    stack.shrink(1);
                    cir.setReturnValue(true);
                }
            }
        }

    }

}
