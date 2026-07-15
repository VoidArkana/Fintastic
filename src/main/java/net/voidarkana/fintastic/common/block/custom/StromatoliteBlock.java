package net.voidarkana.fintastic.common.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class StromatoliteBlock extends BaseCoralPlantTypeBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<StromatoliteBlock> CODEC = simpleCodec(StromatoliteBlock::new);

    protected static final VoxelShape SHAPE = Shapes.or(Block.box(2, 10, 2, 14, 14, 14),
            Block.box(3, 0, 3, 13, 10, 13));

    public StromatoliteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected @NotNull MapCodec<? extends StromatoliteBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
        return false;
    }
}
