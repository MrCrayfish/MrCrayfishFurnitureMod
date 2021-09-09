package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.ArrayList;
import java.util.List;

public class ShowerHeadBlock extends FurnitureHorizontalWaterloggedBlock
{
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ShowerHeadBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] TOP_PIPE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(7.2, 5.6, 7.2, 16.0, 7.2, 8.8), Direction.EAST));
        final VoxelShape[] CENTER_PIPE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(7.2, 4.0, 7.2, 8.8, 5.6, 8.8), Direction.EAST));
        final VoxelShape[] MAIN_WATER_OUTPUT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(5.6, 2.4, 5.6, 10.4, 4.0, 10.4), Direction.EAST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state: states) {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP_PIPE[direction.getHorizontalIndex()]);
            shapes.add(CENTER_PIPE[direction.getHorizontalIndex()]);
            shapes.add(MAIN_WATER_OUTPUT[direction.getHorizontalIndex()]);
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state);
    }


}
