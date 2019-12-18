package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
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
public class HedgeBlock extends FurnitureWaterloggedBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;
    public final ImmutableMap<BlockState, VoxelShape> COLLISION_SHAPES;

    public HedgeBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates(), false);
        COLLISION_SHAPES = this.generateShapes(this.getStateContainer().getValidStates(), true);
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states, boolean collision)
    {
        final VoxelShape POST = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
        final VoxelShape[] SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4, 0, 0, 12, 16, 4), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean north = state.get(NORTH);
            boolean east = state.get(EAST);
            boolean south = state.get(SOUTH);
            boolean west = state.get(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(this.applyCollision(POST, collision));
            if(north)
            {
                shapes.add(this.applyCollision(SIDE[Direction.NORTH.getHorizontalIndex()], collision));
            }
            if(east)
            {
                shapes.add(this.applyCollision(SIDE[Direction.EAST.getHorizontalIndex()], collision));
            }
            if(south)
            {
                shapes.add(this.applyCollision(SIDE[Direction.SOUTH.getHorizontalIndex()], collision));
            }
            if(west)
            {
                shapes.add(this.applyCollision(SIDE[Direction.WEST.getHorizontalIndex()], collision));
            }
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    private VoxelShape applyCollision(VoxelShape shape, boolean collision)
    {
        if(collision)
        {
            shape = VoxelShapeHelper.setMaxHeight(shape, 1.5);
            shape = VoxelShapeHelper.limitHorizontal(shape);
        }
        return shape;
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

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return COLLISION_SHAPES.get(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        return this.getHedgeState(state, world, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getHedgeState(super.getStateForPlacement(context), context.getWorld(), context.getPos());
    }

    private BlockState getHedgeState(BlockState state, IWorld world, BlockPos pos)
    {
        boolean north = canConnectToBlock(state, world, pos, Direction.NORTH);
        boolean east = canConnectToBlock(state, world, pos, Direction.EAST);
        boolean south = canConnectToBlock(state, world, pos, Direction.SOUTH);
        boolean west = canConnectToBlock(state, world, pos, Direction.WEST);
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }

    private boolean canConnectToBlock(BlockState state, IWorld world, BlockPos pos, Direction direction)
    {
        BlockPos offsetPos = pos.offset(direction);
        BlockState offsetState = world.getBlockState(offsetPos);
        if(offsetState.getBlock() == this)
        {
            return true;
        }
        return offsetState.func_224755_d(world, offsetPos, direction.getOpposite());
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

    /*public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }*/

    @Override //causesSuffocation?
    public boolean func_229869_c_(BlockState p_229869_1_, IBlockReader p_229869_2_, BlockPos p_229869_3_)
    {
        return false;
    }

    @Deprecated
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1;
    }
}

