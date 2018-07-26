package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class PageContentsOne extends RecipePage
{
    private GuiButton buttonOven;
    private GuiButton buttonFreezer;
    private GuiButton buttonMineBay;
    private GuiButton buttonPrinter;
    private GuiButton buttonChoppingBoard;
    private GuiButton buttonToaster;
    private GuiButton buttonBlender;
    private GuiButton buttonMicrowave;
    private GuiButton buttonDishwasher;
    private GuiButton buttonWashingMachine;

    private GuiRecipeBook book;

    public PageContentsOne(GuiRecipeBook book)
    {
        super("contents");
        this.book = book;
    }

    @Override
    public void init(List<GuiButton> buttonList)
    {
        String buttonText = I18n.format("cfm.recipe_book.button.go");
        int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
        this.buttonOven = new GuiButton(0, leftPageCenter + 25, 40, 35, 20, buttonText);
        this.buttonFreezer = new GuiButton(0, leftPageCenter + 25, 64, 35, 20, buttonText);
        this.buttonMineBay = new GuiButton(0, leftPageCenter + 25, 88, 35, 20, buttonText);
        this.buttonPrinter = new GuiButton(0, leftPageCenter + 25, 112, 35, 20, buttonText);
        this.buttonChoppingBoard = new GuiButton(0, leftPageCenter + 25, 136, 35, 20, buttonText);

        int rightPageCenter = (book.width) / 2 + (book.bookWidth / 2);
        this.buttonToaster = new GuiButton(0, rightPageCenter + 25, 40, 35, 20, buttonText);
        this.buttonBlender = new GuiButton(0, rightPageCenter + 25, 64, 35, 20, buttonText);
        this.buttonMicrowave = new GuiButton(0, rightPageCenter + 25, 88, 35, 20, buttonText);
        this.buttonDishwasher = new GuiButton(0, rightPageCenter + 25, 112, 35, 20, buttonText);
        this.buttonWashingMachine = new GuiButton(0, rightPageCenter + 25, 136, 35, 20, buttonText);

        onClose();

        buttonList.add(this.buttonOven);
        buttonList.add(this.buttonFreezer);
        buttonList.add(this.buttonMineBay);
        buttonList.add(this.buttonPrinter);
        buttonList.add(this.buttonChoppingBoard);
        buttonList.add(this.buttonToaster);
        buttonList.add(this.buttonBlender);
        buttonList.add(this.buttonMicrowave);
        buttonList.add(this.buttonDishwasher);
        buttonList.add(this.buttonWashingMachine);
    }

    @Override
    public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRenderer;
        fontRendererObj.drawString(I18n.format("tile.oven.name"), leftPageCenter - 55, 45, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.freezer"), leftPageCenter - 55, 45 + (1 * 24), 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.minebay"), leftPageCenter - 55, 45 + (2 * 24), 16739840);
        fontRendererObj.drawString(I18n.format("tile.printer.name"), leftPageCenter - 55, 45 + (3 * 24), 16739840);
        fontRendererObj.drawString(I18n.format("tile.chopping_board.name"), leftPageCenter - 55, 45 + (4 * 24), 16739840);

        int rightPageCenter = (book.width) / 2 + (book.bookWidth / 2);
        fontRendererObj.drawString(I18n.format("tile.toaster.name"), rightPageCenter - 55, 45, 16739840);
        fontRendererObj.drawString(I18n.format("tile.blender.name"), rightPageCenter - 55, 45 + (1 * 24), 16739840);
        fontRendererObj.drawString(I18n.format("tile.microwave.name"), rightPageCenter - 55, 45 + (2 * 24), 16739840);
        fontRendererObj.drawString(I18n.format("tile.dishwasher.name"), rightPageCenter - 55, 45 + (3 * 24), 16739840);
        fontRendererObj.drawString(I18n.format("tile.washing_machine.name"), rightPageCenter - 55, 45 + (4 * 24), 16739840);
    }

    @Override
    public void handleButtonClick(GuiButton button)
    {
        if(button == buttonOven)
        {
            book.gotoPage("oven");
        }
        else if(button == buttonFreezer)
        {
            book.gotoPage("freezer");
        }
        else if(button == buttonMineBay)
        {
            book.gotoPage("minebay");
        }
        else if(button == buttonPrinter)
        {
            book.gotoPage("printer");
        }
        else if(button == buttonChoppingBoard)
        {
            book.gotoPage("choppingboard");
        }
        else if(button == buttonToaster)
        {
            book.gotoPage("toaster");
        }
        else if(button == buttonBlender)
        {
            book.gotoPage("blender");
        }
        else if(button == buttonMicrowave)
        {
            book.gotoPage("microwave");
        }
        else if(button == buttonDishwasher)
        {
            book.gotoPage("dishwasher");
        }
        else if(button == buttonWashingMachine)
        {
            book.gotoPage("washingmachine");
        }
    }

    @Override
    public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public void onShown()
    {
        this.buttonOven.visible = true;
        this.buttonFreezer.visible = true;
        this.buttonMineBay.visible = true;
        this.buttonPrinter.visible = true;
        this.buttonChoppingBoard.visible = true;
        this.buttonToaster.visible = true;
        this.buttonBlender.visible = true;
        this.buttonMicrowave.visible = true;
        this.buttonDishwasher.visible = true;
        this.buttonWashingMachine.visible = true;
    }

    @Override
    public void onClose()
    {
        this.buttonOven.visible = false;
        this.buttonFreezer.visible = false;
        this.buttonMineBay.visible = false;
        this.buttonPrinter.visible = false;
        this.buttonChoppingBoard.visible = false;
        this.buttonToaster.visible = false;
        this.buttonBlender.visible = false;
        this.buttonMicrowave.visible = false;
        this.buttonDishwasher.visible = false;
        this.buttonWashingMachine.visible = false;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("cfm.recipe_book.page.contents");
    }
}
