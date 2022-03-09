package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class UpgradedGateBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<DoorHingeSide> HINGE = EnumProperty.create("hinge", DoorHingeSide.class);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty DOUBLE = BooleanProperty.create("double");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;
    public final ImmutableMap<BlockState, VoxelShape> COLLISION_SHAPES;

    public UpgradedGateBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HINGE, DoorHingeSide.LEFT).setValue(OPEN, false).setValue(DOUBLE, false).setValue(POWERED, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates(), false);
        COLLISION_SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates(), true);
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states, boolean collision)
    {
        final VoxelShape[] RIGHT_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1.5, 1, 7, 14.5, 17, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_DOUBLE_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 1, 7, 14, 17, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 1, -5, 15, 17, 8), Direction.SOUTH));
        final VoxelShape[] RIGHT_DOUBLE_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 1, -6, 15, 17, 8), Direction.SOUTH));
        final VoxelShape[] RIGHT_HINGE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(14, 3, 7, 15, 6, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_HINGE_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(14, 12, 7, 15, 15, 9), Direction.SOUTH));
        final VoxelShape[] RIGHT_POST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(15, 0, 6, 18, 17, 10), Direction.SOUTH));
        final VoxelShape[] LEFT_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1.5, 1, 7, 14.5, 17, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_DOUBLE_GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(2, 1, 7, 16, 17, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 1, -5, 3, 17, 8), Direction.SOUTH));
        final VoxelShape[] LEFT_DOUBLE_GATE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 1, -6, 3, 17, 8), Direction.SOUTH));
        final VoxelShape[] LEFT_HINGE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 3, 7, 2, 6, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_HINGE_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 12, 7, 2, 15, 9), Direction.SOUTH));
        final VoxelShape[] LEFT_POST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(-2, 0, 6, 1, 17, 10), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            DoorHingeSide hingeSide = state.getValue(HINGE);
            boolean open = state.getValue(OPEN);
            boolean double_ = state.getValue(DOUBLE);

            List<VoxelShape> shapes = new ArrayList<>();
            switch(hingeSide)
            {
                case LEFT:
                    VoxelShape post = LEFT_POST[direction.get2DDataValue()];
                    if(collision)
                    {
                        post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                        post = VoxelShapeHelper.limitHorizontal(post);
                    }
                    shapes.add(post);
                    shapes.add(LEFT_HINGE_BOTTOM[direction.get2DDataValue()]);
                    shapes.add(LEFT_HINGE_TOP[direction.get2DDataValue()]);
                    if(open)
                    {
                        VoxelShape doubleGate = double_ ? LEFT_DOUBLE_GATE_OPEN[direction.get2DDataValue()] : LEFT_GATE_OPEN[direction.get2DDataValue()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    else
                    {
                        VoxelShape doubleGate = double_ ? LEFT_DOUBLE_GATE_CLOSED[direction.get2DDataValue()] : LEFT_GATE_CLOSED[direction.get2DDataValue()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    if(!double_)
                    {
                        post = RIGHT_POST[direction.get2DDataValue()];
                        if(collision)
                        {
                            post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                            post = VoxelShapeHelper.limitHorizontal(post);
                        }
                        shapes.add(post);
                    }
                    break;
                case RIGHT:
                    post = RIGHT_POST[direction.get2DDataValue()];
                    if(collision)
                    {
                        post = VoxelShapeHelper.setMaxHeight(post, 1.5);
                        post = VoxelShapeHelper.limitHorizontal(post);
                    }
                    shapes.add(post);
                    shapes.add(RIGHT_HINGE_BOTTOM[direction.get2DDataValue()]);
                    shapes.add(RIGHT_HINGE_TOP[direction.get2DDataValue()]);
                    if(open)
                    {
                        VoxelShape doubleGate = double_ ? RIGHT_DOUBLE_GATE_OPEN[direction.get2DDataValue()] : RIGHT_GATE_OPEN[direction.get2DDataValue()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    else
                    {
                        VoxelShape doubleGate = double_ ? RIGHT_DOUBLE_GATE_CLOSED[direction.get2DDataValue()] : RIGHT_GATE_CLOSED[direction.get2DDataValue()];
                        if(collision)
                        {
                            doubleGate = VoxelShapeHelper.setMaxHeight(doubleGate, 1.5);
                            doubleGate = VoxelShapeHelper.limitHorizontal(doubleGate);
                        }
                        shapes.add(doubleGate);
                    }
                    if(!double_)
                    {
                        post = LEFT_POST[direction.get2DDataValue()];
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult result)
    {
        Direction hitFace = result.getDirection();
        Direction direction = state.getValue(DIRECTION);
        boolean open = state.getValue(OPEN);

        if(hitFace.getAxis() != direction.getAxis())
        {
            if(!open || hitFace.getAxis().isVertical())
            {
                return InteractionResult.PASS;
            }
        }

        DoorHingeSide hingeSide = state.getValue(HINGE);
        this.openGate(state, level, pos, direction, hitFace, !open);
        this.openDoubleGate(level, pos, direction, hitFace, hingeSide, !open);
        this.openAdjacentGate(level, pos, direction, Direction.UP, hitFace, hingeSide, !open, 5);
        this.openAdjacentGate(level, pos, direction, Direction.DOWN, hitFace, hingeSide, !open, 5);

        level.levelEvent(playerEntity, !open ? 1008 : 1014, pos, 0);

        return InteractionResult.SUCCESS;
    }

    private void openGate(BlockState state, LevelAccessor level, BlockPos pos, Direction direction, Direction hitFace, boolean open)
    {
        if(open)
        {
            if(hitFace.getOpposite() == direction)
            {
                level.setBlock(pos, state.setValue(OPEN, open), 3);
            }
            else if(hitFace == direction)
            {
                level.setBlock(pos, state.setValue(OPEN, open).setValue(HINGE, this.getOppositeHinge(state.getValue(HINGE))).setValue(DIRECTION, hitFace.getOpposite()), 3);
            }
        }
        else
        {
            level.setBlock(pos, state.setValue(OPEN, open), 3);
        }
    }

    private void openAdjacentGate(LevelAccessor level, BlockPos pos, Direction direction, Direction offset, Direction hitFace, DoorHingeSide hingeSide, boolean open, int limit)
    {
        if(limit <= 0)
        {
            return;
        }

        BlockPos offsetPos = pos.relative(offset);
        BlockState state = level.getBlockState(offsetPos);
        if(state.getBlock() == this)
        {
            if(state.getValue(DIRECTION) != direction || state.getValue(HINGE) != hingeSide || state.getValue(OPEN) == open)
            {
                return;
            }
            this.openGate(state, level, offsetPos, direction, hitFace, open);
            this.openDoubleGate(level, offsetPos, direction, hitFace, hingeSide, open);
            this.openAdjacentGate(level, offsetPos, direction, offset, hitFace, hingeSide, open, limit - 1);
        }
    }

    private void openDoubleGate(LevelAccessor level, BlockPos pos, Direction direction, Direction hitFace, DoorHingeSide hingeSide, boolean open)
    {
        BlockPos adjacentPos = pos.relative(hingeSide == DoorHingeSide.LEFT ? direction.getClockWise() : direction.getCounterClockWise());
        BlockState adjacentState = level.getBlockState(adjacentPos);
        if(adjacentState.getBlock() == this && adjacentState.getValue(DIRECTION).getAxis() == direction.getAxis())
        {
            if(adjacentState.getValue(HINGE) != hingeSide)
            {
                this.openGate(adjacentState, level, adjacentPos, direction, hitFace, open);
            }
            else
            {
                this.openGate(adjacentState.setValue(DIRECTION, adjacentState.getValue(DIRECTION).getOpposite()).setValue(HINGE, this.getOppositeHinge(adjacentState.getValue(HINGE))), level, adjacentPos, direction, hitFace, open);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return super.getStateForPlacement(context).setValue(HINGE, this.getHingeSide(context));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        Direction facing = state.getValue(DIRECTION);
        Direction offset = state.getValue(HINGE) == DoorHingeSide.LEFT ? facing.getClockWise() : facing.getCounterClockWise();
        BlockState adjacentBlock = level.getBlockState(pos.relative(offset));
        return state.setValue(DOUBLE, adjacentBlock.getBlock() == this);
    }

    private DoorHingeSide getHingeSide(BlockPlaceContext context)
    {
        Direction playerFacing = context.getHorizontalDirection();
        int offsetX = playerFacing.getStepX();
        int offsetZ = playerFacing.getStepZ();
        BlockPos pos = context.getClickedPos();
        Vec3 hitVec = context.getClickLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        boolean side = offsetX < 0 && hitVec.z < 0.5 || offsetX > 0 && hitVec.z > 0.5 || offsetZ < 0 && hitVec.x > 0.5 || offsetZ > 0 && hitVec.x < 0.5;
        return side ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
    }

    private DoorHingeSide getOppositeHinge(DoorHingeSide hingeSide)
    {
        return hingeSide == DoorHingeSide.LEFT ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(HINGE);
        builder.add(OPEN);
        builder.add(DOUBLE);
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity)
    {
        return !state.getValue(OPEN) ? BlockPathTypes.FENCE : BlockPathTypes.OPEN;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if(!level.isClientSide())
        {
            boolean powered = level.hasNeighborSignal(pos);
            if(state.getValue(POWERED) != powered)
            {
                level.setBlock(pos, state.setValue(POWERED, powered).setValue(OPEN, powered), 2);
                if(state.getValue(OPEN) != powered)
                {
                    level.levelEvent(null, powered ? 1008 : 1014, pos, 0);
                }
            }
        }
    }
}
