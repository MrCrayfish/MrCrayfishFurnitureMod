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

import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputer extends TileEntityFurniture
{
	private int stockNum = 0;
	private boolean isTrading = false;
	
	public TileEntityComputer() 
	{
		super("computer", 1);
	}

	public void takeEmeraldFromSlot(int price)
	{
		this.getStackInSlot(0).shrink(price);
		this.markDirty();
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
	

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		this.stockNum = tagCompound.getInteger("StockNum");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("StockNum", stockNum);
		return tagCompound;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		setTrading(true);
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		setTrading(false);
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerComputer(playerInventory, this);
	}
}
