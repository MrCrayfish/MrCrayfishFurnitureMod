package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class PageContentsTwo extends RecipePage
{
    private GuiButton buttonGrill;

    private GuiRecipeBook book;

    public PageContentsTwo(GuiRecipeBook book)
    {
        super("contents");
        this.book = book;
    }

    @Override
    public void init(List<GuiButton> buttonList)
    {
    	String buttonText = I18n.format("cfm.recipe_book.button.go");
        int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
        this.buttonGrill = new GuiButton(0, leftPageCenter + 25, 40, 35, 20, buttonText);

        onClose();

        buttonList.add(this.buttonGrill);
    }

    @Override
    public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRenderer;
        fontRendererObj.drawString(I18n.format("tile.grill.name"), leftPageCenter - 55, 45, 16739840);
    }

    @Override
    public void handleButtonClick(GuiButton button)
    {
        if(button == buttonGrill)
        {
            book.gotoPage("grill");
        }
    }

    @Override
    public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public void onShown()
    {
        this.buttonGrill.visible = true;
    }

    @Override
    public void onClose()
    {
        this.buttonGrill.visible = false;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("cfm.recipe_book.page.contents");
    }
}