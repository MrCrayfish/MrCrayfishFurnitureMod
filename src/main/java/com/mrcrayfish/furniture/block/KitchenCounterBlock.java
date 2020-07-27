package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

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
        this.setDefaultState(this.getStateContainer().getBaseState().with(TYPE, Type.DEFAULT).with(DIRECTION, Direction.NORTH));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 ->
        {
            final VoxelShape TOP = Block.makeCuboidShape(0, 13, 0, 16, 16, 16);
            final VoxelShape[] DEFAULT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 16, 13, 15), Direction.SOUTH));
            final VoxelShape[] LEFT_INVERTED_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 0, 0, 16, 13, 15), Direction.SOUTH));
            final VoxelShape[] RIGHT_INVERTED_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 15, 13, 15), Direction.SOUTH));
            final VoxelShape[] LEFT_CORNER_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 15, 15, 13, 16), Direction.SOUTH));
            final VoxelShape[] RIGHT_CORNER_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 0, 15, 16, 13, 16), Direction.SOUTH));

            Type type = state1.get(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP);
            switch(type)
            {
                case DEFAULT:
                    shapes.add(DEFAULT_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    break;
                case LEFT_CORNER:
                    shapes.add(DEFAULT_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    shapes.add(LEFT_CORNER_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    break;
                case RIGHT_CORNER:
                    shapes.add(DEFAULT_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    shapes.add(RIGHT_CORNER_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    break;
                case LEFT_CORNER_INVERTED:
                    shapes.add(LEFT_INVERTED_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    break;
                case RIGHT_CORNER_INVERTED:
                    shapes.add(RIGHT_INVERTED_BASE[state.get(DIRECTION).getHorizontalIndex()]);
                    break;
            }
            return VoxelShapeHelper.combineAll(shapes);
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getKitchenCounterState(super.getStateForPlacement(context), context.getWorld(), context.getPos());
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        return this.getKitchenCounterState(state, world, pos);
    }

    private BlockState getKitchenCounterState(BlockState state, IWorld world, BlockPos pos)
    {
        Direction direction = state.get(DIRECTION);
        BlockState frontState = world.getBlockState(pos.offset(direction.getOpposite()));
        if(frontState.getBlock() instanceof KitchenCounterBlock)
        {
            if(frontState.get(DIRECTION) == direction.rotateY())
            {
                return state.with(TYPE, Type.RIGHT_CORNER);
            }
            else if(frontState.get(DIRECTION) == direction.rotateYCCW())
            {
                return state.with(TYPE, Type.LEFT_CORNER);
            }
        }

        BlockState backState = world.getBlockState(pos.offset(direction));
        if(backState.getBlock() instanceof KitchenCounterBlock)
        {
            if(backState.get(DIRECTION) == direction.rotateY())
            {
                BlockState leftState = world.getBlockState(pos.offset(direction.rotateYCCW()));
                if(!(leftState.getBlock() instanceof KitchenCounterBlock) || leftState.get(DIRECTION) == direction.getOpposite())
                {
                    return state.with(TYPE, Type.LEFT_CORNER_INVERTED);
                }
            }
            if(backState.get(DIRECTION) == direction.rotateYCCW())
            {
                BlockState rightState = world.getBlockState(pos.offset(direction.rotateY()));
                if(!(rightState.getBlock() instanceof KitchenCounterBlock) || rightState.get(DIRECTION) == direction.getOpposite())
                {
                    return state.with(TYPE, Type.RIGHT_CORNER_INVERTED);
                }
            }
        }

        return state.with(TYPE, Type.DEFAULT);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(TYPE);
    }

    public enum Type implements IStringSerializable
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
            return this.getString();
        }

        @Override
        public String getString()
        {
            return this.id;
        }
    }
}
