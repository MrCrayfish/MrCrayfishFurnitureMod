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

import com.mrcrayfish.furniture.gui.containers.ContainerBedsideCabinet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class TileEntityBedsideCabinet extends TileEntityLockable implements IInventory
{
	private ItemStack[] bedsideCabinetContents = new ItemStack[9];
	private String customName;

	public int getSizeInventory()
	{
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.bedsideCabinetContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.bedsideCabinetContents[par1] != null)
		{
			ItemStack var3;

			if (this.bedsideCabinetContents[par1].stackSize <= par2)
			{
				var3 = this.bedsideCabinetContents[par1];
				this.bedsideCabinetContents[par1] = null;
				this.markDirty();
				return var3;
			}
			var3 = this.bedsideCabinetContents[par1].splitStack(par2);

			if (this.bedsideCabinetContents[par1].stackSize == 0)
			{
				this.bedsideCabinetContents[par1] = null;
			}

			this.markDirty();
			return var3;
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.bedsideCabinetContents[par1] != null)
		{
			ItemStack var2 = this.bedsideCabinetContents[par1];
			this.bedsideCabinetContents[par1] = null;
			return var2;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.bedsideCabinetContents[par1] = par2ItemStack;

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
		NBTTagList var2 = (NBTTagList) par1NBTTagCompound.getTag("cabinetItems");
		this.bedsideCabinetContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(var3);
			int var5 = var4.getByte("cabinetSlot") & 255;

			if (var5 >= 0 && var5 < this.bedsideCabinetContents.length)
			{
				this.bedsideCabinetContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.bedsideCabinetContents.length; ++var3)
		{
			if (this.bedsideCabinetContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("cabinetSlot", (byte) var3);
				this.bedsideCabinetContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("cabinetItems", var2);
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

	@Override
	public void invalidate()
	{
		this.updateContainingBlockInfo();
		super.invalidate();
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "Bed Drawer";
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null;
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
		for (int i = 0; i < bedsideCabinetContents.length; i++)
		{
			bedsideCabinetContents[i] = null;
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerBedsideCabinet(playerInventory, this);
	}

	@Override
	public String getGuiID() 
	{
		return "0";
	}
}
