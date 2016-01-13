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

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotTool;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ContainerDishwasher extends Container
{
	private IInventory dishwasherInventory;

	public ContainerDishwasher(IInventory playerInventory, IInventory dishwasherInventory)
	{
		this.dishwasherInventory = dishwasherInventory;
		dishwasherInventory.openInventory(null);

		this.addSlotToContainer(new SlotTool(dishwasherInventory, 0, 56, 43, 0));
		this.addSlotToContainer(new SlotTool(dishwasherInventory, 1, 80, 43, 1));
		this.addSlotToContainer(new SlotTool(dishwasherInventory, 2, 104, 43, 2));
		this.addSlotToContainer(new SlotTool(dishwasherInventory, 3, 56, 74, 3));
		this.addSlotToContainer(new SlotTool(dishwasherInventory, 4, 80, 74, 4));
		this.addSlotToContainer(new SlotTool(dishwasherInventory, 5, 104, 74, 5));
		this.addSlotToContainer(new Slot(dishwasherInventory, 6, 125, 7));

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
		return this.dishwasherInventory.isUseableByPlayer(player);
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

			if (slotNum <= 6)
			{
				if (!this.mergeItemStack(item, 7, this.inventorySlots.size(), false))
				{
					return null;
				}
			}
			else if (slotNum > 6)
			{
				RecipeData data = RecipeAPI.getDishwasherRecipeFromInput(item);

				if (data != null)
				{
					int corroSlot = toolToSlot(item);
					if (slotNum != -1)
					{
						if (!this.mergeItemStack(item, corroSlot, corroSlot + 1, true))
						{
							return null;
						}
					}
				}
				else if (item.getItem() == FurnitureItems.itemSoapyWater | item.getItem() == FurnitureItems.itemSuperSoapyWater)
				{
					if (!this.mergeItemStack(item, 6, 7, false))
					{
						return null;
					}
				}
				else if (slotNum > 6 && slotNum < this.inventorySlots.size() - 9)
				{
					if (!this.mergeItemStack(item, this.inventorySlots.size() - 9, this.inventorySlots.size(), false))
					{
						return null;
					}
				}
				else if (slotNum >= this.inventorySlots.size() - 9 && slotNum < this.inventorySlots.size())
				{
					if (!this.mergeItemStack(item, 7, this.inventorySlots.size() - 9, false))
					{
						return null;
					}
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

	public static int toolToSlot(ItemStack stack)
	{
		Item item = stack.getItem();
		if (item instanceof ItemPickaxe)
		{
			return 0;
		}

		if (item instanceof ItemSpade)
		{
			return 1;
		}

		if (item instanceof ItemSword)
		{
			return 2;
		}

		if (item instanceof ItemAxe)
		{
			return 3;
		}

		if (item instanceof ItemHoe)
		{
			return 4;
		}

		if (item instanceof ItemBow)
		{
			return 5;
		}
		return -1;
	}
}
