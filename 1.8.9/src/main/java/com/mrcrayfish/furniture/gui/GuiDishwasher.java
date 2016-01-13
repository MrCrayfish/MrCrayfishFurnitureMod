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

import com.mrcrayfish.furniture.gui.containers.ContainerDishwasher;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageDishwasher;
import com.mrcrayfish.furniture.tileentity.TileEntityDishwasher;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiDishwasher extends GuiContainer
{
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/dishwasher.png");
	private TileEntityDishwasher tileEntityDishwasher;
	private VertexFormat format = new VertexFormat();

	private GuiButton button_start;

	public GuiDishwasher(InventoryPlayer inventoryPlayer, TileEntityDishwasher dishwasher)
	{
		super(new ContainerDishwasher(inventoryPlayer, dishwasher));
		this.tileEntityDishwasher = dishwasher;
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
			if (!tileEntityDishwasher.isWashing())
			{
				PacketHandler.INSTANCE.sendToServer(new MessageDishwasher(0, tileEntityDishwasher.getPos().getX(), tileEntityDishwasher.getPos().getY(), tileEntityDishwasher.getPos().getZ()));
			}
			else
			{
				PacketHandler.INSTANCE.sendToServer(new MessageDishwasher(1, tileEntityDishwasher.getPos().getX(), tileEntityDishwasher.getPos().getY(), tileEntityDishwasher.getPos().getZ()));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		if(isPointInRegion(37, 9, 11, 11, mouseX, mouseY))
		{
			if (tileEntityDishwasher.isWashing())
			{
				drawHoveringText(Arrays.asList(new String[]{"Running"}), mouseX, mouseY);
			}
			else
			{
				drawHoveringText(Arrays.asList(new String[]{"Stopped"}), mouseX, mouseY);
			}
		}
		
		if(isPointInRegion(129, 39, 7, 55, mouseX, mouseY))
		{
			drawHoveringText(Arrays.asList(new String[]{"Soap: " + tileEntityDishwasher.timeRemaining + "/5000"}), mouseX, mouseY);
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

		if (tileEntityDishwasher.isWashing())
		{
			int superMode = tileEntityDishwasher.superMode ? 20 : 50;
			int percent = (tileEntityDishwasher.progress % superMode) * 55 / superMode;
			drawTexturedModalRect((l + 39), (i1 + 94) - percent, 176, 55 - percent, 9, percent);
		}

		int percent = tileEntityDishwasher.timeRemaining * 55 / 5000;
		int superMode = tileEntityDishwasher.superMode ? 14 : 0;
		drawTexturedModalRect((l + 129), (i1 + 94) - percent, 185 + superMode, 0, 7, percent);

		drawTexturedModalRect((l + 129), (i1 + 39), 192, 0, 7, 55);

		if (tileEntityDishwasher.isWashing())
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
		renderer.begin(GL11.GL_QUADS, format);
		renderer.putColor4(par4);
		renderer.putNormal(x, y, 0.0F);
		renderer.putNormal(x, y + height, 0.0F);
		renderer.putNormal(x + width, y + height, 0.0F);
		renderer.putNormal(x + width, y, 0.0F);
		Tessellator.getInstance().draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
