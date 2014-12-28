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
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

public class TileEntityOven extends TileEntity implements ISidedInventory, IUpdatePlayerListBox
{
	private static final int[] field_102010_d = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final int[] field_102011_e = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	private ItemStack[] ovenItemStacks = new ItemStack[18];
	private String customName;
	public int ovenCookTime = 0;
	public int currentItemCookTime = 0;
	public int ovenCookingTime = 0;
	private String field_94130_e;

	@Override
	public int getSizeInventory()
	{
		return this.ovenItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.ovenItemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.ovenItemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (this.ovenItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.ovenItemStacks[par1];
				this.ovenItemStacks[par1] = null;
				return itemstack;
			}
			itemstack = this.ovenItemStacks[par1].splitStack(par2);

			if (this.ovenItemStacks[par1].stackSize == 0)
			{
				this.ovenItemStacks[par1] = null;
			}

			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.ovenItemStacks[par1] != null)
		{
			ItemStack itemstack = this.ovenItemStacks[par1];
			this.ovenItemStacks[par1] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.ovenItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = (NBTTagList) par1NBTTagCompound.getTag("Items");
		this.ovenItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.ovenItemStacks.length)
			{
				this.ovenItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.ovenCookTime = par1NBTTagCompound.getShort("BurnTime");
		this.ovenCookingTime = par1NBTTagCompound.getShort("CookTime");
		this.currentItemCookTime = getItemBurnTime(this.ovenItemStacks[1]);

		if (par1NBTTagCompound.hasKey("CustomName"))
		{
			this.field_94130_e = par1NBTTagCompound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short) this.ovenCookTime);
		par1NBTTagCompound.setShort("CookTime", (short) this.ovenCookingTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.ovenItemStacks.length; ++i)
		{
			if (this.ovenItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.ovenItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.ovenCookingTime * par1 / 600;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.currentItemCookTime == 0)
		{
			this.currentItemCookTime = 600;
		}

		return this.ovenCookTime * par1 / this.currentItemCookTime;
	}

	public boolean isBurning()
	{
		return this.ovenCookTime > 0;
	}

	private Random rand = new Random();

	@Override
	public void update()
	{
		boolean flag = this.ovenCookTime > 0;
		boolean flag1 = false;

		if (this.ovenCookTime > 0)
		{
			--this.ovenCookTime;
		}

		if (!this.worldObj.isRemote)
		{
			int itemNum = canSmelt();
			if (itemNum != -1)
			{
				++this.ovenCookingTime;

				if (this.ovenCookingTime == 600)
				{
					this.ovenCookingTime = 0;
					this.cookItems();
					flag1 = true;
				}
			}
			else
			{
				this.ovenCookingTime = 0;
			}

			if (flag != this.ovenCookTime > 0)
			{
				flag1 = true;
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	private int canSmelt()
	{
		boolean hasItem = false;
		int itemNum = -1;
		for (int x = 0; x < 9; x++)
		{
			if (this.ovenItemStacks[x] != null)
			{
				hasItem = true;
				itemNum = x;
				break;
			}
		}

		if (hasItem)
		{
			RecipeData data = Recipes.getOvenRecipeFromInput(ovenItemStacks[itemNum]);

			if (data == null)
			{
				return -1;
			}

			if (ovenItemStacks[itemNum + 9] == null)
			{
				return itemNum;
			}

			if (ovenItemStacks[itemNum + 9].getItem() != data.getOutput().getItem())
				return -1;

			if (ovenItemStacks[itemNum + 9].stackSize < this.getInventoryStackLimit() && ovenItemStacks[itemNum + 9].stackSize < ovenItemStacks[itemNum + 9].getMaxStackSize())
			{
				return itemNum;
			}

			if (ovenItemStacks[itemNum + 9].stackSize < data.getOutput().getMaxStackSize())
			{
				return itemNum;
			}
			else
			{
				return -1;
			}
		}
		return -1;
	}

	public void cookItems()
	{
		int itemNum = canSmelt();
		if (itemNum != -1)
		{
			RecipeData data = Recipes.getOvenRecipeFromInput(ovenItemStacks[itemNum]);

			if (data == null)
			{
				return;
			}

			if (ovenItemStacks[itemNum + 9] == null)
			{
				ovenItemStacks[itemNum + 9] = data.getOutput().copy();
			}
			else if (ovenItemStacks[itemNum + 9].getItem() == data.getOutput().getItem() && ovenItemStacks[itemNum + 9].getItemDamage() == data.getOutput().getItemDamage())
			{
				ovenItemStacks[itemNum + 9].stackSize += data.getOutput().copy().stackSize;
			}

			if (ovenItemStacks[itemNum].getItem().hasContainerItem())
			{
				ovenItemStacks[itemNum] = new ItemStack(ovenItemStacks[itemNum].getItem().getContainerItem());
			}
			else
			{
				ovenItemStacks[itemNum].stackSize--;
			}

			if (ovenItemStacks[itemNum].stackSize <= 0)
			{
				ovenItemStacks[itemNum] = null;
			}
		}
	}

	public static int getItemBurnTime(ItemStack par0ItemStack)
	{
		return 0;
	}

	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemBurnTime(par0ItemStack) > 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(pos) != this ? false : entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i > 8 ? false : true;
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
		return hasCustomName() ? customName : "Cabinet";
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
		return side == EnumFacing.DOWN ? field_102011_e : field_102010_d;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isStackValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return index > 8;
	}
}
