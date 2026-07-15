package net.voidarkana.fintastic.common.block.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.voidarkana.fintastic.common.entity.FintyEntities;
import net.voidarkana.fintastic.common.entity.custom.DwarfFrog;
import org.jetbrains.annotations.NotNull;

public class DwarfFrogspawnBlock extends Block{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
    private static final int minHatchTickDelay = 3600;
    private static final int maxHatchTickDelay = 12000;

    public DwarfFrogspawnBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        return mayPlaceOn(level, pos.below());
    }

    public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, getFrogspawnHatchDelay(level.getRandom()));
    }

    private static int getFrogspawnHatchDelay(RandomSource random) {
        return random.nextInt(minHatchTickDelay, maxHatchTickDelay);
    }

    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        return !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            this.destroyBlock(level, pos);
        } else {
            this.hatchFrogspawn(level, pos, random);
        }
    }

    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Entity entity) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.destroyBlock(level, pos);
        }

    }

    private static boolean mayPlaceOn(BlockGetter level, BlockPos pos) {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState fluidstate1 = level.getFluidState(pos.above());
        return fluidstate.getType() == Fluids.WATER && fluidstate1.getType() == Fluids.EMPTY;
    }

    private void hatchFrogspawn(ServerLevel level, BlockPos pos, RandomSource random) {
        this.destroyBlock(level, pos);
        level.playSound(null, pos, SoundEvents.FROGSPAWN_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.spawnTadpoles(level, pos, random);
    }

    private void destroyBlock(Level level, BlockPos pos) {
        level.destroyBlock(pos, false);
    }

    private void spawnTadpoles(ServerLevel level, BlockPos pos, RandomSource random) {
        int i = random.nextInt(2, 6);

        for(int j = 1; j <= i; ++j) {
            DwarfFrog tadpole = FintyEntities.DWARF_FROG.get().create(level);
            if (tadpole != null) {
                tadpole.setAge(-12000);
                tadpole.setVariant(Util.getRandom(DwarfFrog.FrogVariant.values(), tadpole.getRandom()).getJoinedVariant());
                tadpole.setFromBucket(true);
                double d0 = (double)pos.getX() + this.getRandomTadpolePositionOffset(random);
                double d1 = (double)pos.getZ() + this.getRandomTadpolePositionOffset(random);
                int k = random.nextInt(1, 361);
                tadpole.moveTo(d0, (double)pos.getY() - 0.5D, d1, (float)k, 0.0F);
                level.addFreshEntity(tadpole);
            }
        }

    }

    private double getRandomTadpolePositionOffset(RandomSource random) {
        double d0 = Tadpole.HITBOX_WIDTH / 2.0F;
        return Mth.clamp(random.nextDouble(), d0, 1.0D - d0);
    }
}
