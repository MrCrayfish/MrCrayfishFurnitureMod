package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class TableBlock extends FurnitureBlockWaterlogged
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public TableBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape TABLE_TOP = Block.makeCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        final VoxelShape MIDDLE_POST = Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 14.0, 10.0);
        final VoxelShape END_POST = Block.makeCuboidShape(3.0, 0.0, 6.0, 7.0, 14.0, 10.0);
        final VoxelShape CORNER_POST = Block.makeCuboidShape(3.0, 0.0, 9.0, 7.0, 14.0, 13.0);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean north = state.get(NORTH);
            boolean east = state.get(EAST);
            boolean south = state.get(SOUTH);
            boolean west = state.get(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TABLE_TOP);

            if(!north & !east && !south && !west)
            {
                shapes.add(MIDDLE_POST);
            }
            else if(north & !east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.NORTH));
            }
            else if(!north & east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.EAST));
            }
            else if(!north & !east && south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.SOUTH));
            }
            else if(!north & !east && !south && west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.WEST));
            }
            else if(north && east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.EAST));
            }
            else if(!north && east && south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.SOUTH));
            }
            else if(!north && !east && south && west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.WEST));
            }
            else if(north && !east && !south && west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.NORTH));
            }

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
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        boolean north = world.getBlockState(pos.north()).getBlock() == this;
        boolean east = world.getBlockState(pos.east()).getBlock() == this;
        boolean south = world.getBlockState(pos.south()).getBlock() == this;
        boolean west = world.getBlockState(pos.west()).getBlock() == this;
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }

    //May be able to switch back in a future forge update
    /*@Override
    public BlockState getExtendedState(BlockState state, IBlockReader world, BlockPos pos)
    {
        boolean north = world.getBlockState(pos.north()).getBlock() == this;
        boolean east = world.getBlockState(pos.east()).getBlock() == this;
        boolean south = world.getBlockState(pos.south()).getBlock() == this;
        boolean west = world.getBlockState(pos.west()).getBlock() == this;
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }*/

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }
}