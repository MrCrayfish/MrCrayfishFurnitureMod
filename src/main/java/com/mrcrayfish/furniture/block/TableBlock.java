package com.mrcrayfish.furniture.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

/**
 * Author: MrCrayfish
 */
public class TableBlock extends FurnitureBlockWaterlogged
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public TableBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
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

    @Override
    public BlockState getExtendedState(BlockState state, IBlockReader world, BlockPos pos)
    {
        boolean north = world.getBlockState(pos.north()).getBlock() == this;
        boolean east = world.getBlockState(pos.east()).getBlock() == this;
        boolean south = world.getBlockState(pos.south()).getBlock() == this;
        boolean west = world.getBlockState(pos.west()).getBlock() == this;
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }

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
