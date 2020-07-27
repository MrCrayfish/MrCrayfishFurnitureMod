package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class UpgradedFenceBlock extends FurnitureWaterloggedBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty POST = BooleanProperty.create("post");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;
    public final ImmutableMap<BlockState, VoxelShape> COLLISION_SHAPES;

    public UpgradedFenceBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(POST, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates(), false);
        COLLISION_SHAPES = this.generateShapes(this.getStateContainer().getValidStates(), true);
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states, boolean collision)
    {
        final VoxelShape FENCE_POST = Block.makeCuboidShape(6, 0, 6, 10, 17, 10);
        final VoxelShape[] POST_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.75, 0, 6.5, 4.75, 16, 9.5), Direction.WEST));
        final VoxelShape[] POST_MIDDLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(6.01, 0, 6.5, 8, 16, 9.5), Direction.WEST));
        final VoxelShape[] FRAME_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 2.5, 7, 6, 5.5, 9), Direction.WEST));
        final VoxelShape[] FRAME_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 10.5, 7, 6.01, 13.5, 9), Direction.WEST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean north = state.get(NORTH);
            boolean east = state.get(EAST);
            boolean south = state.get(SOUTH);
            boolean west = state.get(WEST);
            boolean post = state.get(POST);

            List<VoxelShape> shapes = new ArrayList<>();
            if(post)
            {
                shapes.add(this.applyCollision(FENCE_POST, collision));
            }
            if(north)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.NORTH.getHorizontalIndex()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.NORTH.getHorizontalIndex()], collision));
                shapes.add(FRAME_BOTTOM[Direction.NORTH.getHorizontalIndex()]);
                shapes.add(FRAME_TOP[Direction.NORTH.getHorizontalIndex()]);
            }
            if(east)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.EAST.getHorizontalIndex()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.EAST.getHorizontalIndex()], collision));
                shapes.add(FRAME_BOTTOM[Direction.EAST.getHorizontalIndex()]);
                shapes.add(FRAME_TOP[Direction.EAST.getHorizontalIndex()]);
            }
            if(south)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.SOUTH.getHorizontalIndex()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.SOUTH.getHorizontalIndex()], collision));
                shapes.add(FRAME_BOTTOM[Direction.SOUTH.getHorizontalIndex()]);
                shapes.add(FRAME_TOP[Direction.SOUTH.getHorizontalIndex()]);
            }
            if(west)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.WEST.getHorizontalIndex()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.WEST.getHorizontalIndex()], collision));
                shapes.add(FRAME_BOTTOM[Direction.WEST.getHorizontalIndex()]);
                shapes.add(FRAME_TOP[Direction.WEST.getHorizontalIndex()]);
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
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getFenceState(super.getStateForPlacement(context), context.getWorld(), context.getPos());
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        return this.getFenceState(state, world, pos);
    }

    private BlockState getFenceState(BlockState state, IWorld world, BlockPos pos)
    {
        boolean north = canConnectToBlock(state, world, pos, Direction.NORTH);
        boolean east = canConnectToBlock(state, world, pos, Direction.EAST);
        boolean south = canConnectToBlock(state, world, pos, Direction.SOUTH);
        boolean west = canConnectToBlock(state, world, pos, Direction.WEST);
        boolean post = !(north && !east && south && !west || !north && east && !south && west);
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west).with(POST, post);
    }

    private boolean canConnectToBlock(BlockState state, IWorld world, BlockPos pos, Direction direction)
    {
        BlockPos offsetPos = pos.offset(direction);
        BlockState offsetState = world.getBlockState(offsetPos);
        if(offsetState.getBlock() == this)
        {
            return true;
        }
        if(offsetState.getBlock() instanceof UpgradedGateBlock)
        {
            Direction gateDirection = offsetState.get(UpgradedGateBlock.DIRECTION);
            DoorHingeSide hingeSide = offsetState.get(UpgradedGateBlock.HINGE);
            Direction hingeFace = hingeSide == DoorHingeSide.LEFT ? gateDirection.rotateYCCW() : gateDirection.rotateY();
            return direction == hingeFace.getOpposite() || (!offsetState.get(UpgradedGateBlock.DOUBLE) && direction.getAxis() != gateDirection.getAxis());
        }
        return offsetState.isSolidSide(world, offsetPos, direction.getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
        builder.add(POST);
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity)
    {
        return PathNodeType.FENCE;
    }
}
