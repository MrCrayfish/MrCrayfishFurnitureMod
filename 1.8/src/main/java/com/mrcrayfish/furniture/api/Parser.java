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

import java.util.HashMap;

public class Parser
{

	private static Parser instance = new Parser();

	public static Parser getInstance()
	{
		return instance;
	}

	private HashMap<String, String> values = new HashMap<String, String>();

	public void parseLine(String line, boolean removeSpaces)
	{
		values.clear();

		if (removeSpaces)
			line = line.replace(" ", "");

		String[] keys = line.split(",");
		for (String key : keys)
		{
			String[] keyData = key.split("=");
			String key_id = keyData[0];
			String key_value = keyData[1];
			values.put(key_id, key_value);
		}
	}

	public String getValue(String key_id, String d3fault)
	{
		if (values.get(key_id) != null)
		{
			return values.get(key_id);
		}
		return d3fault;
	}

	public HashMap<String, String> getMap()
	{
		return values;
	}
}
