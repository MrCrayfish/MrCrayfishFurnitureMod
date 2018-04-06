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
package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.handler.FuelHandler;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class FurnitureCrafting
{
	public static void register()
	{
		GameRegistry.addSmelting(FurnitureItems.itemFlesh, new ItemStack(FurnitureItems.itemCookedFlesh), 0.05F);
		GameRegistry.registerFuelHandler(new FuelHandler());
		RegistrationHandler.RECIPES.add(new RecipeSoapyWater().setRegistryName("cfm:recipe_soapy_water"));
	}

	private static class RecipeSoapyWater extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
	{
		public RecipeSoapyWater()
		{
			this.setRegistryName(Reference.MOD_ID + ":white_fence");
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn)
		{
			int size = 0;

			for(int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if(!stack.isEmpty())
				{
					size++;
				}
			}

			for(int i = 0; i < inv.getSizeInventory() - inv.getWidth(); i++)
			{
				ItemStack above = inv.getStackInSlot(i);
				if(!above.isEmpty() && above.getItem() == FurnitureItems.itemSoap)
				{
					ItemStack below = inv.getStackInSlot(i + inv.getWidth());
					if(!below.isEmpty() && below.getItem() == Items.WATER_BUCKET)
					{
						return size == 2;
					}
				}
			}
			return false;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv)
		{
			return new ItemStack(FurnitureItems.itemSoapyWater);
		}

		@Override
		public boolean canFit(int width, int height)
		{
			return width > 0 && height > 1;
		}

		@Override
		public ItemStack getRecipeOutput()
		{
			return new ItemStack(FurnitureItems.itemSoapyWater);
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
		{
			NonNullList<ItemStack> items = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

			for(int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if(!stack.isEmpty() && stack.getItem() == FurnitureItems.itemSoap)
				{
					ItemStack soap = stack.copy();
					soap.shrink(1);
					items.set(i, soap);
					break;
				}
			}

			inv.clear();

			return items;
		}

		@Override
		public String getGroup()
		{
			return "tabFurniture";
		}
	}

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler
	{
		public static final List<IRecipe> RECIPES = new LinkedList<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<IRecipe> event)
		{
			RECIPES.stream().forEach(recipe -> event.getRegistry().register(recipe));
		}
	}
}
