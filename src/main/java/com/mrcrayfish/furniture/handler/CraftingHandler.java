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
package com.mrcrayfish.furniture.handler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class CraftingHandler
{
	@SubscribeEvent
	public void onCrafted(ItemCraftedEvent event)
	{
		if (event.crafting.getItem() == FurnitureItems.itemChairWood | event.crafting.getItem() == FurnitureItems.itemChairStone)
		{
			event.player.triggerAchievement(FurnitureAchievements.mineKea);
		}

		if (event.crafting.getItem() == FurnitureItems.itemSoapyWater)
		{
			for (int i = 0; i < 9; i++)
			{
				ItemStack item = event.craftMatrix.getStackInSlot(i);
				if (item != null)
				{
					if (item.getItem() == Items.water_bucket)
					{
						event.craftMatrix.setInventorySlotContents(i, null);
						break;
					}
				}
			}
		}

		if (event.crafting.getItem() == FurnitureItems.itemSuperSoapyWater)
		{
			for (int i = 0; i < 9; i++)
			{
				ItemStack item = event.craftMatrix.getStackInSlot(i);
				if (item != null)
				{
					if (item.getItem() == FurnitureItems.itemSoapyWater)
					{
						event.craftMatrix.setInventorySlotContents(i, null);
						break;
					}
				}
			}
		}
	}
}
