package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockTVStand extends BlockFurniture
{
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockTVStand()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("tv_stand");
        this.setRegistryName("tv_stand");
        this.setHardness(0.5F);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        boolean left = false;
        boolean right = false;

        IBlockState rightState = worldIn.getBlockState(pos.offset(facing.rotateY()));
        if(rightState.getBlock() == this && rightState.getValue(FACING) == facing)
        {
            right = true;
        }
        IBlockState leftState = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
        if(leftState.getBlock() == this && leftState.getValue(FACING) == facing)
        {
            left = true;
        }

        if(left && right)
        {
            state = state.withProperty(TYPE, Type.BOTH);
        }
        else if(left)
        {
            state = state.withProperty(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            state = state.withProperty(TYPE, Type.LEFT);
        }
        else
        {
            state = state.withProperty(TYPE, Type.NONE);
        }
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public enum Type implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.US);
        }
    }
}
