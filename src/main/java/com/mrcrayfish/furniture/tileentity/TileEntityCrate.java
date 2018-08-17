package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerCrate;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCrate extends TileEntityFurniture
{
    public boolean sealed = false;

    public TileEntityCrate()
    {
        super("crate", 16);
    }

    /**
     * Seals the crate so the player can't open unless they break it with a crow bar.
     */
    public void seal()
    {
        if(!sealed)
        {
            sealed = true;
            TileEntityUtil.markBlockForUpdate(world, pos);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.sealed = tagCompound.getBoolean("Sealed");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("Sealed", this.sealed);
        return tagCompound;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerCrate(playerInventory, this);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return super.isUsableByPlayer(player) && !sealed;
    }
}
