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

public class RecipeUtil
{
	public static void printRequired(String type, String desc, String modName)
	{
		System.err.println("## RecipeAPI Error Report ##");
		System.err.println("From Mod: " + modName);
		System.err.println("Type: " + type);
		System.err.println("Description: " + desc);
		System.err.println("Required Variables:");
		for (String var : getRequiredVariablesList(type))
		{
			System.err.println("- " + var);
		}
	}

	public static void printUnknownType(Parser parser, int num, String desc)
	{
		System.err.println("## RecipeAPI Error Report ##");
		System.err.println("Recipe Number: " + num);
		System.err.println("Unknown Type: " + parser.getValue("type", null));
		System.err.println("Description: " + desc);
		System.err.println("Variables: ");
		printVariables(parser, "");
	}

	public static void printMissing(Parser parser, int num, String missingKey, String desc)
	{
		System.err.println("## RecipeAPI Error Report ##");
		System.err.println("Recipe Number: " + num);
		System.err.println("Type: " + parser.getValue("type", null));
		System.err.println("Description: " + desc);
		System.err.println("Variables: ");
		System.err.println("- " + missingKey + ": (Missing)");
		printVariables(parser, "");
	}

	public static void printReport(Parser parser, int num, String erroredKey, String desc)
	{
		System.err.println("## RecipeAPI Error Report ##");
		System.err.println("Recipe Number: " + num);
		System.err.println("Type: " + parser.getValue("type", null) + (erroredKey.equalsIgnoreCase("type") ? " (Error)" : ""));
		System.err.println("Description: " + desc);
		System.err.println("Variables: ");
		printVariables(parser, erroredKey);
	}

	private static void printVariables(Parser parser, String erroredKey)
	{
		for (String key : parser.getMap().keySet())
		{
			if (!key.equalsIgnoreCase("type"))
			{
				String pre = "- " + key + ": " + parser.getValue(key, null);
				if (key.equalsIgnoreCase(erroredKey))
				{
					pre += " (Error)";
				}
				System.err.println(pre);
			}
		}
	}

	private static ArrayList<String> getRequiredVariablesList(String type)
	{
		ArrayList<String> vars = new ArrayList<String>();
		if (type.equalsIgnoreCase("minebay") | type.equalsIgnoreCase("oven") | type.equalsIgnoreCase("freezer") | type.equalsIgnoreCase("printer") | type.equalsIgnoreCase("choppingboard") | type.equalsIgnoreCase("microwave") | type.equalsIgnoreCase("washingmachine") | type.equalsIgnoreCase("toaster") | type.equalsIgnoreCase("dishwasher"))
		{
			vars.add("input");
		}

		if (type.equalsIgnoreCase("oven") | type.equalsIgnoreCase("freezer") | type.equalsIgnoreCase("choppingboard") | type.equalsIgnoreCase("microwave") | type.equalsIgnoreCase("toaster"))
		{
			vars.add("output");
		}

		if (type.equalsIgnoreCase("blender"))
		{
			vars.add("name");
			vars.add("heal");
			vars.add("ingredients");
			vars.add("colour");
		}
		return vars;
	}
}
