package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.common.ModTags;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;
    public final ImmutableMap<BlockState, VoxelShape> COLLISION_SHAPES;

    public UpgradedFenceBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates(), false);
        COLLISION_SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates(), true);
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states, boolean collision)
    {
        final VoxelShape FENCE_POST = Block.box(6, 0, 6, 10, 17, 10);
        final VoxelShape[] POST_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0.75, 0, 6.5, 4.75, 16, 9.5), Direction.WEST));
        final VoxelShape[] POST_MIDDLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(6.01, 0, 6.5, 8, 16, 9.5), Direction.WEST));
        final VoxelShape[] FRAME_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 2.5, 7, 6, 5.5, 9), Direction.WEST));
        final VoxelShape[] FRAME_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 10.5, 7, 6.01, 13.5, 9), Direction.WEST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states)
        {
            boolean north = state.getValue(NORTH);
            boolean east = state.getValue(EAST);
            boolean south = state.getValue(SOUTH);
            boolean west = state.getValue(WEST);
            boolean post = !(north && !east && south && !west || !north && east && !south && west);

            List<VoxelShape> shapes = new ArrayList<>();
            if (post)
            {
                shapes.add(this.applyCollision(FENCE_POST, collision));
            }
            if (north)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.NORTH.get2DDataValue()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.NORTH.get2DDataValue()], collision));
                shapes.add(FRAME_BOTTOM[Direction.NORTH.get2DDataValue()]);
                shapes.add(FRAME_TOP[Direction.NORTH.get2DDataValue()]);
            }
            if (east)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.EAST.get2DDataValue()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.EAST.get2DDataValue()], collision));
                shapes.add(FRAME_BOTTOM[Direction.EAST.get2DDataValue()]);
                shapes.add(FRAME_TOP[Direction.EAST.get2DDataValue()]);
            }
            if (south)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.SOUTH.get2DDataValue()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.SOUTH.get2DDataValue()], collision));
                shapes.add(FRAME_BOTTOM[Direction.SOUTH.get2DDataValue()]);
                shapes.add(FRAME_TOP[Direction.SOUTH.get2DDataValue()]);
            }
            if (west)
            {
                shapes.add(this.applyCollision(POST_SIDE[Direction.WEST.get2DDataValue()], collision));
                shapes.add(this.applyCollision(POST_MIDDLE[Direction.WEST.get2DDataValue()], collision));
                shapes.add(FRAME_BOTTOM[Direction.WEST.get2DDataValue()]);
                shapes.add(FRAME_TOP[Direction.WEST.get2DDataValue()]);
            }
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    private VoxelShape applyCollision(VoxelShape shape, boolean collision)
    {
        if (collision)
        {
            shape = VoxelShapeHelper.setMaxHeight(shape, 1.5);
            shape = VoxelShapeHelper.limitHorizontal(shape);
        }
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return COLLISION_SHAPES.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getFenceState(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getFenceState(state, level, pos);
    }

    private BlockState getFenceState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        boolean north = this.canConnectToBlock(level, pos, Direction.NORTH);
        boolean east = this.canConnectToBlock(level, pos, Direction.EAST);
        boolean south = this.canConnectToBlock(level, pos, Direction.SOUTH);
        boolean west = this.canConnectToBlock(level, pos, Direction.WEST);
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
    }

    private boolean canConnectToBlock(LevelAccessor level, BlockPos pos, Direction direction)
    {
        BlockPos offsetPos = pos.relative(direction);
        BlockState offsetState = level.getBlockState(offsetPos);

        boolean flag1 = false;
        if (offsetState.getBlock() instanceof UpgradedGateBlock)
        {
            Direction gateDirection = offsetState.getValue(UpgradedGateBlock.DIRECTION);
            DoorHingeSide hingeSide = offsetState.getValue(UpgradedGateBlock.HINGE);
            Direction hingeFace = hingeSide == DoorHingeSide.LEFT ? gateDirection.getCounterClockWise() : gateDirection.getClockWise();
            flag1 = direction == hingeFace.getOpposite() || (!offsetState.getValue(UpgradedGateBlock.DOUBLE) && direction.getAxis() != gateDirection.getAxis());
        }

        boolean flag = offsetState.is(ModTags.Blocks.UPGRADED_FENCES) && offsetState.is(ModTags.Blocks.PICKET_FENCES) == this.defaultBlockState().is(ModTags.Blocks.PICKET_FENCES);
        return !isExceptionForConnection(offsetState) && offsetState.isFaceSturdy(level, offsetPos, direction.getOpposite()) || flag || flag1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity)
    {
        return BlockPathTypes.FENCE;
    }
}
