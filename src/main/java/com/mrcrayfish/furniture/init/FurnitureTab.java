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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FurnitureTab extends CreativeTabs 
{
	private String title = "";
	private boolean hoveringButton = false;

	public FurnitureTab(String label) 
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(FurnitureBlocks.chair_oak);
	}
	
	@Override
	public String getTranslatedTabLabel() 
	{
		return hoveringButton ? title : "itemGroup.tabFurniture";
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public void setHoveringButton(boolean hoveringButton) 
	{
		this.hoveringButton = hoveringButton;
	}
	
}
