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

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerFreezer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFreezer;
import com.mrcrayfish.furniture.tileentity.TileEntityFreezer;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFreezer extends GuiContainer
{
	private TileEntityFreezer freezer;
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/freezer.png");

	private GuiButton button_start;
	
	public GuiFreezer(InventoryPlayer playerInventory, TileEntityFreezer freezerInventory)
	{
		super(new ContainerFreezer(playerInventory, freezerInventory));
		this.freezer = freezerInventory;
		this.xSize = 176;
		this.ySize = 159;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);
		buttonList.clear();

		int posX = width / 2;
		int posY = height / 2;

		button_start = new GuiButton(0, posX - 40, posY - 76, 32, 20, "Start");
		buttonList.add(button_start);
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
			if (!freezer.isFreezing())
			{
				PacketHandler.INSTANCE.sendToServer(new MessageFreezer(0, freezer.getPos().getX(), freezer.getPos().getY(), freezer.getPos().getZ()));
			}
			else
			{
				PacketHandler.INSTANCE.sendToServer(new MessageFreezer(1, freezer.getPos().getX(), freezer.getPos().getY(), freezer.getPos().getZ()));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if(isPointInRegion(110, 6, 16, 16, mouseX, mouseY))
		{
			drawHoveringText(Arrays.asList(new Object[]{"Ice Level: " + freezer.timeRemaining + "/" + freezer.fuelTime}), mouseX, mouseY);
		}
		
		if(isPointInRegion(107, 31, 4, 18, mouseX, mouseY))
		{
			drawHoveringText(Arrays.asList(new Object[]{"Freezing: " + freezer.progress + "/200"}), mouseX, mouseY);
		}
		
		if(isPointInRegion(32, 8, 11, 11, mouseX, mouseY))
		{
			if (freezer.isFreezing())
			{
				drawHoveringText(Arrays.asList(new Object[]{"Running"}), mouseX, mouseY);
			}
			else
			{
				drawHoveringText(Arrays.asList(new Object[]{"Stopped"}), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, (ySize - 95) + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		int var7;

		if (freezer.isFreezing())
		{
			button_start.displayString = "Stop";
			drawColour(l + 32, i1 + 8, 11, 11, 49475);
			drawTexturedModalRect(l + 61, i1 + 31, 176, 0, freezer.progress % 25 + 1, 15);
		}
		else
		{
			button_start.displayString = "Start";
			drawColour(l + 32, i1 + 8, 11, 11, 16711680);
		}
		
		if(freezer.fuelTime > 0)
		{
			var7 = (int) Math.ceil(freezer.timeRemaining * 16 / freezer.fuelTime);
			drawTexturedModalRect(l + 110, (i1 + 22) - var7, 176, 31 - var7, 16, var7);
		}
		
		var7 = freezer.progress * 16 / 200;
		drawColour(l + 108, (i1 + 48) - var7, 2, var7, 49475);
	}
	
	public void drawColour(int x, int y, int width, int height, int par4)
	{
		WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		renderer.startDrawingQuads();
		renderer.setColorOpaque_I(par4);
		renderer.addVertex(x, y, 0.0D);
		renderer.addVertex(x, y + height, 0.0D);
		renderer.addVertex(x + width, y + height, 0.0D);
		renderer.addVertex(x + width, y, 0.0D);
		Tessellator.getInstance().draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
