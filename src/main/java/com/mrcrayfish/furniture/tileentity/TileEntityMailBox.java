/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerMailBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMailBox extends TileEntityFurniture
{
    private String ownerUUID = null;
    private String ownerName = "";

    public TileEntityMailBox()
    {
        super("mail_box", 6);
    }

    public void setOwner(EntityPlayer player)
    {
        this.ownerUUID = player.getUniqueID().toString();
        this.ownerName = player.getName();
        markDirty();
    }

    public boolean hasOwner()
    {
        return this.ownerUUID != null;
    }

    public void tryAndUpdateName(EntityPlayer player)
    {
        if(ownerUUID != null && ownerUUID.equalsIgnoreCase(player.getUniqueID().toString()))
        {
            if(!ownerName.equalsIgnoreCase(player.getName()))
            {
                ownerName = player.getName();
                markDirty();
            }
        }
    }

    public int getMailCount()
    {
        int count = 0;
        for(ItemStack stack : this.inventory)
        {
            if(stack != ItemStack.EMPTY) count++;
        }
        return count;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);

        if(tagCompound.hasKey("OwnerUUID", 8))
        {
            this.ownerUUID = tagCompound.getString("OwnerUUID");
        }

        if(tagCompound.hasKey("OwnerName", 8))
        {
            this.ownerName = tagCompound.getString("OwnerName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        if(ownerUUID != null)
        {
            tagCompound.setString("OwnerUUID", ownerUUID.toString());
        }

        if(ownerName != null)
        {
            tagCompound.setString("OwnerName", ownerName);
        }

        return tagCompound;
    }

    public boolean canOpen(EntityPlayer player)
    {
        return ownerUUID != null && ownerUUID.equalsIgnoreCase(player.getUniqueID().toString());
    }

    public boolean isClaimed()
    {
        return ownerUUID != null;
    }

    public void addMail(ItemStack stack)
    {
        for(int i = 0; i < 6; i++)
        {
            if(this.inventory.get(i).isEmpty())
            {
                setInventorySlotContents(i, stack);
                markDirty();
                break;
            }
        }
    }

    public boolean isMailBoxFull()
    {
        return !this.inventory.stream().anyMatch(ItemStack::isEmpty);
    }

    public String getOwner()
    {
        if(this.ownerName == null)
        {
            return "null";
        }
        return this.ownerName;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerMailBox(playerInventory, this);
    }
}
