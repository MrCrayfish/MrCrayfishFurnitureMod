package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockModernTV extends BlockAbstractTV
{
    public static final PropertyBool MOUNTED = PropertyBool.create("mounted");

    public BlockModernTV()
    {
        super(Material.ANVIL, 22, 12, 4, -0.35);
        this.setUnlocalizedName("modern_tv");
        this.setRegistryName("modern_tv");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MOUNTED, false));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        if(facing.getHorizontalIndex() != -1)
        {
            state = state.withProperty(MOUNTED, true);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(MOUNTED) ? 4 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(MOUNTED, meta / 4 > 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, MOUNTED);
    }

    @Override
    public double getScreenZOffset(IBlockState state)
    {
        if(state.getValue(MOUNTED))
        {
            return 4.65;
        }
        return super.getScreenZOffset(state);
    }
}
