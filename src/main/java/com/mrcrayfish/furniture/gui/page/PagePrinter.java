package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;

public class PagePrinter extends RecipePage
{
	public PagePrinter()
	{
		super("printer"); 
	}

	@Override
	public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		gui.getFontRenderer().drawString("All items below can be", x + 150, 40, 16739840);
		gui.getFontRenderer().drawString("used in the printer.", x + 150, 50, 16739840);
		
		gui.getFontRenderer().drawString("All items below can be", x + 4, 40, 16739840);
		gui.getFontRenderer().drawString("used in the printer.", x + 4, 50, 16739840);
		
		for (int i = 0; i < recipes.size(); i++)
		{
			int px = x + ((i % 5) * 20) + ((i / 20) * 145) + 7;
			int py = y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30;
			
			RecipeData data = recipes.get(i);
			gui.getItemRenderer().zLevel = 100.0F;
			gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), px, py);
			gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getInput(), px, py);
			gui.getItemRenderer().zLevel = 0.0F;
		}
	}
	
	@Override
	public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			drawToolTip(gui, mc, x + ((i % 5) * 20) + ((i / 20) * 145) + 7, y + ((i / 5) * 20) - ((i / 20) * (4 * 20)) + 30, mouseX, mouseY, recipes.get(i).getInput());
		}
	}

	@Override
	public String getTitle()
	{
		return "Printer";
	}
}
