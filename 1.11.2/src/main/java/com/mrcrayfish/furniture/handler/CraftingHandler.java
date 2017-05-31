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
package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.blocks.BlockChair;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler
{
	@SubscribeEvent
	public void onCrafted(ItemCraftedEvent event)
	{
		Item item = event.crafting.getItem();
		if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockChair)
		{
			event.player.addStat(FurnitureAchievements.mineKea);
			return;
		}
		
		if(item == FurnitureItems.itemSoapyWater || item == FurnitureItems.itemSuperSoapyWater) 
		{
			for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
			{
				ItemStack stack = event.craftMatrix.getStackInSlot(i);
				if(stack != null)
				{
					stack.shrink(1);
					if(stack.getCount() > 0)
					{
						if(!event.player.inventory.addItemStackToInventory(stack))
						{
							event.player.dropItem(stack, false, true);
						}
					}
				}
				//event.craftMatrix.setInventorySlotContents(i, null); 
			}
		}

		if(item == FurnitureItems.itemLog)
		{
			for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
			{
				ItemStack stack = event.craftMatrix.getStackInSlot(i);
				if(stack != null)
				{
					if(stack.getItem() instanceof ItemAxe)
					{
						stack.damageItem(16, event.player);
						if(!event.player.inventory.addItemStackToInventory(stack))
						{
							event.player.dropItem(stack, false, true);
						}
					}
				}
			}
		}
	}
}
