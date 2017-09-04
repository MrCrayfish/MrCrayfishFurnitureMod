package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;

public class PageChoppingBoard extends RecipePage
{
	public PageChoppingBoard()
	{
		super("choppingboard");
	}

	@Override
	public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			int px = x + ((i / 4) * 150) + 15;
			int py = y + (i % 4 * spacing);
			
			RecipeData data = recipes.get(i);
			gui.getItemRenderer().zLevel = 100.0F;
			gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), px, py);
			gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getInput(), px, py);
			gui.drawKnife(px + 4, py - 6);
			gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getOutput(), px + 60, py);
			gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getOutput(), px + 60, py);
			gui.getItemRenderer().zLevel = 0.0F;
			
			gui.drawPlainArrow(px + 27, py + 2);
		}
	}
	
	@Override
	public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			drawToolTip(gui, mc, x + ((i / 4) * 150) + 15, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
			drawToolTip(gui, mc, x + ((i / 4) * 150) + 75, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getOutput());
		}
	}

	@Override
	public String getTitle()
	{
		return "Chopping Board";
	}
}
