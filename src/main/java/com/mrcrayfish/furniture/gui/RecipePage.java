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
package com.mrcrayfish.furniture.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class RecipePage
{
	private ArrayList<RecipeData> recipes = new ArrayList<RecipeData>();
	public String type;

	private int spacing = 30;

	public RecipePage(String type)
	{
		this.type = type;
	}

	public void addRecipe(RecipeData data)
	{
		this.recipes.add(data);
	}

	public void drawPage(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_LIGHTING);

		drawTitle(gui, x, y);
		drawDesc(gui, x, y);

		for (int i = 0; i < recipes.size(); i++)
		{
			if (type.equals("oven"))
			{
				this.drawOven(mc, gui, recipes.get(i), x + ((i / 4) * 150) + 15, y + (i % 4 * spacing));
				gui.drawProgressArrow(x + ((i / 4) * 150) + 42, y + (i % 4 * spacing) + 2);
			}
			if (type.equals("freezer"))
			{
				this.drawOven(mc, gui, recipes.get(i), x + ((i / 4) * 150) + 15, y + (i % 4 * spacing));
				gui.drawProgressArrow(x + ((i / 4) * 150) + 42, y + (i % 4 * spacing) + 2);
			}
			if (type.equals("minebay"))
			{
				this.drawMineBay(mc, gui, recipes.get(i), x + ((i / 4) * 150), y + (i % 4 * spacing));
			}
			if (type.equals("printer"))
			{
				this.drawPrinter(mc, gui, recipes.get(i), x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30);
			}
			if (type.equals("choppingboard"))
			{
				this.drawChoppingBoard(mc, gui, recipes.get(i), x + ((i / 4) * 150) + 15, y + (i % 4 * spacing));
				gui.drawPlainArrow(x + ((i / 4) * 150) + 42, y + (i % 4 * spacing) + 2);
			}
			if (type.equals("toaster"))
			{
				this.drawOven(mc, gui, recipes.get(i), x + ((i / 4) * 150) + 15, y + (i % 4 * spacing));
				gui.drawProgressArrow(x + ((i / 4) * 150) + 42, y + (i % 4 * spacing) + 2);
			}
			if (type.equals("blender"))
			{
				this.drawBlender(mc, gui, recipes.get(i), x + ((i / 2) * 150) + 8, y + (i % 2 * 50) + 10);
			}
			if (type.equals("washingmachine"))
			{
				this.drawPrinter(mc, gui, recipes.get(i), x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30);
			}
			if (type.equals("dishwasher"))
			{
				this.drawPrinter(mc, gui, recipes.get(i), x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30);
			}
			if (type.equals("microwave"))
			{
				this.drawOven(mc, gui, recipes.get(i), x + ((i / 4) * 150) + 15, y + (i % 4 * spacing));
				gui.drawProgressArrow(x + ((i / 4) * 150) + 42, y + (i % 4 * spacing) + 2);
			}
		}

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();

		for (int i = 0; i < recipes.size(); i++)
		{
			if (type.equals("oven"))
			{
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 15, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 75, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getOutput());
			}
			if (type.equals("freezer"))
			{
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 15, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 75, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getOutput());
			}
			if (type.equals("minebay"))
			{
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 20, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 51, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getCurrency());
			}
			if (type.equals("printer"))
			{
				drawToolTip(gui, mc, x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30, mouseX, mouseY, recipes.get(i).getInput());
			}
			if (type.equals("choppingboard"))
			{
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 15, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 75, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getOutput());
			}
			if (type.equals("toaster"))
			{
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 15, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 75, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getOutput());
			}
			if (type.equals("blender"))
			{
				int posX = x + ((i / 2) * 150) + 8;
				int posY = y + (i % 2 * 50) + 10;

				RecipeData data = recipes.get(i);
				if (gui.func_146978_c(posX + ((0 % 2) * 18), posY + ((0 / 2) * 18) + 10, 16, 16, mouseX, mouseY))
				{
					if (data.getIngredients().size() > 0)
					{
						if (data.getIngredients().get(0) != null)
						{
							gui.renderToolTip(data.getIngredients().get(0), mouseX, mouseY);
						}
					}
				}
				if (gui.func_146978_c(posX + ((1 % 2) * 18), posY + ((1 / 2) * 18) + 10, 16, 16, mouseX, mouseY))
				{
					if (data.getIngredients().size() > 1)
					{
						if (data.getIngredients().get(1) != null)
						{
							gui.renderToolTip(data.getIngredients().get(1), mouseX, mouseY);
						}
					}
				}
				if (gui.func_146978_c(posX + ((2 % 2) * 18), posY + ((2 / 2) * 18) + 10, 16, 16, mouseX, mouseY))
				{
					if (data.getIngredients().size() > 2)
					{
						if (data.getIngredients().get(2) != null)
						{
							gui.renderToolTip(data.getIngredients().get(2), mouseX, mouseY);
						}
					}
				}
				if (gui.func_146978_c(posX + ((3 % 2) * 18), posY + ((3 / 2) * 18) + 10, 16, 16, mouseX, mouseY))
				{
					if (data.getIngredients().size() > 3)
					{
						if (data.getIngredients().get(3) != null)
						{
							gui.renderToolTip(data.getIngredients().get(3), mouseX, mouseY);
						}
					}
				}

				drawToolTip(gui, mc, posX + 80, posY + 20, mouseX, mouseY, getDrink(data.getDrinkName(), data.getRed(), data.getGreen(), data.getBlue()));

			}
			if (type.equals("washingmachine"))
			{
				drawToolTip(gui, mc, x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30, mouseX, mouseY, recipes.get(i).getInput());
			}
			if (type.equals("dishwasher"))
			{
				drawToolTip(gui, mc, x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30, mouseX, mouseY, recipes.get(i).getInput());
			}
			if (type.equals("microwave"))
			{
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 15, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
				drawToolTip(gui, mc, x + ((i / 4) * 150) + 75, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getOutput());
			}
		}
	}

	private void drawTitle(GuiRecipeBook gui, int x, int y)
	{
		if (type.equals("oven"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Oven Recipes", x + 21, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Oven Recipes", x + 170, 25, 1986677);
		}
		if (type.equals("freezer"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Freezer Recipes", x + 13, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Freezer Recipes", x + 163, 25, 1986677);
		}
		if (type.equals("minebay"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "MineBay Items", x + 20, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "MineBay Items", x + 169, 25, 1986677);
		}
		if (type.equals("printer"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Printer Recipes", x + 13, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Printer Recipes", x + 163, 25, 1986677);
		}
		if (type.equals("choppingboard"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Chopping Board Recipes", x - 3, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Chopping Board Recipes", x + 144, 25, 1986677);
		}
		if (type.equals("blender"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Blender Recipes", x + 15, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Blender Recipes", x + 162, 25, 1986677);
		}
		if (type.equals("toaster"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Toaster Recipes", x + 15, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Toaster Recipes", x + 162, 25, 1986677);
		}
		if (type.equals("washingmachine"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Washing Mac. Recipes", x + 5, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Washing Mac. Recipes", x + 152, 25, 1986677);
		}
		if (type.equals("microwave"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Microwave Recipes", x + 10, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Microwave Recipes", x + 157, 25, 1986677);
		}
		if (type.equals("dishwasher"))
		{
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Dishwasher Recipes", x + 5, 25, 1986677);
			gui.getFontRenderer().drawString(EnumChatFormatting.UNDERLINE + "Dishwasher Recipes", x + 152, 25, 1986677);
		}
	}

	private void drawDesc(GuiRecipeBook gui, int x, int y)
	{
		if (type.equals("printer"))
		{
			gui.getFontRenderer().drawString("All items below can be", x + 150, 40, -16731470);
			gui.getFontRenderer().drawString("used in the printer.", x + 150, 50, -16731470);

			gui.getFontRenderer().drawString("All items below can be", x + 4, 40, -16731470);
			gui.getFontRenderer().drawString("used in the printer.", x + 4, 50, -16731470);
		}
		if (type.equals("washingmachine"))
		{
			gui.getFontRenderer().drawString("All items below can be", x + 150, 40, -16731470);
			gui.getFontRenderer().drawString("repaired in the wash-", x + 150, 50, -16731470);
			gui.getFontRenderer().drawString("ing machine.", x + 150, 60, -16731470);

			gui.getFontRenderer().drawString("All items below can be", x + 4, 40, -16731470);
			gui.getFontRenderer().drawString("repaired in the wash-", x + 4, 50, -16731470);
			gui.getFontRenderer().drawString("ing machine.", x + 4, 60, -16731470);
		}
		if (type.equals("dishwasher"))
		{
			gui.getFontRenderer().drawString("All items below can be", x + 150, 40, -16731470);
			gui.getFontRenderer().drawString("repaired in the dish-", x + 150, 50, -16731470);
			gui.getFontRenderer().drawString("washer.", x + 150, 60, -16731470);

			gui.getFontRenderer().drawString("All items below can be", x + 4, 40, -16731470);
			gui.getFontRenderer().drawString("repaired in the dish-", x + 4, 50, -16731470);
			gui.getFontRenderer().drawString("washer.", x + 4, 60, -16731470);
		}
	}

	private void drawOven(Minecraft mc, GuiRecipeBook gui, RecipeData data, int x, int y)
	{
		gui.getItemRenderer().zLevel = 100.0F;
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), x, y);
		gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getInput(), x, y);
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getOutput(), x + 60, y);
		gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getOutput(), x + 60, y);
		gui.getItemRenderer().zLevel = 0.0F;
	}

	private void drawMineBay(Minecraft mc, GuiRecipeBook gui, RecipeData data, int x, int y)
	{
		gui.drawTag(x + 42, y - 1);
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), x + 20, y);
		gui.getItemRenderer().renderItemOverlayIntoGUI(gui.getFontRenderer(), data.getInput(), x + 20, y, null);
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getCurrency(), x + 51, y);
		gui.getItemRenderer().renderItemOverlayIntoGUI(gui.getFontRenderer(), data.getCurrency(), x + 51, y, null);
		gui.getFontRenderer().drawString("x" + Integer.toString(data.getPrice()), x + 68, y + 4, 0);
	}

	private void drawPrinter(Minecraft mc, GuiRecipeBook gui, RecipeData data, int x, int y)
	{
		gui.getItemRenderer().zLevel = 100.0F;
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), x, y);
		gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getInput(), x, y);
		gui.getItemRenderer().zLevel = 0.0F;
	}

	private ItemStack knife = new ItemStack(FurnitureItems.itemKnife);

	private void drawChoppingBoard(Minecraft mc, GuiRecipeBook gui, RecipeData data, int x, int y)
	{
		gui.getItemRenderer().zLevel = 100.0F;
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), x, y);
		gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getInput(), x, y);
		gui.drawKnife(x + 4, y - 6);
		gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getOutput(), x + 60, y);
		gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getOutput(), x + 60, y);
		gui.getItemRenderer().zLevel = 0.0F;
	}

	private void drawBlender(Minecraft mc, GuiRecipeBook gui, RecipeData data, int x, int y)
	{
		gui.getFontRenderer().drawString(fixName(data.getDrinkName()), x, y, 0);
		for (int i = 0; i < data.getIngredients().size(); i++)
		{
			if (data.getIngredients().get(i) != null)
			{
				gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getIngredients().get(i), x + ((i % 2) * 18), y + ((i / 2) * 18) + 10);
				gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getIngredients().get(i), x + ((i % 2) * 18), y + ((i / 2) * 18) + 10);
			}
		}
		gui.drawProgressArrow(x + 45, y + 20);
		gui.getItemRenderer().renderItemAndEffectIntoGUI(getDrink(data.getDrinkName(), data.getRed(), data.getGreen(), data.getBlue()), x + 80, y + 20);
		gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), getDrink(data.getDrinkName(), data.getRed(), data.getGreen(), data.getBlue()), x + 80, y + 20);
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
		drink.setStackDisplayName(name);
		drink.getTagCompound().setIntArray("Colour", new int[] { red, green, blue });
		return drink;
	}

	public void drawToolTip(GuiRecipeBook gui, Minecraft mc, int x, int y, int mouseX, int mouseY, ItemStack stack)
	{
		if (gui.func_146978_c(x, y, 16, 16, mouseX, mouseY))
		{
			gui.renderToolTip(stack, mouseX, mouseY);
		}
	}
}
