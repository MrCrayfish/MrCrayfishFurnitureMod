package com.mrcrayfish.furniture.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

/**
 * Author: MrCrayfish
 */
public class SofaBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public SofaBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(TYPE, Type.SINGLE));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        Direction sofaDirection = state.get(DIRECTION);
        boolean left = isSofa(world, pos, sofaDirection.rotateYCCW(), sofaDirection) || isSofa(world, pos, sofaDirection.rotateYCCW(), sofaDirection.rotateYCCW());
        boolean right = isSofa(world, pos, sofaDirection.rotateY(), sofaDirection) || isSofa(world, pos, sofaDirection.rotateY(), sofaDirection.rotateY());
        boolean cornerLeft = isSofa(world, pos, sofaDirection.getOpposite(), sofaDirection.rotateYCCW());
        boolean cornerRight = isSofa(world, pos, sofaDirection.getOpposite(), sofaDirection.rotateY());

        if(cornerLeft)
        {
            return state.with(TYPE, Type.CORNER_LEFT);
        }
        else if(cornerRight)
        {
            return state.with(TYPE, Type.CORNER_RIGHT);
        }
        else if(left && right)
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

    private boolean isSofa(IWorld world, BlockPos source, Direction direction, Direction targetDirection)
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
        MIDDLE("middle"),
        CORNER_LEFT("corner_left"),
        CORNER_RIGHT("corner_right");

        private final String id;

        Type(String id)
        {
            this.id = id;
        }

        @Override
        public String getName()
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
