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
package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotOven;
import com.mrcrayfish.furniture.init.FurnitureAchievements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOven extends Container
{
	private IInventory ovenInventory;

	public ContainerOven(IInventory playerInventory, IInventory ovenInventory)
	{
		this.ovenInventory = ovenInventory;
		for (int i = 0; i < 4; i++)
		{
			this.addSlotToContainer(new Slot(ovenInventory, i, i * 18 + 53, 31));
		}

		for (int i = 0; i < 4; i++)
		{
			this.addSlotToContainer(new SlotOven(ovenInventory, i + 4, i * 18 + 53, 73));
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 146));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 204));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.ovenInventory.isUseableByPlayer(player);
	}

	@Override
	public ItemStack slotClick(int slotNum, int clickButton, int mode, EntityPlayer player)
	{
		if (slotNum > 8 && slotNum < 18)
		{
			Slot slot = (Slot) this.inventorySlots.get(slotNum);
			if (slot != null && slot.getHasStack())
			{
				player.triggerAchievement(FurnitureAchievements.cookItem);
			}
		}
		return super.slotClick(slotNum, clickButton, mode, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		ItemStack itemCopy = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack item = slot.getStack();
			itemCopy = item.copy();

			RecipeData data = RecipeAPI.getOvenRecipeFromInput(item);

			if (slotNum < 18)
			{
				if (!this.mergeItemStack(item, 18, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (data != null)
			{
				if (!this.mergeItemStack(item, 0, 9, false))
				{
					return null;
				}
			}
			else if (slotNum >= 18 && slotNum < this.inventorySlots.size() - 9)
			{
				if (!this.mergeItemStack(item, this.inventorySlots.size() - 9, this.inventorySlots.size(), false))
				{
					return null;
				}
			}
			else if (slotNum >= this.inventorySlots.size() - 9 && slotNum < this.inventorySlots.size())
			{
				if (!this.mergeItemStack(item, 18, this.inventorySlots.size() - 9, false))
				{
					return null;
				}
			}

			if (item.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		return itemCopy;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.ovenInventory.closeInventory(player);
	}
}
