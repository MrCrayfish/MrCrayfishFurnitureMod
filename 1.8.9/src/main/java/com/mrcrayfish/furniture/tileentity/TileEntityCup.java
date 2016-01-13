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

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCup extends TileEntity implements ISimpleInventory
{
	private ItemStack item = null;
	public int red, green, blue;

	public void setItem(ItemStack item)
	{
		this.item = item.copy();
	}

	public void setColour(int[] rgb)
	{
		this.red = rgb[0];
		this.green = rgb[1];
		this.blue = rgb[2];
	}

	public ItemStack getDrink()
	{
		return item;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		if (tagCompound.hasKey("Item"))
		{
			NBTTagList tagList = (NBTTagList) tagCompound.getTag("Item");
			if (tagList.tagCount() > 0)
			{
				this.item = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
			}
		}
		this.red = tagCompound.getInteger("Red");
		this.green = tagCompound.getInteger("Green");
		this.blue = tagCompound.getInteger("Blue");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();
		NBTTagCompound nbt = new NBTTagCompound();
		if (item != null)
		{
			item.writeToNBT(nbt);
			tagList.appendTag(nbt);
		}
		tagCompound.setTag("Item", tagList);
		tagCompound.setInteger("Red", red);
		tagCompound.setInteger("Green", green);
		tagCompound.setInteger("Blue", blue);
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
	public int getSize()
	{
		return 1;
	}

	@Override
	public ItemStack getItem(int i)
	{
		return getDrink();
	}

	@Override
	public void clear()
	{
		item = null;
	}
}
