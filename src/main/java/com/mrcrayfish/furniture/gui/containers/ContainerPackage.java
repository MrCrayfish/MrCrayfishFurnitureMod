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

import com.mrcrayfish.furniture.gui.slots.SlotPackage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPackage extends Container
{
	private int index;
	private IInventory packageInventory;

	public ContainerPackage(IInventory playerInventory, IInventory packageInventory)
	{
		this.index = ((InventoryPlayer) playerInventory).currentItem;
		this.packageInventory = packageInventory;
		packageInventory.openInventory(null);

		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 142));
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 84));
			}
		}

		this.addSlotToContainer(new SlotPackage(packageInventory, 0, 8 + 0 * 18 + 54, 18 + 0 * 18 - 3));
		this.addSlotToContainer(new SlotPackage(packageInventory, 1, 8 + 0 * 18 + 54, 18 + 1 * 18 - 3));
		this.addSlotToContainer(new SlotPackage(packageInventory, 2, 8 + 1 * 18 + 54, 18 + 0 * 18 - 3));
		this.addSlotToContainer(new SlotPackage(packageInventory, 3, 8 + 1 * 18 + 54, 18 + 1 * 18 - 3));
		this.addSlotToContainer(new SlotPackage(packageInventory, 4, 8 + 2 * 18 + 54, 18 + 0 * 18 - 3));
		this.addSlotToContainer(new SlotPackage(packageInventory, 5, 8 + 2 * 18 + 54, 18 + 1 * 18 - 3));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.packageInventory.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		if(slotNum == index) return ItemStack.EMPTY;

		ItemStack itemCopy = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack() && slot instanceof SlotPackage)
		{
			ItemStack item = slot.getStack();
			itemCopy = item.copy();

			if (slotNum < 6)
			{
				if (!this.mergeItemStack(item, 6, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(item, 0, 6, false))
			{
				return ItemStack.EMPTY;
			}

			if (item.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemCopy;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if(slotId == index) return ItemStack.EMPTY;
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
}
