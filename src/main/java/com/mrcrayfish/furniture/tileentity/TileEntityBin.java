package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerBin;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.SoundCategory;

public class TileEntityBin extends TileEntityFurniture
{
    public TileEntityBin()
    {
        super("bin", 12);
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.bin_open, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.bin_close, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerBin(playerInventory, this);
    }
}
