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
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class OvenBlock extends FurnitureHorizontalWaterloggedBlock
{
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public OvenBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] TOP_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 13.0, 3.0, 14.0, 16.0, 13.0), Direction.EAST));
        final VoxelShape[] LEFT_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 1.0, 14.0, 16.0, 3.0), Direction.EAST));
        final VoxelShape[] BACK_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.0, 1.0, 3.0, 14.0, 13.0, 13.0), Direction.EAST));
        final VoxelShape[] RIGHT_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 13.0, 14.0, 16.0, 15.0), Direction.EAST));
        final VoxelShape[] BOTTOM_FRAME = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 3.0, 14.0, 3.0, 13.0), Direction.EAST));
        final VoxelShape[] DIAL_1 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 14.0, 2.0, 2.0, 15.0, 3.0), Direction.EAST));
        final VoxelShape[] DIAL_2 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 14.0, 7.0, 2.0, 15.0, 8.0), Direction.EAST));
        final VoxelShape[] DIAL_3 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 14.0, 9.0, 2.0, 15.0, 10.0), Direction.EAST));
        final VoxelShape[] DIAL_4 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 14.0, 11.0, 2.0, 15.0, 12.0), Direction.EAST));
        final VoxelShape[] DIAL_5 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 14.0, 13.0, 2.0, 15.0, 14.0), Direction.EAST));
        final VoxelShape[] DOOR_GLASS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 3.0, 4.0, 2.5, 11.0, 12.0), Direction.EAST));
        final VoxelShape[] DOOR_HANDLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.5, 11.5, 6.0, 1.5, 12.5, 10.0), Direction.EAST));
        final VoxelShape[] DOOR_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 11.0, 2.0, 12.0, 13.0, 14.0), Direction.EAST));
        final VoxelShape[] DOOR_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 3.0, 2.0, 2.0, 11.0, 4.0), Direction.EAST));
        final VoxelShape[] DOOR_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 3.0, 12.0, 2.0, 11.0, 14.0), Direction.EAST));
        final VoxelShape[] DOOR_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.0, 1.0, 2.0, 2.0, 3.0, 14.0), Direction.EAST));
        final VoxelShape[] TRAY_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 4.0, 3.0, 13.0, 5.0, 13.0), Direction.EAST));
        final VoxelShape[] TRAY_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 7.0, 3.0, 13.0, 8.0, 13.0), Direction.EAST));
        final VoxelShape[] BURNER_1 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 15.5, 3.0, 7.0, 16.5, 7.0), Direction.EAST));
        final VoxelShape[] BURNER_2 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 15.5, 9.0, 7.0, 16.5, 13.0), Direction.EAST));
        final VoxelShape[] BURNER_3 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(9.0, 15.5, 9.0, 13.0, 16.5, 13.0), Direction.EAST));
        final VoxelShape[] BURNER_4 = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(9.0, 15.5, 3.0, 13.0, 16.5, 7.0), Direction.EAST));
        final VoxelShape[] BACKING = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 0.0, 0.9, 16.0, 16.0, 15.0), Direction.EAST));
        final VoxelShape[] BACKING_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 16.0, 0.9, 16.0, 18.0, 15.0), Direction.EAST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP_FRAME[direction.getHorizontalIndex()]);
            shapes.add(LEFT_FRAME[direction.getHorizontalIndex()]);
            shapes.add(BACK_FRAME[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_FRAME[direction.getHorizontalIndex()]);
            shapes.add(BOTTOM_FRAME[direction.getHorizontalIndex()]);
            shapes.add(DIAL_1[direction.getHorizontalIndex()]);
            shapes.add(DIAL_2[direction.getHorizontalIndex()]);
            shapes.add(DIAL_3[direction.getHorizontalIndex()]);
            shapes.add(DIAL_4[direction.getHorizontalIndex()]);
            shapes.add(DIAL_5[direction.getHorizontalIndex()]);
            shapes.add(DOOR_GLASS[direction.getHorizontalIndex()]);
            shapes.add(DOOR_HANDLE[direction.getHorizontalIndex()]);
            shapes.add(DOOR_TOP[direction.getHorizontalIndex()]);
            shapes.add(DOOR_LEFT[direction.getHorizontalIndex()]);
            shapes.add(DOOR_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(DOOR_BOTTOM[direction.getHorizontalIndex()]);
            shapes.add(TRAY_BOTTOM[direction.getHorizontalIndex()]);
            shapes.add(TRAY_TOP[direction.getHorizontalIndex()]);
            shapes.add(BURNER_1[direction.getHorizontalIndex()]);
            shapes.add(BURNER_2[direction.getHorizontalIndex()]);
            shapes.add(BURNER_3[direction.getHorizontalIndex()]);
            shapes.add(BURNER_4[direction.getHorizontalIndex()]);
            shapes.add(BACKING[direction.getHorizontalIndex()]);
            shapes.add(BACKING_TOP[direction.getHorizontalIndex()]);
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
