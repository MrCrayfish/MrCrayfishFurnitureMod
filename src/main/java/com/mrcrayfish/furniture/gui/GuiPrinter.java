/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerPrinter;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;

public class GuiPrinter extends GuiContainer
{
	private TileEntityPrinter printer;
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/printer.png");

	public GuiPrinter(InventoryPlayer inventoryplayer, TileEntityPrinter tileentityprinter)
	{
		super(new ContainerPrinter(inventoryplayer, tileentityprinter));
		this.printer = tileentityprinter;
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if(isPointInRegion(73, 30, 5, 18, mouseX, mouseY))
		{
			drawHoveringText(Arrays.asList(new Object[]{"Ink Level: " + printer.printerPrintTime + "/" + printer.currentItemPrintTime}), mouseX, mouseY);
		}
		
		if(isPointInRegion(79, 30, 5, 18, mouseX, mouseY))
		{
			drawHoveringText(Arrays.asList(new Object[]{"Progress: " + printer.printingTime + "/" + printer.totalCookTime}), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, (ySize - 116) + 23, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize + 21);
		int var7;

		//TODO switch these around!
		if (this.printer.isPrinting())
		{
			var7 = this.getPrintTimeRemainingScaled(16);
			drawTexturedModalRect(l + 80, (i1 + 45) - var7, 176, 15 - var7, 3, var7 + 1);
		}
		if (this.printer.getStackInSlot(0) != null && this.printer.isPrinting())
		{
			var7 = this.getPrintingProgressScaled(16);
			drawTexturedModalRect(l + 74, (i1 + 46) - var7, 179, 16 - var7, 3, var7);
		}
	}
	
	private int getPrintTimeRemainingScaled(int scale)
    {
        int j = this.printer.getField(2);
        int k = this.printer.getField(3);
        return k != 0 && j != 0 ? j * scale / k : 0;
    }
	
	private int getPrintingProgressScaled(int scale)
    {
        int j = this.printer.getField(1);

        if (j == 0)
        {
            j = 200;
        }

        return this.printer.getField(0) * scale / j;
    }
}
