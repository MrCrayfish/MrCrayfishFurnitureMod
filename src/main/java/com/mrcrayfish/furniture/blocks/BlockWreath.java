package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWreath extends BlockFurniture
{
    private static final AxisAlignedBB[] WREATH_BOTTOM = new Bounds(2, 2, 0, 14, 5, 3).getRotatedBounds();
    private static final AxisAlignedBB[] WREATH_TOP = new Bounds(2, 11, 0, 14, 14, 3).getRotatedBounds();
    private static final AxisAlignedBB[] WREATH_LEFT = new Bounds(2, 5, 0, 5, 11, 3).getRotatedBounds();
    private static final AxisAlignedBB[] WREATH_RIGHT = new Bounds(11, 5, 0, 14, 11, 3).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_1 = new Bounds(2.5, 7.5, 2.5, 3.5, 8.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_2 = new Bounds(3.5, 10, 2.5, 4.5, 11, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_3 = new Bounds(5.5, 11.5, 2.5, 6.5, 12.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_4 = new Bounds(8, 12.5, 2.5, 9, 13.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_5 = new Bounds(10, 11.5, 2.5, 11, 12.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_6 = new Bounds(12, 10, 2.5, 13, 11, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_7 = new Bounds(12.5, 7, 2.5, 13.5, 8, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_8 = new Bounds(12.5, 7, 2.5, 13.5, 8, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_9 = new Bounds(10.5, 4, 2.5, 11.5, 5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_10 = new Bounds(8, 3, 2.5, 9, 4, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_11 = new Bounds(5, 3.5, 2.5, 6, 4.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] DECO_12 = new Bounds(3, 5.5, 2.5, 4, 6.5, 3.5).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(WREATH_BOTTOM, WREATH_TOP, WREATH_LEFT, WREATH_RIGHT, DECO_1, DECO_2, DECO_3, DECO_4, DECO_5, DECO_6, DECO_7, DECO_8, DECO_9, DECO_10, DECO_11, DECO_12);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public BlockWreath(Material materialIn)
    {
        super(materialIn);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(this.canPlaceCheck(worldIn, pos, state))
        {
            EnumFacing enumfacing = state.getValue(FACING);

            if(!worldIn.getBlockState(pos.offset(enumfacing)).isNormalCube())
            {
                this.breakBlock(worldIn, pos, state);
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return side.getHorizontalIndex() != -1;
    }


    private boolean canPlaceCheck(World world, BlockPos pos, IBlockState state)
    {
        EnumFacing enumfacing = state.getValue(FACING);
        if(!this.canPlaceBlockOnSide(world, pos, enumfacing))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
