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

public class MicrowaveBlock extends FurnitureHorizontalWaterloggedBlock
{

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public MicrowaveBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] TOP_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4.0, 7.0, 2.0, 13.0, 8.0, 12.0), Direction.EAST));
        final VoxelShape[] LEFT_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4.0, 0.0, 1.0, 13.0, 8.0, 2.0), Direction.EAST));
        final VoxelShape[] BACK_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.0, 0.0, 2.0, 13.0, 8.0, 12.0), Direction.EAST));
        final VoxelShape[] RIGHT_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4.0, 0.0, 12.0, 13.0, 8.0, 15.0), Direction.EAST));
        final VoxelShape[] BOTTOM_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4.0, 0.0, 2.0, 13.0, 1.0, 12.0), Direction.EAST));
        final VoxelShape[] TOP_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 6.0, 2.0, 4.0, 7.0, 11.0), Direction.EAST));
        final VoxelShape[] LEFT_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 2.0, 2.0, 4.0, 6.0, 3.0), Direction.EAST));
        final VoxelShape[] RIGHT_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 2.0, 10.0, 4.0, 6.0, 11.0), Direction.EAST));
        final VoxelShape[] BOTTOM_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 1.0, 2.0, 4.0, 2.0, 11.0), Direction.EAST));
        final VoxelShape[] TOP_COVER = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 7.0, 1.0, 4.0, 8.0, 12.0), Direction.EAST));
        final VoxelShape[] LEFT_COVER = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 1.0, 1.0, 4.0, 7.0, 2.0), Direction.EAST));
        final VoxelShape[] RIGHT_COVER = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 1.0, 11.0, 4.0, 7.0, 12.0), Direction.EAST));
        final VoxelShape[] BOTTOM_COVER = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 0.0, 1.0, 4.0, 1.0, 12.0), Direction.EAST));
        final VoxelShape[] CONTROLS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 0.0, 12.5, 4.0, 8.0, 15.0), Direction.EAST));
        final VoxelShape[] GLASS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.5, 2.0, 3.0, 3.5, 6.0, 10.0), Direction.EAST));
        final VoxelShape[] PLATE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4.25, 1.0, 3.0, 10.0, 2.0, 10.25), Direction.EAST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP_SIDE[direction.getHorizontalIndex()]);
            shapes.add(LEFT_SIDE[direction.getHorizontalIndex()]);
            shapes.add(BACK_SIDE[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_SIDE[direction.getHorizontalIndex()]);
            shapes.add(BOTTOM_SIDE[direction.getHorizontalIndex()]);
            shapes.add(TOP_FRAME[direction.getHorizontalIndex()]);
            shapes.add(LEFT_FRAME[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_FRAME[direction.getHorizontalIndex()]);
            shapes.add(BOTTOM_FRAME[direction.getHorizontalIndex()]);
            shapes.add(TOP_COVER[direction.getHorizontalIndex()]);
            shapes.add(LEFT_COVER[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_COVER[direction.getHorizontalIndex()]);
            shapes.add(BOTTOM_COVER[direction.getHorizontalIndex()]);
            shapes.add(CONTROLS[direction.getHorizontalIndex()]);
            shapes.add(GLASS[direction.getHorizontalIndex()]);
            shapes.add(PLATE[direction.getHorizontalIndex()]);
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
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state);
    }

}
