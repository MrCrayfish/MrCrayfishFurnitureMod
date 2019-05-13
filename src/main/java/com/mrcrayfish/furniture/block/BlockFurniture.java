package com.mrcrayfish.furniture.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class BlockFurniture extends Block
{
    public BlockFurniture(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getOpacity(IBlockState state, IBlockReader reader, BlockPos pos)
    {
        return 0;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        if(this.hasTileEntity(state))
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            return Container.calcRedstone(tileEntity);
        }
        return super.getComparatorInputOverride(state, world, pos);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return this.hasTileEntity(state);
    }

    @Override
    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean isMoving)
    {
        if(this.hasTileEntity(state))
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof IInventory)
            {
                InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
            }
            /*if(tileEntity instanceof ISimpleInventory) //TODO look into removing
            {
                InventoryUtil.dropInventoryItems(world, pos, (ISimpleInventory) tileEntity);
            }*/
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int type)
    {
        if(this.hasTileEntity(state))
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            return tileEntity != null && tileEntity.receiveClientEvent(id, type);
        }
        return false;
    }
}
