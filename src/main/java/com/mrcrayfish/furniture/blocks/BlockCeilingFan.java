package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCeilingFan;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockCeilingFan extends BlockCollisionRaytrace implements IPowered
{
    private static final AxisAlignedBB BASE = new Bounds(6, 14, 6, 10, 16, 10).toAABB();
    private static final AxisAlignedBB CONNECTION = new Bounds(7.2, 9, 7.2, 8.8, 14, 8.8).toAABB();
    private static final AxisAlignedBB HEAD = new Bounds(6, 6, 6, 10, 9, 10).toAABB();
    private static final AxisAlignedBB BLADE_BASE = new Bounds(4.3, 6.5, 4.3, 11.7, 8.5, 11.7).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(BASE, CONNECTION, HEAD, BLADE_BASE);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    public BlockCeilingFan()
    {
        super(Material.WOOD);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityCeilingFan)
            {
                TileEntityCeilingFan tileEntityCeilingFan = (TileEntityCeilingFan) tileEntity;
                tileEntityCeilingFan.setPowered(!tileEntityCeilingFan.isPowered());
            }
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityCeilingFan();
    }

    @Override
    public void setPowered(World world, BlockPos pos, boolean powered)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityCeilingFan)
        {
            TileEntityCeilingFan tileEntityCeilingFan = (TileEntityCeilingFan) tileEntity;
            tileEntityCeilingFan.setPowered(powered);
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN && world.isSideSolid(pos.offset(side.getOpposite()), side, true);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
