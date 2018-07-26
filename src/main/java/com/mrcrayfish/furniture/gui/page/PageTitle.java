package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
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
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title1"), center - 108, 105, 1986677);
        fontRendererObj.drawString(TextFormatting.UNDERLINE + I18n.format("cfm.recipe_book.page.title2"), center + 55, 25, 1986677);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title3"), center + 18, 40, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title4"), center + 18, 50, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title5"), center + 18, 60, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title6"), center + 18, 70, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title7"), center + 18, 80, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title8"), center + 18, 110, 0);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.title9"), center + 18, 120, 0);
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
