package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerEski;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class TileEntityEsky extends TileEntityFurniture
{
    public TileEntityEsky()
    {
        super("cooler", 8);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerEski(playerInventory, this);
    }
}
