/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class TileEntityMailBox extends TileEntity implements IInventory
{
	public ItemStack[] mailBoxContents = new ItemStack[6];
	public String ownerUUID = null;
	public String ownerName = "";
	public boolean locked = true;

	@Override
	public int getSizeInventory()
	{
		return 6;
	}

	public void setOwner(EntityPlayer player)
	{
		this.ownerUUID = player.getUniqueID().toString();
		this.ownerName = player.getName();
	}

	public void tryAndUpdateName(EntityPlayer player)
	{
		if (ownerUUID != null && ownerUUID.equalsIgnoreCase(player.getUniqueID().toString()))
		{
			if (!ownerName.equalsIgnoreCase(player.getName()))
			{
				ownerName = player.getName();
			}
		}
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.mailBoxContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.mailBoxContents[par1] != null)
		{
			ItemStack var3;

			if (this.mailBoxContents[par1].stackSize <= par2)
			{
				var3 = this.mailBoxContents[par1];
				this.mailBoxContents[par1] = null;
				this.markDirty();
				return var3;
			}
			var3 = this.mailBoxContents[par1].splitStack(par2);

			if (this.mailBoxContents[par1].stackSize == 0)
			{
				this.mailBoxContents[par1] = null;
			}

			this.markDirty();
			return var3;
		}
		return null;
	}

	public int getMailCount()
	{
		int count = 0;
		for (int i = 0; i < this.getSizeInventory(); i++)
		{
			if (mailBoxContents[i] != null)
			{
				count++;
			}
		}
		return count;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.mailBoxContents[par1] != null)
		{
			ItemStack var2 = this.mailBoxContents[par1];
			this.mailBoxContents[par1] = null;
			return var2;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.mailBoxContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = (NBTTagList) par1NBTTagCompound.getTag("mailBoxItems");
		if (par1NBTTagCompound.hasKey("OwnerUUID") && par1NBTTagCompound.hasKey("OwnerName"))
		{
			this.ownerUUID = par1NBTTagCompound.getString("OwnerUUID");
			this.ownerName = par1NBTTagCompound.getString("OwnerName");
		}
		this.locked = par1NBTTagCompound.getBoolean("Locked");
		this.mailBoxContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(var3);
			int var5 = var4.getByte("mailBoxSlot") & 255;

			if (var5 >= 0 && var5 < this.mailBoxContents.length)
			{
				this.mailBoxContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();
		if (ownerUUID != null && ownerName != null)
		{
			par1NBTTagCompound.setString("OwnerUUID", ownerUUID.toString());
			par1NBTTagCompound.setString("OwnerName", ownerName);
		}
		par1NBTTagCompound.setBoolean("Locked", locked);

		for (int var3 = 0; var3 < this.mailBoxContents.length; ++var3)
		{
			if (this.mailBoxContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("mailBoxSlot", (byte) var3);
				this.mailBoxContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("mailBoxItems", var2);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(pos) != this ? false : entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	public boolean canOpen(TileEntityMailBox mailBox, EntityPlayer player)
	{
		return mailBox.ownerUUID.equalsIgnoreCase(player.getUniqueID().toString());
	}

	public void addMail(ItemStack itemStack)
	{
		for (int i = 0; i < 6; i++)
		{
			if (mailBoxContents[i] == null)
			{
				mailBoxContents[i] = itemStack;
				break;
			}
		}
		this.markDirty();
	}

	public boolean isMailBoxFull()
	{
		for (int i = 0; i < 6; i++)
		{
			if (mailBoxContents[i] == null)
			{
				return false;
			}
		}
		return true;
	}

	public void setContents(int slotNumber, ItemStack itemStack)
	{
		mailBoxContents[slotNumber] = itemStack;
		this.markDirty();
	}

	@Override
	public void invalidate()
	{
		this.updateContainingBlockInfo();
		super.invalidate();
	}

	public String getOwner()
	{
		if (this.ownerName == null)
			return "null";
		return this.ownerName;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public String getName()
	{
		return "Mailbox";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(getName());
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < mailBoxContents.length; i++)
		{
			mailBoxContents[i] = null;
		}
	}
}
