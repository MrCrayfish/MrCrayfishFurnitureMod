package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class PageBlender extends RecipePage
{
    public PageBlender()
    {
        super("blender");
    }

    @Override
    public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            int px = x + ((i / 2) * 150) + 8;
            int py = y + (i % 2 * 50) + 10;

            RecipeData data = recipes.get(i);
            gui.getFontRenderer().drawString(fixName(data.getDrinkName()), px, py, 16739840);
            for(int j = 0; j < data.getIngredients().size(); j++)
            {
                if(data.getIngredients().get(j) != null)
                {
                    gui.getItemRenderer().renderItemAndEffectIntoGUI(data.getIngredients().get(j), px + ((j % 2) * 18), py + ((j / 2) * 18) + 10);
                    gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), data.getIngredients().get(j), px + ((j % 2) * 18), py + ((j / 2) * 18) + 10);
                }
            }
            gui.drawProgressArrow(px + 45, py + 20, partialTicks);
            gui.getItemRenderer().renderItemAndEffectIntoGUI(getDrink(data.getDrinkName(), data.getRed(), data.getGreen(), data.getBlue()), px + 80, py + 20);
            gui.getItemRenderer().renderItemOverlays(gui.getFontRenderer(), getDrink(data.getDrinkName(), data.getRed(), data.getGreen(), data.getBlue()), px + 80, py + 20);
        }
    }

    @Override
    public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            int posX = x + ((i / 2) * 150) + 8;
            int posY = y + (i % 2 * 50) + 10;

            RecipeData data = recipes.get(i);
            if(gui.isMouseWithin(posX, posY + 10, 16, 16, mouseX, mouseY))
            {
                if(data.getIngredients().size() > 0)
                {
                    if(data.getIngredients().get(0) != null)
                    {
                        gui.renderToolTip(data.getIngredients().get(0), mouseX, mouseY);
                    }
                }
            }
            if(gui.isMouseWithin(posX + (18), posY + 10, 16, 16, mouseX, mouseY))
            {
                if(data.getIngredients().size() > 1)
                {
                    if(data.getIngredients().get(1) != null)
                    {
                        gui.renderToolTip(data.getIngredients().get(1), mouseX, mouseY);
                    }
                }
            }
            if(gui.isMouseWithin(posX, posY + (18) + 10, 16, 16, mouseX, mouseY))
            {
                if(data.getIngredients().size() > 2)
                {
                    if(data.getIngredients().get(2) != null)
                    {
                        gui.renderToolTip(data.getIngredients().get(2), mouseX, mouseY);
                    }
                }
            }
            if(gui.isMouseWithin(posX + (18), posY + (18) + 10, 16, 16, mouseX, mouseY))
            {
                if(data.getIngredients().size() > 3)
                {
                    if(data.getIngredients().get(3) != null)
                    {
                        gui.renderToolTip(data.getIngredients().get(3), mouseX, mouseY);
                    }
                }
            }

            drawToolTip(gui, mc, posX + 80, posY + 20, mouseX, mouseY, getDrink(data.getDrinkName(), data.getRed(), data.getGreen(), data.getBlue()));
        }
    }

    @Override
    public String getTitle()
    {
        return I18n.format("tile.blender.name");
    }


}