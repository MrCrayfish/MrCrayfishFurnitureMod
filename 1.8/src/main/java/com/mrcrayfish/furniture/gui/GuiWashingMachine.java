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

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerWashingMachine;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageWashingMachine;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWashingMachine extends GuiContainer
{
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/washingmachine.png");
	private TileEntityWashingMachine tileEntityWashingMachine;

	private GuiButton button_start;

	public GuiWashingMachine(InventoryPlayer inventoryPlayer, TileEntityWashingMachine tileEntityWashingMachine)
	{
		super(new ContainerWashingMachine(inventoryPlayer, tileEntityWashingMachine));
		this.tileEntityWashingMachine = tileEntityWashingMachine;
		this.xSize = 176;
		this.ySize = 228;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);
		buttonList.clear();

		int posX = width / 2;
		int posY = height / 2;

		button_start = new GuiButton(0, posX - 35, posY - 109, 32, 20, "Start");
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
			if (!tileEntityWashingMachine.isWashing())
			{
				PacketHandler.INSTANCE.sendToServer(new MessageWashingMachine(0, tileEntityWashingMachine.getPos().getX(), tileEntityWashingMachine.getPos().getY(), tileEntityWashingMachine.getPos().getZ()));
			}
			else
			{
				PacketHandler.INSTANCE.sendToServer(new MessageWashingMachine(1, tileEntityWashingMachine.getPos().getX(), tileEntityWashingMachine.getPos().getY(), tileEntityWashingMachine.getPos().getZ()));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		if(isPointInRegion(37, 9, 11, 11, mouseX, mouseY))
		{
			if (tileEntityWashingMachine.isWashing())
			{
				drawHoveringText(Arrays.asList(new Object[]{"Running"}), mouseX, mouseY);
			}
			else
			{
				drawHoveringText(Arrays.asList(new Object[]{"Stopped"}), mouseX, mouseY);
			}
		}
		
		if(isPointInRegion(129, 30, 10, 73, mouseX, mouseY))
		{
			drawHoveringText(Arrays.asList(new Object[]{"Soap: " + tileEntityWashingMachine.timeRemaining + "/5000"}), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, 135, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		if (tileEntityWashingMachine.isWashing())
		{
			int superMode = tileEntityWashingMachine.superMode ? 20 : 50;
			int percent = (tileEntityWashingMachine.progress % superMode) * 73 / superMode;
			drawTexturedModalRect((l + 34), (i1 + 104) - percent, 176, 73 - percent, 16, percent);
		}

		int percent = tileEntityWashingMachine.timeRemaining * 73 / 5000;
		int superMode = tileEntityWashingMachine.superMode ? 20 : 0;
		drawTexturedModalRect((l + 129), (i1 + 103) - percent, 192 + superMode, 0, 10, percent);

		drawTexturedModalRect((l + 129), (i1 + 30), 202, 0, 10, 73);

		if (tileEntityWashingMachine.isWashing())
		{
			button_start.displayString = "Stop";
			drawColour(l + 37, i1 + 9, 11, 11, 49475);
		}
		else
		{
			button_start.displayString = "Start";
			drawColour(l + 37, i1 + 9, 11, 11, 16711680);
		}
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
