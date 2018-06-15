package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextFormatting;

public class PageTitle extends RecipePage
{
    private GuiRecipeBook book;

    public PageTitle(GuiRecipeBook book)
    {
        super("title");
        this.book = book;
    }

    @Override
    public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        int center = (book.width) / 2;
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRenderer;
        fontRendererObj.drawString("by MrCrayfish", center - 108, 105, 1986677);
        fontRendererObj.drawString(TextFormatting.UNDERLINE + "About", center + 55, 25, 1986677);
        fontRendererObj.drawString("This book contains all", center + 18, 40, 16739840);
        fontRendererObj.drawString("recipes registered in", center + 18, 50, 16739840);
        fontRendererObj.drawString("the RecipeAPI. It will", center + 18, 60, 16739840);
        fontRendererObj.drawString("help you discover", center + 18, 70, 16739840);
        fontRendererObj.drawString("many hidden recipes.", center + 18, 80, 16739840);
        fontRendererObj.drawString("Want to add your own", center + 18, 110, 0);
        fontRendererObj.drawString("custom recipes?", center + 18, 120, 0);
    }

    @Override
    public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public String getTitle()
    {
        return null;
    }

    @Override
    public boolean shouldDrawTitle()
    {
        return false;
    }
}
