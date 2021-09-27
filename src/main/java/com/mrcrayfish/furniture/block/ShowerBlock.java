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

public class ShowerBlock extends FurnitureHorizontalBlock
{
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ShowerBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0), Direction.SOUTH));
        final VoxelShape[] FRONT_TOP_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 31.0, 1.0, 1.0, 32.0, 15.0), Direction.SOUTH));
        final VoxelShape[] FRONT_LEFT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 1.0, 0.0, 1.0, 31.0, 1.0), Direction.SOUTH));
        final VoxelShape[] LEFT_GLASS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.5, 1.0, 0.5, 15.5, 31.0, 0.5), Direction.SOUTH));
        final VoxelShape[] LEFT_TOP_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 31.0, 0.0, 16.0, 32.0, 1.0), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(15.0, 1.0, 0.0, 16.0, 31.0, 1.0), Direction.SOUTH));
        final VoxelShape[] BACK_GLASS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(15.5, 1.0, 0.5, 15.5, 31.0, 15.5), Direction.SOUTH));
        final VoxelShape[] BACK_TOP_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 31.0, 1.0, 1.0, 32.0, 15.0), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(15.0, 1.0, 15.0, 16.0, 31.0, 16.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_GLASS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.5, 1.0, 15.5, 15.5, 31.0, 15.5), Direction.SOUTH));
        final VoxelShape[] RIGHT_TOP_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 31.0, 15.0, 16.0, 32.0, 16.0), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 1.0, 15.0, 1.0, 31.0, 16.0), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BASE[direction.getHorizontalIndex()]);
            shapes.add(FRONT_TOP_FRAME[direction.getHorizontalIndex()]);
            shapes.add(FRONT_LEFT_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(LEFT_GLASS[direction.getHorizontalIndex()]);
            shapes.add(LEFT_TOP_FRAME[direction.getHorizontalIndex()]);
            shapes.add(BACK_LEFT_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(BACK_GLASS[direction.getHorizontalIndex()]);
            shapes.add(BACK_TOP_FRAME[direction.getHorizontalIndex()]);
            shapes.add(BACK_RIGHT_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_GLASS[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_TOP_FRAME[direction.getHorizontalIndex()]);
            shapes.add(FRONT_RIGHT_SUPPORT[direction.getHorizontalIndex()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }
}
