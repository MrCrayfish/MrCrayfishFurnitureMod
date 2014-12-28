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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class TileEntityPrinter extends TileEntity implements ISidedInventory, IUpdatePlayerListBox
{
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };

	private ItemStack[] printerItemStacks = new ItemStack[3];
	private String customName;
	public int printerPrintTime;
	public int currentItemPrintTime;
	public int printerPrintingTime;

	@Override
	public int getSizeInventory()
	{
		return this.printerItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.printerItemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.printerItemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (this.printerItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.printerItemStacks[par1];
				this.printerItemStacks[par1] = null;
				return itemstack;
			}
			itemstack = this.printerItemStacks[par1].splitStack(par2);

			if (this.printerItemStacks[par1].stackSize == 0)
			{
				this.printerItemStacks[par1] = null;
			}

			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.printerItemStacks[par1] != null)
		{
			ItemStack itemstack = this.printerItemStacks[par1];
			this.printerItemStacks[par1] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.printerItemStacks[par1] = par2ItemStack;

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
		this.printerItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.printerItemStacks.length)
			{
				this.printerItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.printerPrintTime = par1NBTTagCompound.getShort("PrintTime");
		this.printerPrintingTime = par1NBTTagCompound.getShort("PrintingTime");
		this.currentItemPrintTime = getItemPrintTime(this.printerItemStacks[1]);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("PrintTime", (short) this.printerPrintTime);
		par1NBTTagCompound.setShort("PrintingTime", (short) this.printerPrintingTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.printerItemStacks.length; ++i)
		{
			if (this.printerItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.printerItemStacks[i].writeToNBT(nbttagcompound1);
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
	public int getPrintingProgressScaled(int par1)
	{
		if (printerItemStacks[0] != null)
		{
			if (printerItemStacks[0].getItem() == Items.enchanted_book)
			{
				return this.printerPrintingTime * par1 / 10000;
			}
			return this.printerPrintingTime * par1 / 1000;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public int getPrintTimeRemainingScaled(int par1)
	{
		if (printerItemStacks[0] != null)
		{
			if (printerItemStacks[0].getItem() == Items.enchanted_book)
			{
				if (this.currentItemPrintTime == 0)
				{
					this.currentItemPrintTime = 10000;
				}
			}
			else
			{
				if (this.currentItemPrintTime == 0)
				{
					this.currentItemPrintTime = 1000;
				}
			}
		}
		else
		{
			if (this.currentItemPrintTime == 0)
			{
				this.currentItemPrintTime = 1000;
			}
		}
		return this.printerPrintTime * par1 / this.currentItemPrintTime;
	}

	public boolean isPrinting()
	{
		return this.printerPrintTime > 0;
	}

	@Override
	public void update()
	{
		boolean flag1 = false;

		if (this.printerPrintTime > 0)
		{
			--this.printerPrintTime;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.printerPrintTime == 0 && this.canPrint())
			{
				this.currentItemPrintTime = this.printerPrintTime = getItemPrintTime(this.printerItemStacks[1]);

				if (this.printerPrintTime > 0)
				{
					flag1 = true;

					if (this.printerItemStacks[1] != null)
					{
						--this.printerItemStacks[1].stackSize;

						if (this.printerItemStacks[1].stackSize == 0)
						{
							this.printerItemStacks[1] = this.printerItemStacks[1].getItem().getContainerItem(printerItemStacks[1]);
						}
					}
				}
			}

			if (this.isPrinting() && this.canPrint())
			{
				++this.printerPrintingTime;
				if (printerItemStacks[0] != null && printerItemStacks[0].getItem() == Items.enchanted_book)
				{
					if (this.printerPrintingTime == 10000)
					{
						this.printerPrintingTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				}
				else
				{
					if (this.printerPrintingTime == 1000)
					{
						this.printerPrintingTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				}
			}
			else
			{
				this.printerPrintingTime = 0;
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	private boolean canPrint()
	{
		if (this.printerItemStacks[0] == null)
		{
			return false;
		}
		if (RecipeAPI.getPrinterRecipeFromInput(printerItemStacks[0]) != null)
		{
			if (this.printerItemStacks[2] == null)
				return true;
		}
		return false;
	}

	public void smeltItem()
	{
		if (this.canPrint())
		{
			ItemStack itemstack = this.printerItemStacks[0];
			if (this.printerItemStacks[2] == null)
			{
				this.printerItemStacks[2] = itemstack.copy();
			}
		}
	}

	public static int getItemPrintTime(ItemStack par0ItemStack)
	{
		if (par0ItemStack == null)
		{
			return 0;
		}
		Item i = par0ItemStack.getItem();
		if (par0ItemStack.getItemDamage() == 0)
		{
			if (i == Items.dye)
				return 1000;
			if (i == FurnitureItems.itemInkCartridge)
				return 5000;
		}
		return 0;
	}

	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemPrintTime(par0ItemStack) > 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(pos) != this ? false : entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true);
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
		for (int i = 0; i < printerItemStacks.length; i++)
		{
			printerItemStacks[i] = null;
		}	
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
		return side == EnumFacing.DOWN ? slots_bottom : (side == EnumFacing.UP ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return direction == EnumFacing.DOWN;
	}
}
