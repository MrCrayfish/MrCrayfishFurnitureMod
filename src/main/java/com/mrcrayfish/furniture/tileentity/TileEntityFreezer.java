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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class TileEntityFreezer extends TileEntity implements ISidedInventory, IUpdatePlayerListBox
{
	private ItemStack[] inventory = new ItemStack[3];
	public int freezeTime = 0;
	public int currentItemCoolTime = 0;
	public int coolTime = 0;
	private String customName;

	private static final int[] slots_top = new int[] { 1 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 0 };

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (inventory[i] != null)
		{
			if (inventory[i].stackSize <= j)
			{
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0)
			{
				inventory[i] = null;
			}
			return itemstack1;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inventory[par1] != null)
		{
			ItemStack itemstack = inventory[par1];
			inventory[par1] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		NBTTagList nbttaglist = (NBTTagList) par1NBTTagCompound.getTag("Items");
		inventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		coolTime = par1NBTTagCompound.getShort("CoolTime");
		freezeTime = par1NBTTagCompound.getShort("FreezeTime");
		currentItemCoolTime = getItemFreezeTime(inventory[1]);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("CoolTime", (short) coolTime);
		par1NBTTagCompound.setShort("FreezeTime", (short) freezeTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	public int getCoolProgressScaled(int i)
	{
		return (coolTime * i) / 200;
	}

	public int getFreezeTimeRemainingScaled(int i)
	{
		if (currentItemCoolTime == 0)
		{
			currentItemCoolTime = 200;
		}
		return (this.freezeTime * i) / currentItemCoolTime;
	}

	public boolean isFreezing()
	{
		return freezeTime > 0;
	}

	public void update()
	{
		boolean flag = freezeTime > 0;
		boolean flag1 = false;

		if (freezeTime > 0)
		{
			freezeTime--;
		}

		if (freezeTime == 0 && canSolidify())
		{
			currentItemCoolTime = freezeTime = getItemFreezeTime(inventory[1]);

			if (freezeTime > 0)
			{
				flag1 = true;

				if (inventory[1] != null)
				{
					inventory[1].stackSize--;

					if (inventory[1].stackSize == 0)
					{
						inventory[1] = null;
					}
				}
			}
		}

		if (isFreezing() && canSolidify())
		{
			coolTime++;

			if (coolTime == 200)
			{
				coolTime = 0;
				solidifyItem();
				flag1 = true;
			}
		}
		else
		{
			coolTime = 0;
		}

		if (flag != (freezeTime > 0))
		{
			flag1 = true;
		}
		if (flag1)
		{
			this.markDirty();
		}
	}

	private boolean canSolidify()
	{
		if (this.inventory[0] == null)
		{
			return false;
		}

		RecipeData data = Recipes.getFreezerRecipeFromInput(inventory[0]);

		if (data == null)
		{
			return false;
		}

		if (inventory[2] == null)
			return true;
		if (inventory[2].getItem() != data.getOutput().getItem())
			return false;

		if (inventory[2].stackSize < this.getInventoryStackLimit() && inventory[2].stackSize < inventory[2].getMaxStackSize())
		{
			return true;
		}
		return inventory[2].stackSize < data.getOutput().getMaxStackSize();
	}

	public void solidifyItem()
	{
		if (canSolidify())
		{
			RecipeData data = Recipes.getFreezerRecipeFromInput(inventory[0]);

			if (data == null)
			{
				return;
			}

			if (inventory[2] == null)
			{
				inventory[2] = data.getOutput().copy();
			}
			else if (inventory[2].getItem() == data.getOutput().getItem() && inventory[2].getItemDamage() == data.getOutput().getItemDamage())
			{
				inventory[2].stackSize += data.getOutput().copy().stackSize;
			}

			if (inventory[0].getItem().hasContainerItem())
			{
				inventory[0] = new ItemStack(inventory[0].getItem().getContainerItem());
			}
			else
			{
				inventory[0].stackSize--;
			}

			if (inventory[0].stackSize <= 0)
			{
				inventory[0] = null;
			}
		}
	}

	private static int getItemFreezeTime(ItemStack itemstack)
	{
		if (itemstack == null)
		{
			return 0;
		}
		Item i = itemstack.getItem();
		if (i == FurnitureItems.itemCoolPack)
		{
			return 2500;
		}
		else if (i == new ItemStack(Blocks.ice).getItem())
		{
			return 5000;
		}
		else
		{
			return 0;
		}
	}

	public static boolean isItemFuel(ItemStack itemstack)
	{
		return getItemFreezeTime(itemstack) > 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : entityplayer.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : (par1 == 1 ? false : true);
	}

	@Override
	public void openInventory(EntityPlayer playerIn)
	{

	}

	@Override
	public void closeInventory(EntityPlayer playerIn)
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

	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.fridge";
	}

	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return null;
	}

	@Override
	public boolean canInsertItem(int slotIn, ItemStack itemStackIn, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int slotId, ItemStack stack, EnumFacing direction)
	{
		return false;
	}

}
