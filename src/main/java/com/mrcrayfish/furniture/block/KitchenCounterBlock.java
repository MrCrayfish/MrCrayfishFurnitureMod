package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class KitchenCounterBlock extends FurnitureHorizontalBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public KitchenCounterBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, Type.DEFAULT).setValue(DIRECTION, Direction.NORTH));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 ->
        {
            final VoxelShape TOP = Block.box(0, 13, 0, 16, 16, 16);
            final VoxelShape[] DEFAULT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 0, 16, 13, 15), Direction.SOUTH));
            final VoxelShape[] LEFT_INVERTED_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 0, 0, 16, 13, 15), Direction.SOUTH));
            final VoxelShape[] RIGHT_INVERTED_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 0, 15, 13, 15), Direction.SOUTH));
            final VoxelShape[] LEFT_CORNER_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 15, 15, 13, 16), Direction.SOUTH));
            final VoxelShape[] RIGHT_CORNER_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 0, 15, 16, 13, 16), Direction.SOUTH));

            Type type = state1.getValue(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP);
            switch(type)
            {
                case DEFAULT:
                    shapes.add(DEFAULT_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    break;
                case LEFT_CORNER:
                    shapes.add(DEFAULT_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    shapes.add(LEFT_CORNER_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    break;
                case RIGHT_CORNER:
                    shapes.add(DEFAULT_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    shapes.add(RIGHT_CORNER_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    break;
                case LEFT_CORNER_INVERTED:
                    shapes.add(LEFT_INVERTED_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    break;
                case RIGHT_CORNER_INVERTED:
                    shapes.add(RIGHT_INVERTED_BASE[state.getValue(DIRECTION).get2DDataValue()]);
                    break;
            }
            return VoxelShapeHelper.combineAll(shapes);
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getKitchenCounterState(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getKitchenCounterState(state, level, pos);
    }

    private BlockState getKitchenCounterState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        Direction direction = state.getValue(DIRECTION);
        BlockState frontState = level.getBlockState(pos.relative(direction.getOpposite()));
        if(this.isKitchenBlock(frontState))
        {
            if(frontState.getValue(DIRECTION) == direction.getClockWise())
            {
                return state.setValue(TYPE, Type.RIGHT_CORNER);
            }
            else if(frontState.getValue(DIRECTION) == direction.getCounterClockWise())
            {
                return state.setValue(TYPE, Type.LEFT_CORNER);
            }
        }

        BlockState backState = level.getBlockState(pos.relative(direction));
        if(backState.getBlock() instanceof KitchenCounterBlock)
        {
            if(backState.getValue(DIRECTION) == direction.getClockWise())
            {
                BlockState leftState = level.getBlockState(pos.relative(direction.getCounterClockWise()));
                if(!(leftState.getBlock() instanceof KitchenCounterBlock) || leftState.getValue(DIRECTION) == direction.getOpposite())
                {
                    return state.setValue(TYPE, Type.LEFT_CORNER_INVERTED);
                }
            }
            if(backState.getValue(DIRECTION) == direction.getCounterClockWise())
            {
                BlockState rightState = level.getBlockState(pos.relative(direction.getClockWise()));
                if(!(rightState.getBlock() instanceof KitchenCounterBlock) || rightState.getValue(DIRECTION) == direction.getOpposite())
                {
                    return state.setValue(TYPE, Type.RIGHT_CORNER_INVERTED);
                }
            }
        }

        return state.setValue(TYPE, Type.DEFAULT);
    }

    protected boolean isKitchenBlock(BlockState state)
    {
        return state.getBlock() instanceof KitchenCounterBlock || state.getBlock() instanceof KitchenDrawerBlock || state.getBlock() instanceof KitchenSinkBlock;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum Type implements StringRepresentable
    {
        DEFAULT("default"),
        LEFT_CORNER("left_corner"),
        RIGHT_CORNER("right_corner"),
        LEFT_CORNER_INVERTED("left_corner_inverted"),
        RIGHT_CORNER_INVERTED("right_corner_inverted");

        private String id;

        Type(String id)
        {
            this.id = id;
        }

        @Override
        public String toString()
        {
            return this.getSerializedName();
        }

        @Override
        public String getSerializedName()
        {
            return this.id;
        }
    }
}
