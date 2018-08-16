package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerPresent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityPresent extends TileEntityFurniture implements ISidedInventory
{
    private static final int[] slots = new int[]{0}; //TODO reconsider
    public String ownerName = "Unknown";
    public boolean locked = true;

    public TileEntityPresent()
    {
        super("present", 4);
    }

    public void setOwner(String username)
    {
        this.ownerName = username;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.ownerName = tagCompound.getString("OwnerName");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setString("OwnerName", ownerName);
        return tagCompound;
    }

    public String getOwner()
    {
        if(this.ownerName == null) return "null";
        return this.ownerName;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return slots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return false;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerPresent(playerInventory, this);
    }
}
