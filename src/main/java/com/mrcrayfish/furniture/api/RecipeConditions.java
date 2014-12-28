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

import net.minecraft.item.ItemStack;

public class RecipeConditions
{

	public static boolean hasMineBayArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasOvenArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		if (params.get("output") == null)
			return false;
		if (!(params.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasFreezerArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		if (params.get("output") == null)
			return false;
		if (!(params.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasPrinterArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasChoppingBoardArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		if (params.get("output") == null)
			return false;
		if (!(params.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasBlenderArgs(RecipeVariables params)
	{
		if (params.get("name") == null)
			return false;
		if (!(params.get("name") instanceof String))
			return false;
		if (params.get("heal") == null)
			return false;
		if (!(params.get("heal") instanceof Integer))
			return false;
		if (params.get("ingredients") == null)
			return false;
		if (!(params.get("ingredients") instanceof ItemStack[]))
			return false;
		if (((ItemStack[]) params.get("ingredients")).length == 0 | ((ItemStack[]) params.get("ingredients")).length > 4)
			return false;
		if (params.get("colour") == null)
			return false;
		if (!(params.get("colour") instanceof int[]))
			return false;
		if (((int[]) params.get("colour")).length != 3)
			return false;
		return true;
	}

	public static boolean hasMicrowaveArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		if (params.get("output") == null)
			return false;
		if (!(params.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasWashingMachineArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasToasterArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		if (params.get("output") == null)
			return false;
		if (!(params.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasDishwasherArgs(RecipeVariables params)
	{
		if (params.get("input") == null)
			return false;
		if (!(params.get("input") instanceof ItemStack))
			return false;
		return true;
	}
}
