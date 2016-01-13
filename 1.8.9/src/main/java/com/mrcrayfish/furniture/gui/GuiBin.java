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

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerBin;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageEmptyBin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBin extends GuiContainer
{
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/bin.png");
	private GuiButton button_empty;
	private int tileX, tileY, tileZ;

	public GuiBin(IInventory playerInventory, IInventory binInventory, int tileX, int tileY, int tileZ)
	{
		super(new ContainerBin(playerInventory, binInventory));
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileZ = tileZ;
		this.xSize = 176;
		this.ySize = 197;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);
		buttonList.clear();
		int posX = width / 2;
		int posY = height / 2;
		button_empty = new GuiButton(0, posX + 40, posY - 50, 40, 20, "Empty");
		button_empty.enabled = true;
		buttonList.add(button_empty);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (!guibutton.enabled)
		{
			return;
		}
		if (guibutton.id == 0)
		{
			this.emptyBin();
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k)
	{
		try
		{
			super.mouseClicked(i, j, k);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void emptyBin()
	{
		PacketHandler.INSTANCE.sendToServer(new MessageEmptyBin(tileX, tileY, tileZ));
	}
}
