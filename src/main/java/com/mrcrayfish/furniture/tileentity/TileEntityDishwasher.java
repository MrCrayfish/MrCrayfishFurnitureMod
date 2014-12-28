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

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class TileEntityDishwasher extends TileEntity implements ISidedInventory, IUpdatePlayerListBox
{
	private ItemStack[] inventory = new ItemStack[7];
	private String customName;

	private boolean washing = false;
	public boolean superMode = false;
	public int progress = 0;
	public int timeRemaining = 0;

	public void startWashing()
	{
		if (canWash())
		{
			if (timeRemaining == 0)
			{
				if (inventory[6].getItem() == FurnitureItems.itemSuperSoapyWater)
				{
					superMode = true;
				}
				else
				{
					superMode = false;
				}
				inventory[6] = new ItemStack(inventory[6].getItem().getContainerItem());
				timeRemaining = 5000;
			}
			washing = true;
		}
	}

	public void stopWashing()
	{
		progress = 0;
		washing = false;
	}

	public boolean canWash()
	{
		if (inventory[6] == null && timeRemaining == 0)
		{
			return false;
		}

		if (inventory[6] != null && timeRemaining == 0)
		{
			if (inventory[6].getItem() != FurnitureItems.itemSoapyWater)
			{
				if (inventory[6].getItem() != FurnitureItems.itemSuperSoapyWater)
				{
					return false;
				}
			}
		}

		boolean flag = false;
		for (int i = 0; i < 6; i++)
		{
			if (inventory[i] != null)
			{
				RecipeData data = RecipeAPI.getDishwasherRecipeFromInput(inventory[i]);
				if (data == null)
				{
					return false;
				}
				else
				{
					flag = true;
				}
			}
		}
		return flag;
	}

	public boolean isWashing()
	{
		return washing;
	}

	private Random rand = new Random();
	private int timer = 0;

	@Override
	public void update()
	{
		if (washing)
		{
			if (canRepair())
			{
				for (int i = 0; i < 6; i++)
				{
					if (inventory[i] != null)
					{
						if (inventory[i].getMaxDamage() - inventory[i].getItemDamage() != inventory[i].getMaxDamage())
						{
							inventory[i].setItemDamage(inventory[i].getItemDamage() - 1);
						}
					}
				}
			}

			timeRemaining--;
			if (timeRemaining <= 0)
			{
				if (inventory[4] != null)
				{
					if (inventory[4].getItem() == FurnitureItems.itemSoapyWater)
					{
						this.superMode = false;
						inventory[4] = new ItemStack(FurnitureItems.itemSoapyWater.getContainerItem());
						timeRemaining = 5000;
					}
					else if (inventory[4].getItem() == FurnitureItems.itemSuperSoapyWater)
					{
						this.superMode = true;
						inventory[4] = new ItemStack(FurnitureItems.itemSuperSoapyWater.getContainerItem());
						timeRemaining = 5000;
					}
				}
				else
				{
					timeRemaining = 0;
					progress = 0;
					washing = false;
				}
			}

			progress++;

			if (timer == 20)
			{
				timer = 0;
			}
			if (timer == 0)
			{
				worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:dishwasher", 0.75F, 1.0F);
			}
			timer++;
		}
	}

	public boolean canRepair()
	{
		return progress % (superMode ? 20 : 50) == 0;
	}

	@Override
	public int getSizeInventory()
	{
		return 7;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int number)
	{
		if (this.inventory[slot] != null)
		{
			ItemStack itemstack;

			if (this.inventory[slot].stackSize <= number)
			{
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			}
			itemstack = this.inventory[slot].splitStack(number);

			if (this.inventory[slot].stackSize == 0)
			{
				this.inventory[slot] = null;
			}

			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack par2ItemStack)
	{
		this.inventory[slot] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
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
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		if (tagCompound.hasKey("Items"))
		{
			NBTTagList tagList = (NBTTagList) tagCompound.getTag("Items");
			this.inventory = new ItemStack[7];

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound nbt = (NBTTagCompound) tagList.getCompoundTagAt(i);
				byte slot = nbt.getByte("Slot");

				if (slot >= 0 && slot < this.inventory.length)
				{
					this.inventory[slot] = ItemStack.loadItemStackFromNBT(nbt);
				}
			}
		}
		this.washing = tagCompound.getBoolean("Washing");
		this.superMode = tagCompound.getBoolean("SuperMode");
		this.progress = tagCompound.getInteger("Progress");
		this.timeRemaining = tagCompound.getInteger("Remaining");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();
		for (int slot = 0; slot < this.inventory.length; ++slot)
		{
			if (this.inventory[slot] != null)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) slot);
				this.inventory[slot].writeToNBT(nbt);
				tagList.appendTag(nbt);
			}
		}
		tagCompound.setTag("Items", tagList);
		tagCompound.setBoolean("Washing", washing);
		tagCompound.setBoolean("SuperMode", superMode);
		tagCompound.setInteger("Progress", progress);
		tagCompound.setInteger("Remaining", timeRemaining);
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
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return false;
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
		
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "Dishwasher";
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
	public int[] getSlotsForFace(EnumFacing side)
	{
		return null;
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
