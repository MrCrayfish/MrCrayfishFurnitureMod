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
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityMailBox extends TileEntity implements IInventory
{
	public ItemStack[] mailBoxContents = new ItemStack[6];
	public String ownerUUID = null;
	public String ownerName = "";

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
	public ItemStack removeStackFromSlot(int par1)
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

		if (par1NBTTagCompound.hasKey("OwnerUUID") && par1NBTTagCompound.hasKey("OwnerName"))
		{
			this.ownerUUID = par1NBTTagCompound.getString("OwnerUUID");
			this.ownerName = par1NBTTagCompound.getString("OwnerName");
		}
		
		if(par1NBTTagCompound.hasKey("mailBoxItems"))
		{
			this.mailBoxContents = new ItemStack[this.getSizeInventory()];
			NBTTagList tagList = (NBTTagList) par1NBTTagCompound.getTag("mailBoxItems");
			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound itemTag = (NBTTagCompound) tagList.getCompoundTagAt(i);
				int slot = itemTag.getByte("mailBoxSlot") & 255;

				if (slot >= 0 && slot < this.mailBoxContents.length)
				{
					this.mailBoxContents[slot] = ItemStack.loadItemStackFromNBT(itemTag);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		
		if (ownerUUID != null && ownerName != null)
		{
			tagCompound.setString("OwnerUUID", ownerUUID.toString());
			tagCompound.setString("OwnerName", ownerName);
		}

		NBTTagList tagList = new NBTTagList();
		for (int slot = 0; slot < this.mailBoxContents.length; ++slot)
		{
			if (this.mailBoxContents[slot] != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("mailBoxSlot", (byte) slot);
				this.mailBoxContents[slot].writeToNBT(itemTag);
				tagList.appendTag(itemTag);
			}
		}
		tagCompound.setTag("mailBoxItems", tagList);
		return tagCompound;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
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

	public boolean canOpen(EntityPlayer player)
	{
		return ownerUUID != null && ownerUUID.equalsIgnoreCase(player.getUniqueID().toString());
	}
	
	public boolean isClaimed()
	{
		return ownerUUID != null && !ownerName.isEmpty();
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
	public ITextComponent getDisplayName() 
	{
		return new TextComponentString(getName());
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
