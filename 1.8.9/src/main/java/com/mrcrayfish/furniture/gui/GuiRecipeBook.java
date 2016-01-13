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

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiRecipeBook extends GuiScreen
{
	private static final ResourceLocation page_1 = new ResourceLocation("cfm:textures/gui/recipebook_1.png");
	private static final ResourceLocation page_2 = new ResourceLocation("cfm:textures/gui/recipebook_2.png");

	private int bookWidth = 146;
	private int bookHeight = 180;

	private int xSize = bookWidth * 2;

	private NextPageButton buttonNextPage;
	private NextPageButton buttonPreviousPage;
	private GuiButton link;
	private GuiButton contents;

	private GuiButton oven;
	private GuiButton freezer;
	private GuiButton minebay;
	private GuiButton printer;
	private GuiButton choppingboard;
	private GuiButton toaster;
	private GuiButton blender;
	private GuiButton microwave;
	private GuiButton dishwasher;
	private GuiButton washingmachine;

	private HashMap<Integer, RecipePage> pages = new HashMap<Integer, RecipePage>();
	private int pageCount = 1;
	private int pageNum;
	private int progress = 0;
	private long previousTime;

	public GuiRecipeBook()
	{
		compilePages();
	}

	public void compilePages()
	{
		ArrayList<RecipeData> oven = Recipes.getRecipes("oven");
		for (int i = 0; i < oven.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(oven.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("oven"));
				pages.get(pageCount).addRecipe(oven.get(i));
			}
		}

		ArrayList<RecipeData> freezer = Recipes.getRecipes("freezer");
		for (int i = 0; i < freezer.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(freezer.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("freezer"));
				pages.get(pageCount).addRecipe(freezer.get(i));
			}
		}

		ArrayList<RecipeData> minebay = Recipes.getRecipes("minebay");
		for (int i = 0; i < minebay.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(minebay.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("minebay"));
				pages.get(pageCount).addRecipe(minebay.get(i));
			}
		}

		ArrayList<RecipeData> printer = Recipes.getRecipes("printer");
		for (int i = 0; i < printer.size(); i++)
		{
			if (i % 40 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(printer.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("printer"));
				pages.get(pageCount).addRecipe(printer.get(i));
			}
		}

		ArrayList<RecipeData> choppingboard = Recipes.getRecipes("choppingboard");
		for (int i = 0; i < choppingboard.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(choppingboard.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("choppingboard"));
				pages.get(pageCount).addRecipe(choppingboard.get(i));
			}
		}

		ArrayList<RecipeData> toaster = Recipes.getRecipes("toaster");
		for (int i = 0; i < toaster.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(toaster.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("toaster"));
				pages.get(pageCount).addRecipe(toaster.get(i));
			}
		}

		ArrayList<RecipeData> blender = Recipes.getRecipes("blender");
		for (int i = 0; i < blender.size(); i++)
		{
			if (i % 4 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(blender.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("blender"));
				pages.get(pageCount).addRecipe(blender.get(i));
			}
		}

		ArrayList<RecipeData> microwave = Recipes.getRecipes("microwave");
		for (int i = 0; i < microwave.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(microwave.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("microwave"));
				pages.get(pageCount).addRecipe(microwave.get(i));
			}
		}

		ArrayList<RecipeData> dishwasher = Recipes.getRecipes("dishwasher");
		for (int i = 0; i < dishwasher.size(); i++)
		{
			if (i % 40 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(dishwasher.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("dishwasher"));
				pages.get(pageCount).addRecipe(dishwasher.get(i));
			}
		}

		ArrayList<RecipeData> washingmachine = Recipes.getRecipes("washingmachine");
		for (int i = 0; i < washingmachine.size(); i++)
		{
			if (i % 40 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(washingmachine.get(i));
			}
			else
			{
				pages.put(pageCount, new RecipePage("washingmachine"));
				pages.get(pageCount).addRecipe(washingmachine.get(i));
			}
		}
	}

	public void initGui()
	{
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		int i = (this.width - 18) / 2;
		this.buttonNextPage = new NextPageButton(1, i + 110, 160, true);
		this.buttonPreviousPage = new NextPageButton(2, i - 110, 160, false);
		this.link = new GuiButton(3, i + 50, 135, 60, 20, "Click here");
		this.oven = new GuiButton(4, i, 0, 20, 20, "Go");
		this.freezer = new GuiButton(5, i, 0, 20, 20, "Go");
		this.minebay = new GuiButton(6, i, 0, 20, 20, "Go");
		this.printer = new GuiButton(7, i, 0, 20, 20, "Go");
		this.choppingboard = new GuiButton(8, i, 0, 20, 20, "Go");
		this.toaster = new GuiButton(9, i, 0, 20, 20, "Go");
		this.blender = new GuiButton(10, i, 0, 20, 20, "Go");
		this.microwave = new GuiButton(11, i, 0, 20, 20, "Go");
		this.dishwasher = new GuiButton(12, i, 0, 20, 20, "Go");
		this.washingmachine = new GuiButton(13, i, 0, 20, 20, "Go");
		this.contents = new GuiButton(14, i - 60, 152, 50, 20, "Contents");

		this.buttonList.add(this.buttonNextPage);
		this.buttonList.add(this.buttonPreviousPage);
		this.buttonList.add(this.link);
		this.buttonList.add(this.oven);
		this.buttonList.add(this.freezer);
		this.buttonList.add(this.minebay);
		this.buttonList.add(this.printer);
		this.buttonList.add(this.choppingboard);
		this.buttonList.add(this.toaster);
		this.buttonList.add(this.blender);
		this.buttonList.add(this.microwave);
		this.buttonList.add(this.dishwasher);
		this.buttonList.add(this.washingmachine);
		this.buttonList.add(this.contents);
		updateButtons();
	}

	public void updateButtons()
	{
		if (pageNum == 0)
		{
			this.buttonPreviousPage.visible = false;
			this.link.visible = true;
			this.contents.visible = false;
			disableContentsButtons();
		}
		else if (pageNum == 1)
		{
			this.buttonPreviousPage.visible = true;
			if (pageNum == pages.size() + 1)
			{
				this.buttonNextPage.visible = false;
			}
			else
			{
				this.buttonNextPage.visible = true;
			}
			this.link.visible = false;
			this.contents.visible = false;
			enableContentsButtons();
		}
		else
		{
			this.buttonPreviousPage.visible = true;
			if (pageNum == pages.size() + 1)
			{
				this.buttonNextPage.visible = false;
			}
			else
			{
				this.buttonNextPage.visible = true;
			}
			this.link.visible = false;
			this.contents.visible = true;
			disableContentsButtons();
		}
	}

	public void disableContentsButtons()
	{
		this.oven.visible = false;
		this.freezer.visible = false;
		this.minebay.visible = false;
		this.printer.visible = false;
		this.choppingboard.visible = false;
		this.toaster.visible = false;
		this.blender.visible = false;
		this.microwave.visible = false;
		this.dishwasher.visible = false;
		this.washingmachine.visible = false;
	}

	public void enableContentsButtons()
	{
		this.oven.visible = true;
		this.freezer.visible = true;
		this.minebay.visible = true;
		this.printer.visible = true;
		this.choppingboard.visible = true;
		this.toaster.visible = true;
		this.blender.visible = true;
		this.microwave.visible = true;
		this.dishwasher.visible = true;
		this.washingmachine.visible = true;
	}

	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case 1:
			pageNum++;
			if (pageNum + 1 > pages.size() + 1)
			{
				pageNum = pages.size() + 1;
			}
			break;
		case 2:
			pageNum--;
			if (pageNum < 0)
			{
				pageNum = 0;
			}
			break;
		case 3:
			openTutorial();
			break;
		case 4:
			gotoPage("oven");
			break;
		case 5:
			gotoPage("freezer");
			break;
		case 6:
			gotoPage("minebay");
			break;
		case 7:
			gotoPage("printer");
			break;
		case 8:
			gotoPage("choppingboard");
			break;
		case 9:
			gotoPage("toaster");
			break;
		case 10:
			gotoPage("blender");
			break;
		case 11:
			gotoPage("microwave");
			break;
		case 12:
			gotoPage("dishwasher");
			break;
		case 13:
			gotoPage("washingmachine");
			break;
		case 14:
			pageNum = 1;
			break;
		}
		this.updateButtons();
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		this.pages = null;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int k = (this.width - this.bookWidth) / 2;

		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(k - bookWidth / 2, 5, 0, 0, bookWidth, bookHeight);

		this.mc.getTextureManager().bindTexture(page_2);
		this.drawTexturedModalRect(k + bookWidth / 2, 5, 0, 0, bookWidth, bookHeight);

		if (pageNum == 0)
		{
			this.mc.getTextureManager().bindTexture(page_1);
			this.drawTexturedModalRect((this.width / 2) - (this.bookWidth / 2) - (93 / 2), 50, 146, 0, 93, 25);
			this.drawTexturedModalRect((this.width / 2) - (this.bookWidth / 2) - (66 / 2), 75, 146, 25, 66, 22);
		}

		super.drawScreen(par1, par2, par3);

		this.updatePage(par1, par2);

		long currentTime = System.currentTimeMillis();
		if (currentTime > previousTime)
		{
			previousTime = currentTime + 10;
			progress++;
			if (progress >= 160)
			{
				progress = 0;
			}
		}
	}

	public void updatePage(int mouseX, int mouseY)
	{
		int k = this.width / 2;
		if (pageNum == 0)
		{
			this.drawMainPage();
		}
		else if (pageNum == 1)
		{
			this.drawContents();
		}
		else
		{
			if (pages.containsKey(pageNum))
			{
				this.pages.get(pageNum).drawPage(mc, this, k - 130, 40, mouseX, mouseY);
			}
		}
	}

	public void gotoPage(String type)
	{
		for (int i = 2; i < 2 + pages.size(); i++)
		{
			if (pages.get(i) != null)
			{
				if (pages.get(i).type.equalsIgnoreCase(type))
				{
					this.pageNum = i;
					break;
				}
			}
		}
	}

	public void drawMainPage()
	{
		int center = (this.width) / 2;
		this.fontRendererObj.drawString("by MrCrayfish", center - 108, 105, 1986677);
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + "About", center + 55, 25, 1986677);
		this.fontRendererObj.drawString("This book contains all", center + 18, 40, -16731470);
		this.fontRendererObj.drawString("recipes registered in", center + 18, 50, -16731470);
		this.fontRendererObj.drawString("the RecipeAPI. It will", center + 18, 60, -16731470);
		this.fontRendererObj.drawString("help you discover", center + 18, 70, -16731470);
		this.fontRendererObj.drawString("many hidden recipes.", center + 18, 80, -16731470);
		this.fontRendererObj.drawString("Want to add your own", center + 18, 110, 0);
		this.fontRendererObj.drawString("custom recipes?", center + 18, 120, 0);
	}

	public void drawContents()
	{
		int center_1 = (this.width) / 2 - (this.bookWidth / 2);
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + "Contents", center_1 - 22, 25, 1986677);
		this.drawContentsLink(oven, "Oven", "oven", center_1 - 55, 45);
		this.drawContentsLink(freezer, "Freezer", "freezer", center_1 - 55, 45 + (1 * 24));
		this.drawContentsLink(minebay, "MineBay", "minebay", center_1 - 55, 45 + (2 * 24));
		this.drawContentsLink(printer, "Printer", "printer", center_1 - 55, 45 + (3 * 24));
		this.drawContentsLink(choppingboard, "Chopping Board", "choppingboard", center_1 - 55, 45 + (4 * 24));
		int center_2 = (this.width) / 2 + (this.bookWidth / 2);
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + "Contents", center_2 - 22, 25, 1986677);
		this.drawContentsLink(toaster, "Toaster", "toaster", center_2 - 55, 45);
		this.drawContentsLink(blender, "Blender", "blender", center_2 - 55, 45 + (1 * 24));
		this.drawContentsLink(microwave, "Microwave", "microwave", center_2 - 55, 45 + (2 * 24));
		this.drawContentsLink(dishwasher, "Dishwasher", "dishwasher", center_2 - 55, 45 + (3 * 24));
		this.drawContentsLink(washingmachine, "Washing Machine", "washingmachine", center_2 - 55, 45 + (4 * 24));
	}

	public void drawContentsLink(GuiButton button, String name, String type, int x, int y)
	{
		this.fontRendererObj.drawString(name, x, y, -16731470);
		button.xPosition = x + 90;
		button.yPosition = y - 5;
	}

	public void drawOvenRecipes()
	{

	}

	public void drawPlainArrow(int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(x, y, 36, 180, 22, 15);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void drawProgressArrow(int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(x, y, 36, 180, 22, 15);
		int percent = progress * 22 / 160;
		this.drawTexturedModalRect(x, y, 58, 180, percent + 1, 16);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void drawTag(int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(x, y, 0, 200, 45, 18);
		GL11.glPopMatrix();
	}

	private ItemStack knife = new ItemStack(FurnitureItems.itemKnife);

	public void drawKnife(int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		int percent = progress * 16 / 160;
		if (percent >= 12)
			percent = 12;
		getItemRenderer().renderItemAndEffectIntoGUI(knife, x, y + percent);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public RenderItem getItemRenderer()
	{
		return itemRender;
	}

	public FontRenderer getFontRenderer()
	{
		return fontRendererObj;
	}

	public void openTutorial()
	{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null)
		{
			try
			{
				desktop.browse(new URL("http://mrcrayfishs-furniture-mod.wikia.com/wiki/RecipeAPI").toURI());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_)
	{
		return p_146978_5_ >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && p_146978_6_ >= p_146978_2_ - 1 && p_146978_6_ < p_146978_2_ + p_146978_4_ + 1;
	}

	protected void renderToolTip(ItemStack itemStack, int mouseX, int mouseY)
	{
		List list = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

		for (int k = 0; k < list.size(); ++k)
		{
			if (k == 0)
			{
				list.set(k, itemStack.getRarity().rarityColor + (String) list.get(k));
			}
			else
			{
				list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
			}
		}

		FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
		drawHoveringText(list, mouseX, mouseY, (font == null ? fontRendererObj : font));
	}

	@SideOnly(Side.CLIENT)
	static class NextPageButton extends GuiButton
	{
		private final boolean rightButton;
		private static final String __OBFID = "CL_00000745";

		public NextPageButton(int id, int posX, int posY, boolean rightButton)
		{
			super(id, posX, posY, 18, 10, "");
			this.rightButton = rightButton;
		}

		/**
		 * Draws this button to the screen.
		 */
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(GuiRecipeBook.page_1);

				int u = 0;
				int v = 180;
				boolean flag = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

				if (flag)
				{
					u += width;
				}

				if (!this.rightButton)
				{
					v += height;
				}

				this.drawTexturedModalRect(xPosition, yPosition, u, v, width, height);
			}
		}
	}
}
