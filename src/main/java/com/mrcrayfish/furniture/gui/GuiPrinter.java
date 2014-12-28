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

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerPrinter;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;

public class GuiPrinter extends GuiContainer
{
	private TileEntityPrinter printerInventory;
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/printer.png");

	public GuiPrinter(InventoryPlayer inventoryplayer, TileEntityPrinter tileentityprinter)
	{
		super(new ContainerPrinter(inventoryplayer, tileentityprinter));
		this.printerInventory = tileentityprinter;
		this.xSize = 176;
		this.ySize = 187;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, (ySize - 116) + 13, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
		int var7;

		if (this.printerInventory.isPrinting())
		{
			var7 = this.printerInventory.getPrintTimeRemainingScaled(16);
			drawTexturedModalRect(l + 74, (i1 + 38) - var7, 179, 16 - var7, 3, var7);
		}
		if (this.printerInventory.getStackInSlot(0) != null && this.printerInventory.isPrinting())
		{
			var7 = this.printerInventory.getPrintingProgressScaled(16);
			drawTexturedModalRect(l + 80, (i1 + 37) - var7, 176, 15 - var7, 3, var7 + 1);
		}
	}
}
