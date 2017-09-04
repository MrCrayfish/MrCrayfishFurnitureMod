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
package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiFurnitureConfig extends GuiConfig
{
	public GuiFurnitureConfig(GuiScreen parent)
	{
		super(parent, getConfigElements(), "cfm", false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
	}
	
	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> configs = new ArrayList<IConfigElement>();
		configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_SETTINGS)).getChildElements());
		configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_RECIPE_SETTINGS)).getChildElements());
		configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_API)).getChildElements());
		return configs;
	}
}