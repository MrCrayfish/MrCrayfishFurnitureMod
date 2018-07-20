package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.StateHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Author: MrCrayfish
 */
public class BlockDesk extends BlockFurniture implements IDesk
{
    public static final PropertyEnum<DeskType> TYPE = PropertyEnum.create("type", DeskType.class);

    public BlockDesk(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, DeskType.NONE));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) instanceof IDesk)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
            {
                return state.withProperty(TYPE, DeskType.CORNER_RIGHT);
            }
            else if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
            {
                return state.withProperty(TYPE, DeskType.CORNER_LEFT);
            }
        }

        boolean left = false;
        boolean right = false;

        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) instanceof IDesk)
        {
            left = true;
        }
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) instanceof IDesk)
        {
            right = true;
        }
        if(left && !right)
        {
            return state.withProperty(TYPE, DeskType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, DeskType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, DeskType.NONE);
        }
        return state.withProperty(TYPE, DeskType.BOTH);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    public enum DeskType implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH,
        CORNER_LEFT,
        CORNER_RIGHT;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }
    }
}
