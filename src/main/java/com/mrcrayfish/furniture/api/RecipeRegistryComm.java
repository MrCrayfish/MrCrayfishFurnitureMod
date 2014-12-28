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
package com.mrcrayfish.furniture.api;

import com.mrcrayfish.furniture.handler.ConfigurationHandler;

public class RecipeRegistryComm extends RecipeAPI implements IRecipeRegistry
{

	private static RecipeRegistryComm furnitureRegister = null;
	private String modName;

	public static RecipeRegistryComm getInstance(String modName)
	{
		if (furnitureRegister == null)
		{
			furnitureRegister = new RecipeRegistryComm();
		}
		furnitureRegister.modName = modName;
		return furnitureRegister;
	}

	@Override
	public void registerRecipe(String type, RecipeVariables params)
	{
		if (type.equalsIgnoreCase("minebay"))
		{
			if (RecipeConditions.hasMineBayArgs(params))
			{
				addMineBayRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables", modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("oven"))
		{
			if (RecipeConditions.hasOvenArgs(params))
			{
				addOvenRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables", modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("freezer"))
		{
			if (RecipeConditions.hasFreezerArgs(params))
			{
				addFreezerRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables", modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("printer"))
		{
			if (RecipeConditions.hasPrinterArgs(params))
			{
				addPrinterRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("toaster"))
		{
			if (RecipeConditions.hasToasterArgs(params))
			{
				addToasterRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("choppingboard"))
		{
			if (RecipeConditions.hasChoppingBoardArgs(params))
			{
				addChoppingBoardRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("blender"))
		{
			if (RecipeConditions.hasBlenderArgs(params))
			{
				addBlenderRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("microwave"))
		{
			if (RecipeConditions.hasMicrowaveArgs(params))
			{
				addMicrowaveRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("washingmachine"))
		{
			if (RecipeConditions.hasWashingMachineArgs(params))
			{
				addWashingMachineRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else if (type.equalsIgnoreCase("dishwasher"))
		{
			if (RecipeConditions.hasDishwasherArgs(params))
			{
				addDishwasherRecipe(RecipeData.convertFrom(params), COMM);
			}
			else
			{
				if (ConfigurationHandler.api_debug)
				{
					RecipeUtil.printRequired(type, "Missing required variables for " + type, modName);
				}
			}
		}
		else
		{
			if (ConfigurationHandler.api_debug)
			{
				System.err.println("## RecipeAPI Error Report ##");
				System.err.println("From Mod: " + modName);
				System.err.println("Description: " + " The mod '" + modName + "' is trying to add a non existing recipe type '" + type + "'.");
				System.err.println("Type: " + type + " (Unknown)");
			}
		}
		params.clear();
	}
}
