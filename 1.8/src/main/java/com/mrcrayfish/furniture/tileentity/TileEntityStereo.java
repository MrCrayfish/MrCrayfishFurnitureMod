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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStereo extends TileEntity
{
	private ItemStack record;
	public int count;

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("RecordItem"))
		{
			this.setRecord(ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("RecordItem")));
		}
		else if (par1NBTTagCompound.getInteger("Record") > 0)
		{
			this.setRecord(new ItemStack(Item.getItemById(par1NBTTagCompound.getInteger("Record")), 1, 0));
		}

		this.count = par1NBTTagCompound.getInteger("count");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		if (this.getRecordStack() != null)
		{
			par1NBTTagCompound.setTag("RecordItem", this.getRecordStack().writeToNBT(new NBTTagCompound()));
			par1NBTTagCompound.setInteger("Record", Item.getIdFromItem(this.getRecordStack().getItem()));
		}

		par1NBTTagCompound.setInteger("count", count);
	}

	public ItemStack getRecordStack()
	{
		return this.record;
	}

	public void setRecord(ItemStack par1ItemStack)
	{
		this.record = par1ItemStack;
		this.markDirty();
	}
}
