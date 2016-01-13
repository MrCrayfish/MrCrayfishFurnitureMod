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
package com.mrcrayfish.furniture.api;

import java.util.ArrayList;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.item.ItemStack;

public class Recipes
{
	// Used for sending to clients
	public static ArrayList<String> recipeData = new ArrayList<String>();

	/** Recipes registered from the Config */
	public static ArrayList<RecipeData> localMineBayRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localOvenRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localFreezerRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localPrinterRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localToasterRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localChoppingBoardRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localBlenderRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localDishwasherRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localWashingMachineRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> localMicrowaveRecipes = new ArrayList<RecipeData>();

	/** Recipes registered through FMLInterModComms */
	public static ArrayList<RecipeData> commMineBayRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commOvenRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commFreezerRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commPrinterRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commToasterRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commChoppingBoardRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commBlenderRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commDishwasherRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commWashingMachineRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> commMicrowaveRecipes = new ArrayList<RecipeData>();

	/** Recipes registered from a server */
	public static ArrayList<RecipeData> remoteMineBayRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteOvenRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteFreezerRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remotePrinterRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteToasterRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteChoppingBoardRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteBlenderRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteDishwasherRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteWashingMachineRecipes = new ArrayList<RecipeData>();
	public static ArrayList<RecipeData> remoteMicrowaveRecipes = new ArrayList<RecipeData>();

	public static RecipeData[] getMineBayItems()
	{
		return getRecipes("minebay").toArray(new RecipeData[0]);
	}

