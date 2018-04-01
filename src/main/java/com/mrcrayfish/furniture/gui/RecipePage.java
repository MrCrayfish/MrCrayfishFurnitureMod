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

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class RecipePage
{
	protected ArrayList<RecipeData> recipes = new ArrayList<RecipeData>();
	public String type;

	protected int spacing = 30;

	public RecipePage(String type)
	{
		this.type = type;
	}
	
	public void init(List<GuiButton> buttonList) {}
	
	public abstract void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks);
	
	public abstract void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks);
	
	public void onShown() {}
	
	public void onClose() {}
	
	public void handleButtonClick(GuiButton button) {};
	
	public abstract String getTitle();
	
	public boolean shouldDrawTitle() 
	{
		return true;
	}

	public void addRecipe(RecipeData data)
	{
		this.recipes.add(data);
	}

	public String fixName(String name)
	{
		if (name.length() > 18)
		{
			name = name.substring(0, 18) + "...";
		}
		return name;
	}

	private ItemStack drink = new ItemStack(FurnitureItems.itemDrink);

	public ItemStack getDrink(String name, int red, int green, int blue)
	{
		drink.getTagCompound().setIntArray("Colour", new int[] { red, green, blue });
		drink.getTagCompound().setString("Name", name);
		return drink;
	}

	public void drawToolTip(GuiRecipeBook gui, Minecraft mc, int x, int y, int mouseX, int mouseY, ItemStack stack)
	{
		if (gui.isMouseWithin(x, y, 16, 16, mouseX, mouseY))
		{
			gui.renderToolTip(stack, mouseX, mouseY);
		}
	}
}
