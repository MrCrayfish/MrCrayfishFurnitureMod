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

import com.mrcrayfish.furniture.api.RecipeAPI;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotArmour extends Slot
{
	private EntityEquipmentSlot armourType;

	public SlotArmour(IInventory machine, int id, int x, int y, EntityEquipmentSlot armourType) {
		super(machine, id, x, y);
		this.armourType = armourType;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack == null) {
			return false;
		}

		if (stack.isEmpty()) {
			return false;
		}

		if (RecipeAPI.getWashingMachineRecipeFromInput(stack) == null)
			return false;

		if (EntityLiving.getSlotForItemStack(stack) != armourType) {
			return false;
		}

		return true;
	}

	@Override
	public String getSlotTexture()
	{
		return ItemArmor.EMPTY_SLOT_NAMES[armourType.getIndex()];
	}
}