	public static RecipeData getOvenRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("oven");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				if (validItemStack.getItemDamage() == itemStack.getItemDamage())
				{
					return recipes.get(i);
				}
			}
		}
		return null;
	}

	public static RecipeData getFreezerRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("freezer");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				if (validItemStack.getItemDamage() == itemStack.getItemDamage())
				{
					return recipes.get(i);
				}
			}
		}
		return null;
	}

	public static RecipeData getPrinterRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("printer");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				if (validItemStack.getItemDamage() == itemStack.getItemDamage())
				{
					if (itemStack.stackSize == 1)
					{
						return recipes.get(i);
					}
				}
			}
		}
		return null;
	}

	public static RecipeData getToasterRecipeFormInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("toaster");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				if (validItemStack.getItemDamage() == itemStack.getItemDamage())
				{
					return recipes.get(i);
				}
			}
		}
		return null;
	}

	public static RecipeData getChoppingBoardRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("choppingboard");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				if (validItemStack.getItemDamage() == itemStack.getItemDamage())
				{
					return recipes.get(i);
				}
			}
		}
		return null;
	}

	public static RecipeData getBlenderRecipeFromIngredients(ItemStack[] itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("blender");
		for (int i = 0; i < recipes.size(); i++)
		{
			int count = 0;
			ArrayList<ItemStack> ingredients = recipes.get(i).getIngredients();
			for (ItemStack item : itemStack)
			{
				if (item != null)
				{
					for (ItemStack ingredient : ingredients)
					{
						if (ingredient.getItem() == item.getItem())
						{
							if (ingredient.stackSize == item.stackSize)
							{
								if (ingredient.getItemDamage() == item.getItemDamage())
								{
									count++;
								}
							}
						}
						if (count == ingredients.size())
						{
							return recipes.get(i);
						}
					}
				}
			}
		}
		return null;
	}

	public static RecipeData getMicrowaveRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("microwave");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				if (validItemStack.getItemDamage() == itemStack.getItemDamage())
				{
					return recipes.get(i);
				}
			}
		}
		return null;
	}

	public static RecipeData getWashingMachineRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("washingmachine");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				return recipes.get(i);
			}
		}
		return null;
	}

	public static RecipeData getDishwasherRecipeFromInput(ItemStack itemStack)
	{
		ArrayList<RecipeData> recipes = getRecipes("dishwasher");
		for (int i = 0; i < recipes.size(); i++)
		{
			ItemStack validItemStack = recipes.get(i).getInput();
			if (validItemStack.getItem() == itemStack.getItem())
			{
				return recipes.get(i);
			}
		}
		return null;
	}

	public static void updateDataList()
	{
		recipeData.clear();
		for (RecipeData data : localMineBayRecipes)
		{
			recipeData.add("type=minebay," + data.toString());
		}
		for (RecipeData data : localOvenRecipes)
		{
			recipeData.add("type=oven," + data.toString());
		}
		for (RecipeData data : localFreezerRecipes)
		{
			recipeData.add("type=freezer," + data.toString());
		}
		for (RecipeData data : localPrinterRecipes)
		{
			recipeData.add("type=printer," + data.toString());
		}
		for (RecipeData data : localToasterRecipes)
		{
			recipeData.add("type=toaster," + data.toString());
		}
		for (RecipeData data : localChoppingBoardRecipes)
		{
			recipeData.add("type=choppingboard," + data.toString());
		}
		for (RecipeData data : localBlenderRecipes)
		{
			recipeData.add("type=blender," + data.toString());
		}
		for (RecipeData data : localMicrowaveRecipes)
		{
			recipeData.add("type=microwave," + data.toString());
		}
		for (RecipeData data : localWashingMachineRecipes)
		{
			recipeData.add("type=washingmachine," + data.toString());
		}
		for (RecipeData data : localDishwasherRecipes)
		{
			recipeData.add("type=dishwasher," + data.toString());
		}
	}

	public static ArrayList<RecipeData> getRecipes(String type)
	{
		if (MrCrayfishFurnitureMod.proxy.isSinglePlayer() | MrCrayfishFurnitureMod.proxy.isDedicatedServer())
		{
			if (type.equalsIgnoreCase("minebay"))
			{
				return localMineBayRecipes;
			}
			else if (type.equalsIgnoreCase("freezer"))
			{
				return localFreezerRecipes;
			}
			else if (type.equalsIgnoreCase("oven"))
			{
				return localOvenRecipes;
			}
			else if (type.equalsIgnoreCase("printer"))
			{
				return localPrinterRecipes;
			}
			else if (type.equalsIgnoreCase("toaster"))
			{
				return localToasterRecipes;
			}
			else if (type.equalsIgnoreCase("choppingboard"))
			{
				return localChoppingBoardRecipes;
			}
			else if (type.equalsIgnoreCase("blender"))
			{
				return localBlenderRecipes;
			}
			else if (type.equalsIgnoreCase("microwave"))
			{
				return localMicrowaveRecipes;
			}
			else if (type.equalsIgnoreCase("washingmachine"))
			{
				return localWashingMachineRecipes;
			}
			else if (type.equalsIgnoreCase("dishwasher"))
			{
				return localDishwasherRecipes;
			}
		}
		else
		{
			if (type.equalsIgnoreCase("minebay"))
			{
				return remoteMineBayRecipes;
			}
			else if (type.equalsIgnoreCase("freezer"))
			{
				return remoteFreezerRecipes;
			}
			else if (type.equalsIgnoreCase("oven"))
			{
				return remoteOvenRecipes;
			}
			else if (type.equalsIgnoreCase("printer"))
			{
				return remotePrinterRecipes;
			}
			else if (type.equalsIgnoreCase("toaster"))
			{
				return remoteToasterRecipes;
			}
			else if (type.equalsIgnoreCase("choppingboard"))
			{
				return remoteChoppingBoardRecipes;
			}
			else if (type.equalsIgnoreCase("blender"))
			{
				return remoteBlenderRecipes;
			}
			else if (type.equalsIgnoreCase("microwave"))
			{
				return remoteMicrowaveRecipes;
			}
			else if (type.equalsIgnoreCase("washingmachine"))
			{
				return remoteWashingMachineRecipes;
			}
			else if (type.equalsIgnoreCase("dishwasher"))
			{
				return remoteDishwasherRecipes;
			}
		}
		return new ArrayList<RecipeData>();
	}

	public static void addCommRecipesToLocal()
	{
		for (RecipeData data : commMineBayRecipes)
		{
			localMineBayRecipes.add(data);
		}
		for (RecipeData data : commOvenRecipes)
		{
			localOvenRecipes.add(data);
		}
		for (RecipeData data : commFreezerRecipes)
		{
			localFreezerRecipes.add(data);
		}
		for (RecipeData data : commPrinterRecipes)
		{
			localPrinterRecipes.add(data);
		}
		for (RecipeData data : commToasterRecipes)
		{
			localToasterRecipes.add(data);
		}
		for (RecipeData data : commChoppingBoardRecipes)
		{
			localChoppingBoardRecipes.add(data);
		}
		for (RecipeData data : commBlenderRecipes)
		{
			localBlenderRecipes.add(data);
		}
		for (RecipeData data : commMicrowaveRecipes)
		{
			localMicrowaveRecipes.add(data);
		}
		for (RecipeData data : commWashingMachineRecipes)
		{
			localWashingMachineRecipes.add(data);
		}
		for (RecipeData data : commDishwasherRecipes)
		{
			localDishwasherRecipes.add(data);
		}
	}

	public static void clearLocalRecipes()
	{
		localMineBayRecipes.clear();
		localOvenRecipes.clear();
		localFreezerRecipes.clear();
		localPrinterRecipes.clear();
		localToasterRecipes.clear();
		localChoppingBoardRecipes.clear();
		localBlenderRecipes.clear();
		localMicrowaveRecipes.clear();
		localWashingMachineRecipes.clear();
		localDishwasherRecipes.clear();
	}

	public static void clearRemoteRecipes()
	{
		remoteMineBayRecipes.clear();
		remoteOvenRecipes.clear();
		remoteFreezerRecipes.clear();
		remotePrinterRecipes.clear();
		remoteToasterRecipes.clear();
		remoteChoppingBoardRecipes.clear();
		remoteBlenderRecipes.clear();
		remoteMicrowaveRecipes.clear();
		remoteWashingMachineRecipes.clear();
		remoteDishwasherRecipes.clear();
	}

	public static void clearCommRecipes()
	{
		commMineBayRecipes.clear();
		commOvenRecipes.clear();
		commFreezerRecipes.clear();
		commPrinterRecipes.clear();
		commToasterRecipes.clear();
		commChoppingBoardRecipes.clear();
		commBlenderRecipes.clear();
		commMicrowaveRecipes.clear();
		commWashingMachineRecipes.clear();
		commDishwasherRecipes.clear();
	}

	public static void clearAll()
	{
		clearLocalRecipes();
		clearRemoteRecipes();
		clearCommRecipes();
	}
}
