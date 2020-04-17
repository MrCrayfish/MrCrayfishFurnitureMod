package com.mrcrayfish.furniture.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FurnitureBlock extends Block
{
    public FurnitureBlock(Properties properties)
    {
        super(properties);
    }

    /*@Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }*/

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos)
    {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state)
    {
        return this.hasTileEntity(state);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof IInventory)
            {
                InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
                world.updateComparatorOutputLevel(pos, this);
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int type)
    {
        super.eventReceived(state, world, pos, id, type);
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity != null && tileEntity.receiveClientEvent(id, type);
    }
}
