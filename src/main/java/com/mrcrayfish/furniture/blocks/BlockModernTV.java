package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockModernTV extends BlockAbstractTV
{
    public static final PropertyBool MOUNTED = PropertyBool.create("mounted");

    private static final AxisAlignedBB[] SELECTION_BOX = new Bounds(6, 0, -5, 11, 18, 21).getRotatedBounds();
    private static final AxisAlignedBB[] SELECTION_BOX_MOUNTED = new Bounds(12, 2, -5, 16, 18, 21).getRotatedBounds();

    private static final AxisAlignedBB[] COLLISION_BOX = new Bounds(7, 0, -4, 10, 17, 20).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOX_MOUNTED = new Bounds(13, 3, -4, 15, 17, 20).getRotatedBounds();

    public BlockModernTV()
    {
        super(Material.ANVIL, 22, 12, 4, -0.35);
        this.setUnlocalizedName("modern_tv");
        this.setRegistryName("modern_tv");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MOUNTED, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        if(state.getValue(MOUNTED))
        {
            return SELECTION_BOX_MOUNTED[facing.getHorizontalIndex()];
        }
        return SELECTION_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);
        if(state.getValue(MOUNTED))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_MOUNTED[facing.getHorizontalIndex()]);
        }
        else
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX[facing.getHorizontalIndex()]);
        }
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