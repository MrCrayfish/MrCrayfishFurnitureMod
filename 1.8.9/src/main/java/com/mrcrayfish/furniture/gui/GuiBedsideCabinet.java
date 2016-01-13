/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
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

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerBedsideCabinet;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBedsideCabinet extends GuiContainer
{
	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/bedsidecabinet.png");

	public GuiBedsideCabinet(IInventory par1IInventory, IInventory par2IInventory)
	{
		super(new ContainerBedsideCabinet(par1IInventory, par2IInventory));
		this.upperChestInventory = par1IInventory;
		this.lowerChestInventory = par2IInventory;
		this.allowUserInput = false;
		this.xSize = 176;
		this.ySize = 168;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(lowerChestInventory.getName(), (this.xSize / 2) - 28, 6, 4210752);
		this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}
}
