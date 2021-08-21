package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModernCoffeeTableBlock extends FurnitureWaterloggedBlock {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty TALL = BooleanProperty.create("tall");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ModernCoffeeTableBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(TALL, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape TOP = Block.makeCuboidShape(0.0, 6.0, 0.0, 16.0, 8.0, 16.0);
        final VoxelShape TALL_TOP = Block.makeCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        final VoxelShape SOUTH_EAST_LEG = Block.makeCuboidShape(1.0, 0.0, 13.5, 2.5, 6.0, 15.0);
        final VoxelShape TALL_SOUTH_EAST_LEG = Block.makeCuboidShape(1.0, 0.0, 13.5, 2.5, 14.0, 15.0);
        final VoxelShape SOUTH_WEST_LEG = Block.makeCuboidShape(13.5, 0.0, 13.5, 15.0, 6.0, 15.0);
        final VoxelShape TALL_SOUTH_WEST_LEG = Block.makeCuboidShape(13.5, 0.0, 13.5, 15.0, 14.0, 15.0);
        final VoxelShape NORTH_EAST_LEG = Block.makeCuboidShape(1.0, 0.0, 1.0, 2.5, 6.0, 2.5);
        final VoxelShape TALL_NORTH_EAST_LEG = Block.makeCuboidShape(1.0, 0.0, 1.0, 2.5, 14.0, 2.5);
        final VoxelShape NORTH_WEST_LEG = Block.makeCuboidShape(13.5, 0.0, 1.0, 15.0, 6.0, 2.5);
        final VoxelShape TALL_NORTH_WEST_LEG = Block.makeCuboidShape(13.5, 0.0, 1.0, 15.0, 14.0, 2.5);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean tall = state.get(TALL);
            boolean north = state.get(NORTH);
            boolean east = state.get(EAST);
            boolean south = state.get(SOUTH);
            boolean west = state.get(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(tall ? TALL_TOP : TOP);

            if (tall)
            {
                if (!north && !west && !east)
                {
                    shapes.add(TALL_NORTH_WEST_LEG);
                    shapes.add(TALL_NORTH_EAST_LEG);
                }
                if (!north && west)
                {
                    shapes.add(TALL_NORTH_WEST_LEG);
                }
                if (!north && east)
                {
                    shapes.add(TALL_NORTH_EAST_LEG);
                }
                if (!south && !west && !east)
                {
                    shapes.add(TALL_SOUTH_WEST_LEG);
                    shapes.add(TALL_SOUTH_EAST_LEG);
                }
                if (!south && west)
                {
                    shapes.add(TALL_SOUTH_WEST_LEG);
                }
                if (!south && east)
                {
                    shapes.add(TALL_SOUTH_EAST_LEG);
                }
            }

            if (!tall)
            {
                if (!north && !west && !east)
                {
                    shapes.add(NORTH_WEST_LEG);
                    shapes.add(NORTH_EAST_LEG);
                }
                if (!north && west)
                {
                    shapes.add(NORTH_WEST_LEG);
                }
                if (!north && east)
                {
                    shapes.add(NORTH_EAST_LEG);
                }
                if (!south && !west && !east)
                {
                    shapes.add(SOUTH_WEST_LEG);
                    shapes.add(SOUTH_EAST_LEG);
                }
                if (!south && west)
                {
                    shapes.add(SOUTH_WEST_LEG);
                }
                if (!south && east)
                {
                    shapes.add(SOUTH_EAST_LEG);
                }
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
        boolean tall = state.get(TALL);
        boolean north = isCoffeeTable(world, pos, Direction.NORTH, tall);
        boolean east = isCoffeeTable(world, pos, Direction.EAST, tall);
        boolean south = isCoffeeTable(world, pos, Direction.SOUTH, tall);
        boolean west = isCoffeeTable(world, pos, Direction.WEST, tall);
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }

    private boolean isCoffeeTable(IWorld world, BlockPos source, Direction direction, boolean tall)
    {
        BlockState state = world.getBlockState(source.offset(direction));
        return state.getBlock() == this && state.get(TALL) == tall;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos pos = context.getPos();
        BlockState state = context.getWorld().getBlockState(pos);
        if(state.getBlock() == this)
        {
            return state.with(TALL, true);
        }
        return super.getStateForPlacement(context);
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
    public boolean isReplaceable(BlockState state, BlockItemUseContext context)
    {
        ItemStack stack = context.getItem();
        return !state.get(TALL) && stack.getItem() == this.asItem();
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
        builder.add(TALL);
    }
}
