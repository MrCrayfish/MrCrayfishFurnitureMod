package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ParkBenchBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ParkBenchBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(TYPE, Type.SINGLE).with(WATERLOGGED, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] SEAT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 8, 3, 16, 9, 16), Direction.SOUTH));
        final VoxelShape[] BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 9, 1, 16, 20, 5), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 0, 3, 4, 8, 6), Direction.SOUTH));
        final VoxelShape[] FRONT_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 0, 12, 4, 8, 15), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12, 0, 12, 15, 8, 15), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12, 0, 3, 15, 8, 6), Direction.SOUTH));
        final VoxelShape[] LEFT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 6, 6, 4, 8, 12), Direction.SOUTH));
        final VoxelShape[] RIGHT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12, 6, 6, 15, 8, 12), Direction.SOUTH));
        final VoxelShape[] FRONT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 6, 12, 15, 8, 15), Direction.SOUTH));
        final VoxelShape[] BACK_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 6, 3, 15, 8, 6), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            Type type = state.get(TYPE);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(SEAT[direction.getHorizontalIndex()]);
            shapes.add(BACKREST[direction.getHorizontalIndex()]);
            shapes.add(FRONT_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(BACK_SUPPORT[direction.getHorizontalIndex()]);

            switch(type)
            {
                case SINGLE:
                    shapes.add(BACK_LEFT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(FRONT_LEFT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(FRONT_RIGHT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(BACK_RIGHT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_SUPPORT[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_SUPPORT[direction.getHorizontalIndex()]);
                    break;
                case LEFT:
                    shapes.add(BACK_LEFT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(FRONT_LEFT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_SUPPORT[direction.getHorizontalIndex()]);
                    break;
                case RIGHT:
                    shapes.add(FRONT_RIGHT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(BACK_RIGHT_LEG[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_SUPPORT[direction.getHorizontalIndex()]);
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
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        return SeatEntity.create(world, pos, 0.3375, playerEntity);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return this.getBenchState(state, context.getWorld(), context.getPos(), state.get(DIRECTION));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        return this.getBenchState(state, world, pos, state.get(DIRECTION));
    }

    private BlockState getBenchState(BlockState state, IWorld world, BlockPos pos, Direction dir)
    {
        boolean left = isBench(world, pos, dir.rotateYCCW(), dir);
        boolean right = isBench(world, pos, dir.rotateY(), dir);
        if(left && right)
        {
            return state.with(TYPE, Type.MIDDLE);
        }
        else if(left)
        {
            return state.with(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            return state.with(TYPE, Type.LEFT);
        }
        return state.with(TYPE, Type.SINGLE);
    }

    private boolean isBench(IWorld world, BlockPos source, Direction direction, Direction targetDirection)
    {
        BlockState state = world.getBlockState(source.offset(direction));
        if(state.getBlock() == this)
        {
            Direction sofaDirection = state.get(DIRECTION);
            return sofaDirection.equals(targetDirection);
        }
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(TYPE);
    }

    public enum Type implements IStringSerializable
    {
        SINGLE("single"),
        LEFT("left"),
        RIGHT("right"),
        MIDDLE("middle");

        private final String id;

        Type(String id)
        {
            this.id = id;
        }

        @Override
        public String getString()
        {
            return id;
        }

        @Override
        public String toString()
        {
            return id;
        }
    }
}
