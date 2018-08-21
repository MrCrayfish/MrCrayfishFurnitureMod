package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerCabinet;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.SoundCategory;

public class TileEntityCabinetKitchen extends TileEntityFurniture
{
    public TileEntityCabinetKitchen()
    {
        super("cabinet_kitchen", 15);
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.cabinet_close, SoundCategory.BLOCKS, 0.75F, 0.9F, true);
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.cabinet_close, SoundCategory.BLOCKS, 0.75F, 0.8F, true);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerCabinet(playerInventory, this);
    }
}
