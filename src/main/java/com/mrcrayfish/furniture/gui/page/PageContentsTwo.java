package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

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
		int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
		this.buttonGrill = new GuiButton(0, leftPageCenter + 35, 40, 20, 20, "Go");
		
		onClose();
 
		buttonList.add(this.buttonGrill);
	}

	@Override
	public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		fontRendererObj.drawString("Grill", leftPageCenter - 55, 45, 16739840);
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
		return "Contents";
	}
}
