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

import com.mrcrayfish.furniture.blocks.BlockCabinet;
import com.mrcrayfish.furniture.gui.containers.ContainerCabinet;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCabinet extends GuiContainer
{
	private static final ResourceLocation GUI_CABINET_OAK = new ResourceLocation("cfm:textures/gui/cabinet_oak.png");
	private static final ResourceLocation GUI_CABINET_SPRUCE = new ResourceLocation("cfm:textures/gui/cabinet_spruce.png");
	private static final ResourceLocation GUI_CABINET_BIRCH = new ResourceLocation("cfm:textures/gui/cabinet_birch.png");
	private static final ResourceLocation GUI_CABINET_JUNGLE = new ResourceLocation("cfm:textures/gui/cabinet_jungle.png");
	private static final ResourceLocation GUI_CABINET_ACACIA = new ResourceLocation("cfm:textures/gui/cabinet_acacia.png");
	private static final ResourceLocation GUI_CABINET_DARK_OAK = new ResourceLocation("cfm:textures/gui/cabinet_dark_oak.png");

	private final int type;

	public GuiCabinet(IInventory playerInventory, IInventory cabinetInventory, BlockCabinet block)
	{
		super(new ContainerCabinet(playerInventory, cabinetInventory));
		this.xSize = 176;
		this.ySize = 167;
		this.type = getType(block);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString("Cabinet", (this.xSize / 2) - 44, 6, 4210752);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 94, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.bindGuiTexture();
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}

	public void bindGuiTexture()
	{
		ResourceLocation resource;
		switch (type)
		{
		case 1:
			resource = GUI_CABINET_SPRUCE;
			break;
		case 2:
			resource = GUI_CABINET_BIRCH;
			break;
		case 3:
			resource = GUI_CABINET_JUNGLE;
			break;
		case 4:
			resource = GUI_CABINET_ACACIA;
			break;
		case 5:
			resource = GUI_CABINET_DARK_OAK;
			break;
		default:
			resource = GUI_CABINET_OAK;
			break;
		}
		this.mc.getTextureManager().bindTexture(resource);
	}

	public int getType(BlockCabinet block)
	{
		if (block == FurnitureBlocks.cabinet_spruce) return 1;
		if (block == FurnitureBlocks.cabinet_birch) return 2;
		if (block == FurnitureBlocks.cabinet_jungle) return 3;
		if (block == FurnitureBlocks.cabinet_acacia) return 4;
		if (block == FurnitureBlocks.cabinet_dark_oak) return 5;
		return 0;
	}
}
