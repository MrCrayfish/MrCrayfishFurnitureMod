package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class PageMineBay extends RecipePage
{
    public PageMineBay()
    {
        super("minebay");
    }

    @Override
    public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            int px = x + ((i / 4) * 150);
            int py = y + (i % 4 * spacing);

            RecipeData data = recipes.get(i);
            gui.drawTag(px + 42, py - 1);
            gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getInput(), px + 20, py);
            gui.getItemRenderer().renderItemOverlayIntoGUI(gui.getFontRenderer(), data.getInput(), px + 20, py, null);
            gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getCurrency(), px + 51, py);
            gui.getItemRenderer().renderItemOverlayIntoGUI(gui.getFontRenderer(), data.getCurrency(), px + 51, py, null);
            gui.getFontRenderer().drawString("x" + Integer.toString(data.getPrice()), px + 68, py + 4, 0);
        }
    }

    @Override
    public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            drawToolTip(gui, mc, x + ((i / 4) * 150) + 20, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getInput());
            drawToolTip(gui, mc, x + ((i / 4) * 150) + 51, y + (i % 4 * spacing), mouseX, mouseY, recipes.get(i).getCurrency());
        }
    }

    @Override
    public String getTitle()
    {
        return I18n.format("cfm.recipe_book.page.minebay");
    }
}