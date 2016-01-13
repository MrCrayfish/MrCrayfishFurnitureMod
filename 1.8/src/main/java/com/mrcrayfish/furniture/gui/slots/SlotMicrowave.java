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

import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMicrowave extends Slot
{
	private TileEntityMicrowave microwave;

	public SlotMicrowave(TileEntityMicrowave microwave, int id, int x, int y)
	{
		super(microwave, id, x, y);
		this.microwave = microwave;
	}

	@Override
	public boolean isItemValid(ItemStack item)
	{
		if (microwave.isCooking())
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		if (microwave.isCooking())
		{
			return false;
		}
		return true;
	}
}
