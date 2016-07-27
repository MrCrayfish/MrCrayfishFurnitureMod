package com.mrcrayfish.furniture.gui.page;

import java.util.List;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

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
		int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
		this.buttonOven = new GuiButton(0, leftPageCenter + 35, 40, 20, 20, "Go");
		this.buttonFreezer = new GuiButton(0, leftPageCenter + 35, 64, 20, 20, "Go");
		this.buttonMineBay = new GuiButton(0, leftPageCenter + 35, 88, 20, 20, "Go");
		this.buttonPrinter = new GuiButton(0, leftPageCenter + 35, 112, 20, 20, "Go");
		this.buttonChoppingBoard = new GuiButton(0, leftPageCenter + 35, 136, 20, 20, "Go");
		
		int rightPageCenter = (book.width) / 2 + (book.bookWidth / 2);
		this.buttonToaster = new GuiButton(0, rightPageCenter + 35, 40, 20, 20, "Go");
		this.buttonBlender = new GuiButton(0, rightPageCenter + 35, 64, 20, 20, "Go");
		this.buttonMicrowave = new GuiButton(0, rightPageCenter + 35, 88, 20, 20, "Go");
		this.buttonDishwasher = new GuiButton(0, rightPageCenter + 35, 112, 20, 20, "Go");
		this.buttonWashingMachine = new GuiButton(0, rightPageCenter + 35, 136, 20, 20, "Go");
		
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
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		fontRendererObj.drawString("Oven", leftPageCenter - 55, 45, 16739840);
		fontRendererObj.drawString("Freezer", leftPageCenter - 55, 45 + (1 * 24), 16739840);
		fontRendererObj.drawString("MineBay", leftPageCenter - 55, 45 + (2 * 24), 16739840);
		fontRendererObj.drawString("Printer", leftPageCenter - 55, 45 + (3 * 24), 16739840);
		fontRendererObj.drawString("Chopping Board", leftPageCenter - 55, 45 + (4 * 24), 16739840);

		int rightPageCenter = (book.width) / 2 + (book.bookWidth / 2);
		fontRendererObj.drawString("Toaster", rightPageCenter - 55, 45, 16739840);
		fontRendererObj.drawString("Blender", rightPageCenter - 55, 45 + (1 * 24), 16739840);
		fontRendererObj.drawString("Microwave", rightPageCenter - 55, 45 + (2 * 24), 16739840);
		fontRendererObj.drawString("Dishwasher", rightPageCenter - 55, 45 + (3 * 24), 16739840);
		fontRendererObj.drawString("Washing Machine", rightPageCenter - 55, 45 + (4 * 24), 16739840);
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
		return "Contents";
	}
}
