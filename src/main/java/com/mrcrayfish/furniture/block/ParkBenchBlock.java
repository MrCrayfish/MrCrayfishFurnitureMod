package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(TYPE, Type.SINGLE).setValue(WATERLOGGED, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] SEAT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 8, 3, 16, 9, 16), Direction.SOUTH));
        final VoxelShape[] BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 9, 1, 16, 20, 5), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 0, 3, 4, 8, 6), Direction.SOUTH));
        final VoxelShape[] FRONT_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 0, 12, 4, 8, 15), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(12, 0, 12, 15, 8, 15), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(12, 0, 3, 15, 8, 6), Direction.SOUTH));
        final VoxelShape[] LEFT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 6, 6, 4, 8, 12), Direction.SOUTH));
        final VoxelShape[] RIGHT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(12, 6, 6, 15, 8, 12), Direction.SOUTH));
        final VoxelShape[] FRONT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 6, 12, 15, 8, 15), Direction.SOUTH));
        final VoxelShape[] BACK_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 6, 3, 15, 8, 6), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            Type type = state.getValue(TYPE);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(SEAT[direction.get2DDataValue()]);
            shapes.add(BACKREST[direction.get2DDataValue()]);
            shapes.add(FRONT_SUPPORT[direction.get2DDataValue()]);
            shapes.add(BACK_SUPPORT[direction.get2DDataValue()]);

            switch(type)
            {
                case SINGLE:
                    shapes.add(BACK_LEFT_LEG[direction.get2DDataValue()]);
                    shapes.add(FRONT_LEFT_LEG[direction.get2DDataValue()]);
                    shapes.add(FRONT_RIGHT_LEG[direction.get2DDataValue()]);
                    shapes.add(BACK_RIGHT_LEG[direction.get2DDataValue()]);
                    shapes.add(LEFT_SUPPORT[direction.get2DDataValue()]);
                    shapes.add(RIGHT_SUPPORT[direction.get2DDataValue()]);
                    break;
                case LEFT:
                    shapes.add(BACK_LEFT_LEG[direction.get2DDataValue()]);
                    shapes.add(FRONT_LEFT_LEG[direction.get2DDataValue()]);
                    shapes.add(LEFT_SUPPORT[direction.get2DDataValue()]);
                    break;
                case RIGHT:
                    shapes.add(FRONT_RIGHT_LEG[direction.get2DDataValue()]);
                    shapes.add(BACK_RIGHT_LEG[direction.get2DDataValue()]);
                    shapes.add(RIGHT_SUPPORT[direction.get2DDataValue()]);
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult result)
    {
        return SeatEntity.create(level, pos, 0.3375, playerEntity, state.getValue(DIRECTION));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return this.getBenchState(state, context.getLevel(), context.getClickedPos(), state.getValue(DIRECTION));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getBenchState(state, level, pos, state.getValue(DIRECTION));
    }

    private BlockState getBenchState(BlockState state, LevelAccessor level, BlockPos pos, Direction dir)
    {
        boolean left = this.isBench(level, pos, dir.getCounterClockWise(), dir);
        boolean right = this.isBench(level, pos, dir.getClockWise(), dir);
        if(left && right)
        {
            return state.setValue(TYPE, Type.MIDDLE);
        }
        else if(left)
        {
            return state.setValue(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            return state.setValue(TYPE, Type.LEFT);
        }
        return state.setValue(TYPE, Type.SINGLE);
    }

    private boolean isBench(LevelAccessor level, BlockPos source, Direction direction, Direction targetDirection)
    {
        BlockState state = level.getBlockState(source.relative(direction));
        if(state.getBlock() == this)
        {
            Direction sofaDirection = state.getValue(DIRECTION);
            return sofaDirection.equals(targetDirection);
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum Type implements StringRepresentable
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
        public String getSerializedName()
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
