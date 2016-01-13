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

import java.util.Map;

import net.minecraft.item.ItemStack;

public class RecipeConditions
{
	public static boolean hasMineBayArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasOvenArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		if (variables.get("output") == null)
			return false;
		if (!(variables.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasFreezerArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		if (variables.get("output") == null)
			return false;
		if (!(variables.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasPrinterArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasChoppingBoardArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		if (variables.get("output") == null)
			return false;
		if (!(variables.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasBlenderArgs(Map<String, Object> variables)
	{
		if (variables.get("name") == null)
			return false;
		if (!(variables.get("name") instanceof String))
			return false;
		if (variables.get("heal") == null)
			return false;
		if (!(variables.get("heal") instanceof Integer))
			return false;
		if (variables.get("ingredients") == null)
			return false;
		if (!(variables.get("ingredients") instanceof ItemStack[]))
			return false;
		if (((ItemStack[]) variables.get("ingredients")).length == 0 | ((ItemStack[]) variables.get("ingredients")).length > 4)
			return false;
		if (variables.get("colour") == null)
			return false;
		if (!(variables.get("colour") instanceof int[]))
			return false;
		if (((int[]) variables.get("colour")).length != 3)
			return false;
		return true;
	}

	public static boolean hasMicrowaveArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		if (variables.get("output") == null)
			return false;
		if (!(variables.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasWashingMachineArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasToasterArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		if (variables.get("output") == null)
			return false;
		if (!(variables.get("output") instanceof ItemStack))
			return false;
		return true;
	}

	public static boolean hasDishwasherArgs(Map<String, Object> variables)
	{
		if (variables.get("input") == null)
			return false;
		if (!(variables.get("input") instanceof ItemStack))
			return false;
		return true;
	}
}
