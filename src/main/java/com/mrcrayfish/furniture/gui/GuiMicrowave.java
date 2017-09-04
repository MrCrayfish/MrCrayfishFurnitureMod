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

import com.mrcrayfish.furniture.gui.containers.ContainerMicrowave;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageMicrowave;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiMicrowave extends GuiContainer
{
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/microwave.png");
	private TileEntityMicrowave tileEntityMicrowave;

	private GuiButton button_start;

	public GuiMicrowave(InventoryPlayer inventoryPlayer, TileEntityMicrowave tileEntityMicrowave)
	{
		super(new ContainerMicrowave(inventoryPlayer, tileEntityMicrowave));
		this.tileEntityMicrowave = tileEntityMicrowave;
		this.xSize = 176;
		this.ySize = 174;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);
		buttonList.clear();

		int posX = width / 2;
		int posY = height / 2;

		button_start = new GuiButton(0, posX + 30, posY - 48, 32, 20, "Start");
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
			if (!tileEntityMicrowave.isCooking())
			{
				PacketHandler.INSTANCE.sendToServer(new MessageMicrowave(0, tileEntityMicrowave.getPos().getX(), tileEntityMicrowave.getPos().getY(), tileEntityMicrowave.getPos().getZ()));
			}
			else
			{
				PacketHandler.INSTANCE.sendToServer(new MessageMicrowave(1, tileEntityMicrowave.getPos().getX(), tileEntityMicrowave.getPos().getY(), tileEntityMicrowave.getPos().getZ()));
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, 80, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		int percent = tileEntityMicrowave.progress * 27 / 40;
		drawTexturedModalRect(l + 120, i1 + 26, 176, 0, percent, 5);

		if (tileEntityMicrowave.isCooking())
		{
			button_start.displayString = "Stop";
		}
		else
		{
			button_start.displayString = "Start";
		}
	}

}
