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

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotArmour extends Slot
{
	private int armourType;

	public SlotArmour(IInventory machine, int id, int x, int y, int armourType)
	{
		super(machine, id, x, y);
		this.armourType = armourType;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		if (par1ItemStack == null)
		{
			return false;
		}

		if (!(par1ItemStack.getItem() instanceof ItemArmor))
		{
			return false;
		}

		if (((ItemArmor) par1ItemStack.getItem()).armorType != armourType)
		{
			return false;
		}

		return true;
	}
}
