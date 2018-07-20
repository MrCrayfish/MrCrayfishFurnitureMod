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
public class BlockDeskCabinet extends BlockFurniture implements IDesk
{
    public static final PropertyEnum<DeskCabinetType> TYPE = PropertyEnum.create("type", DeskCabinetType.class);

    public BlockDeskCabinet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, DeskCabinetType.NONE));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
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
            return state.withProperty(TYPE, DeskCabinetType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, DeskCabinetType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, DeskCabinetType.BOTH);
        }
        return state.withProperty(TYPE, DeskCabinetType.NONE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    public enum DeskCabinetType implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }
    }
}
