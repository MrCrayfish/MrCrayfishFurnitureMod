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
import com.mrcrayfish.furniture.gui.slots.SlotArmour;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerWashingMachine extends Container
{
	private IInventory washingMachineInventory;

	public ContainerWashingMachine(IInventory playerInventory, IInventory washingMachineInventory)
	{
		this.washingMachineInventory = washingMachineInventory;

		this.addSlotToContainer(new SlotArmour(washingMachineInventory, 0, 80, 43, 0));
		this.addSlotToContainer(new SlotArmour(washingMachineInventory, 1, 64, 60, 1));
		this.addSlotToContainer(new SlotArmour(washingMachineInventory, 2, 96, 60, 2));
		this.addSlotToContainer(new SlotArmour(washingMachineInventory, 3, 80, 76, 3));
		this.addSlotToContainer(new Slot(washingMachineInventory, 4, 125, 7));

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
		return this.washingMachineInventory.isUseableByPlayer(player);
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

			if (slotNum < 5)
			{
				if (!this.mergeItemStack(item, 5, this.inventorySlots.size(), false))
				{
					return null;
				}
			}
			else if (slotNum > 4)
			{
				RecipeData data = RecipeAPI.getWashingMachineRecipeFromInput(item);

				if (data != null)
				{
					if (data.getInput().getItem() instanceof ItemArmor)
					{
						ItemArmor armour = (ItemArmor) data.getInput().getItem();
						if (!this.mergeItemStack(item, armour.armorType, armour.armorType + 1, true))
						{
							return null;
						}
					}
				}
				else if (item.getItem() == FurnitureItems.itemSoapyWater | item.getItem() == FurnitureItems.itemSuperSoapyWater)
				{
					if (!this.mergeItemStack(item, 4, 5, false))
					{
						return null;
					}
				}
				else if (slotNum > 4 && slotNum < this.inventorySlots.size() - 9)
				{
					if (!this.mergeItemStack(item, this.inventorySlots.size() - 9, this.inventorySlots.size(), false))
					{
						return null;
					}
				}
				else if (slotNum >= this.inventorySlots.size() - 9 && slotNum < this.inventorySlots.size())
				{
					if (!this.mergeItemStack(item, 5, this.inventorySlots.size() - 9, false))
					{
						return null;
					}
				}
			}
			else if (!this.mergeItemStack(item, 0, 9, false))
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
		}

		return itemCopy;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		washingMachineInventory.closeInventory(player);
	}
}
