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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityComputer extends TileEntity implements IInventory
{
	private ItemStack paySlot = null;
	private String customName;
	public int stockNum = 0;
	private boolean isTrading = false;

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return paySlot;
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.paySlot != null)
		{
			ItemStack var3;

			if (this.paySlot.stackSize <= par2)
			{
				var3 = this.paySlot;
				this.paySlot = null;
				this.markDirty();
				return var3;
			}
			var3 = this.paySlot.splitStack(par2);

			if (this.paySlot.stackSize == 0)
			{
				this.paySlot = null;
			}

			this.markDirty();
			return var3;
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.paySlot != null)
		{
			ItemStack var2 = this.paySlot;
			this.paySlot = null;
			return var2;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.paySlot = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	/**
	 * Returns the name of the inventory.
	 */
	public String getInvName()
	{
		return "Computer";
	}

	public void takeEmeraldFromSlot(int price)
	{
		if (this.paySlot != null)
		{
			this.paySlot.stackSize -= price;
		}
		this.markDirty();
	}

	public void clearInventory()
	{
		this.paySlot = null;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		this.stockNum = par1NBTTagCompound.getInteger("StockNum");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("StockNum", stockNum);
	}

	public void setBrowsingInfo(int stockNum)
	{
		this.stockNum = stockNum;
	}

	public int getBrowsingInfo()
	{
		return stockNum;
	}

	public void setTrading(boolean isUsing)
	{
		this.isTrading = isUsing;
	}

	public boolean isTrading()
	{
		return isTrading;
	}

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
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "Computer";
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null;
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
		setTrading(false);
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
		return 4;
	}

	@Override
	public void clear()
	{
		paySlot = null;
	}
}
