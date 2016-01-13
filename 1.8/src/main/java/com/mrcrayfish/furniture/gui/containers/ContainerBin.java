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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBin extends Container
{
	private IInventory binInventory;

	public ContainerBin(IInventory playerInventory, IInventory binInventory)
	{
		this.binInventory = binInventory;
		this.binInventory.openInventory(null);

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(binInventory, j + i * 3, j * 18 + 62, i * 18 + 18));
			}
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 115));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 173));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(slot);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (slot < 12)
			{
				if (!this.mergeItemStack(var5, 12, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 12, false))
			{
				return null;
			}

			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			}
			else
			{
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return this.binInventory.isUseableByPlayer(entityPlayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityPlayer)
	{
		super.onContainerClosed(entityPlayer);
		binInventory.closeInventory(entityPlayer);
	}

}
