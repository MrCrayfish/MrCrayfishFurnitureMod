package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

public abstract class BlockFirePit extends Block
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);

    private static final AxisAlignedBB BOUNDING_BOX = new Bounds(0, 0, 0, 16, 3, 16).toAABB();

    public BlockFirePit(Material materialIn)
    {
        super(materialIn);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(1.0F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return isBurning() ? 0 : state.getValue(STAGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return isBurning() ? this.getDefaultState() : this.getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isBurning() ? super.createBlockState() : new BlockStateContainer(this, STAGE);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureItems.LOG);
    }

    public abstract boolean isBurning();

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
