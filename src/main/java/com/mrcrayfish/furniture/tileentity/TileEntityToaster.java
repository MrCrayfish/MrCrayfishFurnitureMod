/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

public class TileEntityToaster extends TileEntity implements IUpdatePlayerListBox, ISimpleInventory
{
	public ItemStack[] slots = new ItemStack[2];

	private int toastingTime = 0;
	private boolean toasting = false;

	public boolean addSlice(ItemStack item)
	{
		for (int i = 0; i < slots.length; i++)
		{
			if (slots[i] == null)
			{
				slots[i] = item.copy();
				return true;
			}
		}
		return false;
	}

	public void removeSlice()
	{
		for (int i = 0; i < slots.length; i++)
		{
			if (slots[i] != null)
			{
				if (!worldObj.isRemote)
				{
					EntityItem entityItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, slots[i]);
					worldObj.spawnEntityInWorld(entityItem);
				}
				slots[i] = null;
				return;
			}
		}
		worldObj.markBlockForUpdate(pos);
	}

	public void startToasting()
	{
		this.toasting = true;
	}

	public boolean isToasting()
	{
		return toasting;
	}

	public ItemStack getSlice(int slot)
	{
		return slots[slot];
	}

	@Override
	public void update()
	{
		if (toasting)
		{
			if (toastingTime == 200)
			{
				for (int i = 0; i < slots.length; i++)
				{
					if (slots[i] != null)
					{
						if (!worldObj.isRemote)
						{
							RecipeData data = RecipeAPI.getToasterRecipeFromInput(slots[i]);
							EntityItem entityItem = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, data.getOutput().copy());
							worldObj.spawnEntityInWorld(entityItem);
						}
						slots[i] = null;
					}
				}
				if (!worldObj.isRemote)
				{
					worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:toaster_down", 0.75F, 1.0F);
				}
				toastingTime = 0;
				toasting = false;
				worldObj.updateComparatorOutputLevel(pos, blockType);
			}
			else
			{
				toastingTime++;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = (NBTTagList) par1NBTTagCompound.getTag("Items");
		this.slots = new ItemStack[2];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.slots.length)
			{
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.toastingTime = par1NBTTagCompound.getInteger("ToastTime");
		this.toasting = par1NBTTagCompound.getBoolean("Toasting");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.slots.length; ++i)
		{
			if (this.slots[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		par1NBTTagCompound.setTag("Items", nbttaglist);
		par1NBTTagCompound.setInteger("ToastTime", this.toastingTime);
		par1NBTTagCompound.setBoolean("Toasting", toasting);
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
		return 2;
	}

	@Override
	public ItemStack getItem(int i)
	{
		return slots[i];
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < slots.length; i++)
		{
			slots[i] = null;
		}

	}
}
