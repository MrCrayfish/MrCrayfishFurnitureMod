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
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.page.*;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiRecipeBook extends GuiScreen
{
	private static final ResourceLocation page_1 = new ResourceLocation("cfm:textures/gui/recipebook_1.png");
	private static final ResourceLocation page_2 = new ResourceLocation("cfm:textures/gui/recipebook_2.png");

	public int bookWidth = 146;
	public int bookHeight = 180;

	private int xSize = bookWidth * 2;

	private NextPageButton buttonNextPage;
	private NextPageButton buttonPreviousPage;
	private GuiButton buttonTutorial;
	private GuiButton buttonContents;

	private HashMap<Integer, RecipePage> pages = new HashMap<Integer, RecipePage>();
	private int pageCount = 0;
	private int pageNum = 0;
	private int progress = 0;

	public GuiRecipeBook()
	{
		compilePages();
	}

	public void compilePages()
	{
		PageTitle titlePage = new PageTitle(this);
		pages.put(pageCount++, titlePage);
		
		PageContentsOne contentsPageOne = new PageContentsOne(this);
		pages.put(pageCount++, contentsPageOne);
		
		PageContentsTwo contentsPageTwo = new PageContentsTwo(this);
		pages.put(pageCount, contentsPageTwo);
		
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
				pages.put(pageCount, new PageOven());
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
				pages.put(pageCount, new PageFreezer());
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
				pages.put(pageCount, new PageMineBay());
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
				pages.put(pageCount, new PagePrinter());
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
				pages.put(pageCount, new PageChoppingBoard());
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
				pages.put(pageCount, new PageToaster());
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
				pages.put(pageCount, new PageBlender());
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
				pages.put(pageCount, new PageMicrowave());
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
				pages.put(pageCount, new PageDishwasher());
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
				pages.put(pageCount, new PageWashingMachine());
				pages.get(pageCount).addRecipe(washingmachine.get(i));
			}
		}
		
		ArrayList<RecipeData> grill = Recipes.getRecipes("grill");
		for (int i = 0; i < grill.size(); i++)
		{
			if (i % 8 == 0)
			{
				pageCount++;
			}

			if (pages.containsKey(pageCount))
			{
				pages.get(pageCount).addRecipe(grill.get(i));
			}
			else
			{
				pages.put(pageCount, new PageGrill());
				pages.get(pageCount).addRecipe(grill.get(i));
			}
		}
	}

	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);

		int i = (this.width - 18) / 2;
		this.buttonNextPage = new NextPageButton(1, i + 110, 160, true);
		this.buttonPreviousPage = new NextPageButton(2, i - 110, 160, false);
		this.buttonTutorial = new GuiButton(3, i + 50, 135, 60, 20, "Click here");
		this.buttonContents = new GuiButton(14, i - 60, 152, 50, 20, "Contents");

		this.buttonList.add(buttonNextPage);
		this.buttonList.add(buttonPreviousPage);
		this.buttonList.add(buttonTutorial);
		this.buttonList.add(buttonContents);
		
		for(RecipePage page : pages.values())
		{
			page.init(buttonList);
		}
		
		updateButtons();
	}

	public void updateButtons()
	{
		if (pageNum == 0)
		{
			this.buttonPreviousPage.visible = false;
			this.buttonTutorial.visible = true;
			this.buttonContents.visible = false;
		}
		else
		{
			this.buttonPreviousPage.visible = true;
			if (pageNum == pages.size() - 1)
			{
				this.buttonNextPage.visible = false;
			}
			else
			{
				this.buttonNextPage.visible = true;
			}
			this.buttonTutorial.visible = false;
			if(pageNum >= 3)
			{
				this.buttonContents.visible = true;
			}
			else
			{
				this.buttonContents.visible = false;
			}
		}
	}
	
	protected void actionPerformed(GuiButton button)
	{
		pages.get(pageNum).onClose();
		
		if(button == buttonNextPage)
		{
			pageNum++;
			if (pageNum > pages.size() - 1)
			{
				pageNum = pages.size() - 1;
			}
		}
		else if(button == buttonPreviousPage)
		{
			pageNum--;
			if (pageNum < 0)
			{
				pageNum = 0;
			}
		}
		else if(button == buttonContents)
		{
			gotoPage("contents");
		}
		else if(button == buttonTutorial)
		{
			openTutorial();
		}
		else
		{
			for(RecipePage page : pages.values())
			{
				page.handleButtonClick(button);
			}
		}
		pages.get(pageNum).onShown();
		this.updateButtons();
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		this.pages = null;
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		progress++;
		if (progress >= 160)
		{
			progress = 0;
		}
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

		this.updatePage(par1, par2, par3);
	}

	public void updatePage(int mouseX, int mouseY, float partialTicks)
	{
		int k = this.width / 2;
		RecipePage page = pages.get(pageNum);
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		if(page.shouldDrawTitle()) 
		{
			fontRenderer.drawString(TextFormatting.BOLD + page.getTitle(), k - bookWidth + 20, 26, 1986677, false);
			fontRenderer.drawString(TextFormatting.BOLD + page.getTitle(), k + 20, 26, 1986677, false);
		}
		page.draw(this, k - 130, 40, mouseX, mouseY, partialTicks);
		page.drawOverlay(mc, this, k - 130, 40, mouseX, mouseY, partialTicks);
		GL11.glPopMatrix();
	}

	public void gotoPage(String type)
	{
		for (int i = 0; i < pages.size(); i++)
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

	public void drawPlainArrow(int x, int y)
	{
		GL11.glPushMatrix();
		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(x, y, 36, 180, 22, 15);
		GL11.glPopMatrix();
	}

	public void drawProgressArrow(int x, int y, float partialTicks)
	{
		GL11.glPushMatrix();
		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(x, y, 36, 180, 22, 15);
		int percent = (int) (progress + partialTicks) * 22 / 160;
		this.drawTexturedModalRect(x, y, 58, 180, percent + 1, 16);
		GL11.glPopMatrix();
	}

	public void drawTag(int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(page_1);
		this.drawTexturedModalRect(x, y, 0, 200, 45, 18);
		GL11.glPopMatrix();
	}

	private ItemStack knife = new ItemStack(FurnitureItems.itemKnife);

	public void drawKnife(int x, int y)
	{
		GL11.glPushMatrix();
		int percent = progress * 16 / 160;
		if (percent >= 12)
			percent = 12;
		getItemRenderer().renderItemAndEffectIntoGUI(knife, x, y + percent);
		GL11.glPopMatrix();
	}

	public RenderItem getItemRenderer()
	{
		return itemRender;
	}

	public FontRenderer getFontRenderer()
	{
		return fontRenderer;
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

	public boolean isMouseWithin(int posX, int posY, int width, int height, int mouseX, int mouseY)
	{
		return mouseX >= posX - 1 && mouseX < posX + width + 1 && mouseY >= posY - 1 && mouseY < posY + height + 1;
	}

	public void renderToolTip(ItemStack itemStack, int mouseX, int mouseY)
	{
		List list = itemStack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

		for (int k = 0; k < list.size(); ++k)
		{
			if (k == 0)
			{
				list.set(k, itemStack.getRarity().rarityColor + (String) list.get(k));
			}
			else
			{
				list.set(k, TextFormatting.GRAY + (String) list.get(k));
			}
		}

		FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
		drawHoveringText(list, mouseX, mouseY, (font == null ? fontRenderer : font));
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
				boolean flag = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

				if (flag)
				{
					u += width;
				}

				if (!this.rightButton)
				{
					v += height;
				}

				this.drawTexturedModalRect(x, y, u, v, width, height);
			}
		}
	}
}
