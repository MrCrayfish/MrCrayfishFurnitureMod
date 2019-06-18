package com.mrcrayfish.furniture.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class FurnitureBlock extends Block
{
    public FurnitureBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isNormalCube(BlockState p_220081_1_, IBlockReader p_220081_2_, BlockPos p_220081_3_)
    {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return 255;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos)
    {
        if(this.hasTileEntity(state))
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            return Container.calcRedstone(tileEntity);
        }
        return super.getComparatorInputOverride(state, world, pos);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state)
    {
        return this.hasTileEntity(state);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(this.hasTileEntity(state))
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof IInventory)
            {
                InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int type)
    {
        if(this.hasTileEntity(state))
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            return tileEntity != null && tileEntity.receiveClientEvent(id, type);
        }
        return false;
    }
}
