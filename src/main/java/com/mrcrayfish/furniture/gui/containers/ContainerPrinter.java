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
package com.mrcrayfish.furniture.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.mrcrayfish.furniture.gui.slots.SlotPrinter;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;

public class ContainerPrinter extends Container
{
	private IInventory printerInventory;

	public ContainerPrinter(IInventory playerInventory, IInventory printerInventory)
	{
		this.printerInventory = printerInventory;
		this.addSlotToContainer(new Slot(printerInventory, 0, 80, 5));
		this.addSlotToContainer(new Slot(printerInventory, 1, 55, 30));
		this.addSlotToContainer(new SlotPrinter(printerInventory, 2, 80, 61));
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 103));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 161));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.printerInventory.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack slotClick(int slotNum, int clickedButton, int mode, EntityPlayer player)
	{
		if (slotNum == 2)
		{
			Slot var4 = (Slot) this.inventorySlots.get(slotNum);
			if (var4 != null && var4.getHasStack())
			{
				player.triggerAchievement(FurnitureAchievements.copyItem);
			}
		}
		return super.slotClick(slotNum, clickedButton, mode, player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		ItemStack itemCopy = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack item = slot.getStack();
			itemCopy = item.copy();

			if (slotNum == 2)
			{
				if (!this.mergeItemStack(item, 3, 39, true))
				{
					return null;
				}

				slot.onSlotChange(item, itemCopy);
			}
			else if (slotNum != 1 && slotNum != 0)
			{
				if (itemCopy.getItem() == Items.enchanted_book | itemCopy.getItem() == Items.written_book)
				{
					if (!this.mergeItemStack(item, 0, 1, false))
					{
						return null;
					}
				}
				else if (TileEntityPrinter.isItemFuel(item))
				{
					if (!this.mergeItemStack(item, 1, 2, false))
					{
						return null;
					}
				}
				else if (slotNum >= 3 && slotNum < 30)
				{
					if (!this.mergeItemStack(item, 30, 39, false))
					{
						return null;
					}
				}
				else if (slotNum >= 30 && slotNum < 39 && !this.mergeItemStack(item, 3, 30, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(item, 3, 39, false))
			{
				return null;
			}

			if (item.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (item.stackSize == itemCopy.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, item);
		}

		return itemCopy;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		printerInventory.closeInventory(player);
	}
}
