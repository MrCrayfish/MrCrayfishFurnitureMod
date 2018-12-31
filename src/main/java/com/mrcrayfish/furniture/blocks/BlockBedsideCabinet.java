package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityBedsideCabinet;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedsideCabinet extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] DRAWER_BOTTOM = new Bounds(0, 2, 2, 1, 7, 14).getRotatedBounds();
    private static final AxisAlignedBB[] DRAWER_TOP = new Bounds(0, 9, 2, 1, 14, 14).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_BOTTOM = new Bounds(-0.48, 4, 6, 0, 5, 10).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_TOP = new Bounds(-0.48, 11, 6, 0, 12, 10).getRotatedBounds();
    private static final AxisAlignedBB[] BODY = new Bounds(1, 1.6, 1, 15, 14.4, 15).getRotatedBounds();
    private static final AxisAlignedBB[] BASE = new Bounds(0, 0, 0, 16, 1.6, 16).getRotatedBounds();
    private static final AxisAlignedBB[] TOP = new Bounds(0, 14.4, 0, 16, 16, 16).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, DRAWER_BOTTOM, DRAWER_TOP, HANDLE_BOTTOM, HANDLE_TOP, BODY, BASE, TOP);

    public BlockBedsideCabinet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
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
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityBedsideCabinet)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(blockIn instanceof BlockLamp)
        {
            worldIn.notifyNeighborsOfStateChange(pos.down(), this, true);
        }
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if(blockAccess.getBlockState(pos.up()).getBlock() == FurnitureBlocks.LAMP_ON)
        {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBedsideCabinet();
    }
}
