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
package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.items.IMail;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnvelope extends Slot
{
	InventoryEnvelope inventoryEnv;

	public SlotEnvelope(IInventory inventory, int slotIndex, int x, int y)
	{
		super(inventory, slotIndex, x, y);
		inventoryEnv = (InventoryEnvelope) inventory;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		if (this.inventoryEnv.isSigned())
		{
			return false;
		}
		return par1ItemStack != null && par1ItemStack.getItem() instanceof IMail ? false : true;
	}

}
