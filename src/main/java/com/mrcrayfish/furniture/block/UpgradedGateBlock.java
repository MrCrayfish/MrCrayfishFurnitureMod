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
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
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

    public UpgradedGateBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(HINGE, DoorHingeSide.LEFT).with(OPEN, false).with(DOUBLE, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] GATE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 1, 7, 16, 17, 10), Direction.SOUTH));
        final VoxelShape[] GATE_LEFT_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 1, -4, 3, 17, 9), Direction.SOUTH));
        final VoxelShape[] GATE_RIGHT_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 1, -4, 16, 17, 9), Direction.SOUTH));
        final VoxelShape[] POST_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(-2, 0, 6, 1, 17, 10), Direction.SOUTH));
        final VoxelShape[] POST_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(15, 0, 6, 18, 17, 10), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            DoorHingeSide hingeSide = state.get(HINGE);
            boolean open = state.get(OPEN);
            boolean double_ = state.get(DOUBLE);

            List<VoxelShape> shapes = new ArrayList<>();
            if(!open)
            {
                shapes.add(GATE_CLOSED[direction.getHorizontalIndex()]);
            }
            switch(hingeSide)
            {
                case LEFT:
                    shapes.add(POST_LEFT[direction.getHorizontalIndex()]);
                    if(open)
                    {
                        shapes.add(GATE_LEFT_OPEN[direction.getHorizontalIndex()]);
                    }
                    if(!double_)
                    {
                        shapes.add(POST_RIGHT[direction.getHorizontalIndex()]);
                    }
                    break;
                case RIGHT:
                    shapes.add(POST_RIGHT[direction.getHorizontalIndex()]);
                    if(open)
                    {
                        shapes.add(GATE_RIGHT_OPEN[direction.getHorizontalIndex()]);
                    }
                    if(!double_)
                    {
                        shapes.add(POST_LEFT[direction.getHorizontalIndex()]);
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
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult)
    {
        Direction direction = state.get(DIRECTION);
        Direction hitFace = rayTraceResult.getFace();
        DoorHingeSide hingeSide = state.get(HINGE);
        boolean open = state.get(OPEN);
        this.openGate(state, world, pos, direction, hitFace, !open);
        this.openDoubleGate(world, pos, direction, hitFace, hingeSide, !open);
        this.openAdjacentGate(world, pos, direction, Direction.UP, hitFace, hingeSide, !open, 5);
        this.openAdjacentGate(world, pos, direction, Direction.DOWN, hitFace, hingeSide, !open, 5);
        world.playEvent(playerEntity, !open ? 1008 : 1014, pos, 0);
        return true;
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
        if(adjacentState.getBlock() == this && adjacentState.get(DIRECTION).getAxis() == direction.getAxis() && adjacentState.get(HINGE) != hingeSide)
        {
            this.openGate(adjacentState, world, adjacentPos, direction, hitFace, open);
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
        Vec3d hitVec = context.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
        System.out.println(hitVec);
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

    @Override
    public boolean isSideInvisible(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_)
    {
        return super.isSideInvisible(p_200122_1_, p_200122_2_, p_200122_3_);
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity)
    {
        return !state.get(OPEN) ? PathNodeType.FENCE : PathNodeType.OPEN;
    }
}