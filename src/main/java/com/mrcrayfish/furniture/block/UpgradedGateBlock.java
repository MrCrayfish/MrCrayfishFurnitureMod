package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class UpgradedGateBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<DoorHingeSide> HINGE = EnumProperty.create("hinge", DoorHingeSide.class);
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty DOUBLE = BooleanProperty.create("double");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;
    public final ImmutableMap<BlockState, VoxelShape> COLLISION_SHAPES;

    public UpgradedGateBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(HINGE, DoorHingeSide.LEFT).with(OPEN, false).with(DOUBLE, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates(), false);
        COLLISION_SHAPES = this.generateShapes(this.getStateContainer().getValidStates(), true);
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states, boolean collision)
    {
        final VoxelShape[] RIGHT_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 1, 7, 14.5, 17, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_DOUBLE_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 1, 7, 14, 17, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 1, -5, 15, 17, 8), Direction.SOUTH));
        final VoxelShape[] RIGHT_DOUBLE_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 1, -6, 15, 17, 8), Direction.SOUTH));
        final VoxelShape[] RIGHT_HINGE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14, 3, 7, 15, 6, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_HINGE_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14, 12, 7, 15, 15, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_POST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(15, 0, 6, 18, 17, 10), Direction.SOUTH));
        final VoxelShape[] LEFT_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 1, 7, 14.5, 17, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_DOUBLE_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2, 1, 7, 16, 17, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 1, -5, 3, 17, 8), Direction.SOUTH));
        final VoxelShape[] LEFT_DOUBLE_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 1, -6, 3, 17, 8), Direction.SOUTH));
        final VoxelShape[] LEFT_HINGE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 3, 7, 2, 6, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_HINGE_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 12, 7, 2, 15, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_POST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(-2, 0, 6, 1, 17, 10), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            DoorHingeSide hingeSide = state.get(HINGE);
            boolean open = state.get(OPEN);
            boolean double_ = state.get(DOUBLE);

            List<VoxelShape> shapes = new ArrayList<>();
            switch(hingeSide)
            {
                case LEFT:
                    VoxelShape post = LEFT_POST[direction.getHorizontalIndex()];
                    if(collision)
                    {
                        post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                        post = VoxelShapeHelper.limitHorizontal(post);
                    }
                    shapes.add(post);
                    shapes.add(LEFT_HINGE_BOTTOM[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_HINGE_TOP[direction.getHorizontalIndex()]);
                    if(open)
                    {
                        VoxelShape doubleGate = double_ ? LEFT_DOUBLE_GATE_OPEN[direction.getHorizontalIndex()] : LEFT_GATE_OPEN[direction.getHorizontalIndex()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    else
                    {
                        VoxelShape doubleGate = double_ ? LEFT_DOUBLE_GATE_CLOSED[direction.getHorizontalIndex()] : LEFT_GATE_CLOSED[direction.getHorizontalIndex()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    if(!double_)
                    {
                        post = RIGHT_POST[direction.getHorizontalIndex()];
                        if(collision)
                        {
                            post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                            post = VoxelShapeHelper.limitHorizontal(post);
                        }
                        shapes.add(post);
                    }
                    break;
                case RIGHT:
                    post = RIGHT_POST[direction.getHorizontalIndex()];
                    if(collision)
                    {
                        post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                        post = VoxelShapeHelper.limitHorizontal(post);
                    }
                    shapes.add(post);
                    shapes.add(RIGHT_HINGE_BOTTOM[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_HINGE_TOP[direction.getHorizontalIndex()]);
                    if(open)
                    {
                        VoxelShape doubleGate = double_ ? RIGHT_DOUBLE_GATE_OPEN[direction.getHorizontalIndex()] : RIGHT_GATE_OPEN[direction.getHorizontalIndex()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    else
                    {
                        VoxelShape doubleGate = double_ ? RIGHT_DOUBLE_GATE_CLOSED[direction.getHorizontalIndex()] : RIGHT_GATE_CLOSED[direction.getHorizontalIndex()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    if(!double_)
                    {
                        post = LEFT_POST[direction.getHorizontalIndex()];
                        if(collision)
                        {
                            post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                            post = VoxelShapeHelper.limitHorizontal(post);
                        }
                        shapes.add(post);
                    }
                    break;
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
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        Direction hitFace = result.getFace();
        Direction direction = state.get(DIRECTION);
        boolean open = state.get(OPEN);

        if(hitFace.getAxis() != direction.getAxis())
        {
            if(!open || hitFace.getAxis().isVertical())
            {
                return ActionResultType.PASS;
            }
        }

        DoorHingeSide hingeSide = state.get(HINGE);
        this.openGate(state, world, pos, direction, hitFace, !open);
        this.openDoubleGate(world, pos, direction, hitFace, hingeSide, !open);
        this.openAdjacentGate(world, pos, direction, Direction.UP, hitFace, hingeSide, !open, 5);
        this.openAdjacentGate(world, pos, direction, Direction.DOWN, hitFace, hingeSide, !open, 5);

        world.playEvent(playerEntity, !open ? 1008 : 1014, pos, 0);

        return ActionResultType.SUCCESS;
    }

    private void openGate(BlockState state, IWorld world, BlockPos pos, Direction direction, Direction hitFace, boolean open)
    {
        if(open)
        {
            if(hitFace.getOpposite() == direction)
            {
                world.setBlockState(pos, state.with(OPEN, open), 3);
            }
            else if(hitFace == direction)
            {
                world.setBlockState(pos, state.with(OPEN, open).with(HINGE, this.getOppositeHinge(state.get(HINGE))).with(DIRECTION, hitFace.getOpposite()), 3);
            }
        }
        else
        {
            world.setBlockState(pos, state.with(OPEN, open), 3);
        }
    }

    private void openAdjacentGate(IWorld world, BlockPos pos, Direction direction, Direction offset, Direction hitFace, DoorHingeSide hingeSide, boolean open, int limit)
    {
        if(limit <= 0)
        {
            return;
        }

        BlockPos offsetPos = pos.offset(offset);
        BlockState state = world.getBlockState(offsetPos);
        if(state.getBlock() == this)
        {
            if(state.get(DIRECTION) != direction || state.get(HINGE) != hingeSide || state.get(OPEN) == open)
            {
                return;
            }
            this.openGate(state, world, offsetPos, direction, hitFace, open);
            this.openDoubleGate(world, offsetPos, direction, hitFace, hingeSide, open);
            this.openAdjacentGate(world, offsetPos, direction, offset, hitFace, hingeSide, open, limit - 1);
        }
    }

    private void openDoubleGate(IWorld world, BlockPos pos, Direction direction, Direction hitFace, DoorHingeSide hingeSide, boolean open)
    {
        BlockPos adjacentPos = pos.offset(hingeSide == DoorHingeSide.LEFT ? direction.rotateY() : direction.rotateYCCW());
        BlockState adjacentState = world.getBlockState(adjacentPos);
        if(adjacentState.getBlock() == this && adjacentState.get(DIRECTION).getAxis() == direction.getAxis())
        {
            if(adjacentState.get(HINGE) != hingeSide)
            {
                this.openGate(adjacentState, world, adjacentPos, direction, hitFace, open);
            }
            else
            {
                this.openGate(adjacentState.with(DIRECTION, adjacentState.get(DIRECTION).getOpposite()).with(HINGE, this.getOppositeHinge(adjacentState.get(HINGE))), world, adjacentPos, direction, hitFace, open);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return super.getStateForPlacement(context).with(HINGE, this.getHingeSide(context));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        Direction facing = state.get(DIRECTION);
        Direction offset = state.get(HINGE) == DoorHingeSide.LEFT ? facing.rotateY() : facing.rotateYCCW();
        BlockState adjacentBlock = world.getBlockState(pos.offset(offset));
        return state.with(DOUBLE, adjacentBlock.getBlock() == this);
    }

    private DoorHingeSide getHingeSide(BlockItemUseContext context)
    {
        Direction playerFacing = context.getPlacementHorizontalFacing();
        int offsetX = playerFacing.getXOffset();
        int offsetZ = playerFacing.getZOffset();
        BlockPos pos = context.getPos();
        Vector3d hitVec = context.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
        boolean side = offsetX < 0 && hitVec.z < 0.5 || offsetX > 0 && hitVec.z > 0.5 || offsetZ < 0 && hitVec.x > 0.5 || offsetZ > 0 && hitVec.x < 0.5;
        return side ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
    }

    private DoorHingeSide getOppositeHinge(DoorHingeSide hingeSide)
    {
        return hingeSide == DoorHingeSide.LEFT ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(HINGE);
        builder.add(OPEN);
        builder.add(DOUBLE);
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity)
    {
        return !state.get(OPEN) ? PathNodeType.FENCE : PathNodeType.OPEN;
    }
}
