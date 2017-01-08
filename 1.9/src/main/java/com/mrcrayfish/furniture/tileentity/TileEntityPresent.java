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
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityPresent extends TileEntity implements ISidedInventory
{
	private static final int[] slots = new int[] { 0 };
	public ItemStack[] presentContents = new ItemStack[4];
	public String ownerName = "World";
	public boolean locked = true;

	public int getSizeInventory()
	{
		return 4;
	}

	public void setOwner(String username)
	{
		this.ownerName = username;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.presentContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.presentContents[par1] != null)
		{
			ItemStack var3;

			if (this.presentContents[par1].stackSize <= par2)
			{
				var3 = this.presentContents[par1];
				this.presentContents[par1] = null;
				this.markDirty();
				return var3;
			}
			var3 = this.presentContents[par1].splitStack(par2);

			if (this.presentContents[par1].stackSize == 0)
			{
				this.presentContents[par1] = null;
			}

			this.markDirty();
			return var3;
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.presentContents[par1] != null)
		{
			ItemStack var2 = this.presentContents[par1];
			this.presentContents[par1] = null;
			return var2;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.presentContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		this.ownerName = tagCompound.getString("OwnerName");
		
		if(tagCompound.hasKey("presentContents"))
		{
			NBTTagList var2 = (NBTTagList) tagCompound.getTag("presentContents");
			this.presentContents = new ItemStack[this.getSizeInventory()];

			for (int i = 0; i < var2.tagCount(); ++i)
			{
				NBTTagCompound itemTag = (NBTTagCompound) var2.getCompoundTagAt(i);
				int slot = itemTag.getByte("presentItem") & 255;

				if (slot >= 0 && slot < this.presentContents.length)
				{
					this.presentContents[slot] = ItemStack.loadItemStackFromNBT(itemTag);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();
		tagCompound.setString("OwnerName", ownerName);

		for (int slot = 0; slot < this.presentContents.length; ++slot)
		{
			if (this.presentContents[slot] != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("presentItem", (byte) slot);
				this.presentContents[slot].writeToNBT(itemTag);
				tagList.appendTag(itemTag);
			}
		}
		tagCompound.setTag("presentContents", tagList);
		
		return tagCompound;
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

	public void setContents(int slotNumber, ItemStack itemStack)
	{
		presentContents[slotNumber] = itemStack;
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
	public String getName()
	{
		return "Present";
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
		for (int i = 0; i < presentContents.length; i++)
		{
			presentContents[i] = null;
		}	
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
}
