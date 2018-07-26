/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.I18n;
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

        button_start = new GuiButton(0, posX - 35, posY - 109, 32, 20, I18n.format("cfm.button.start"));
        buttonList.add(button_start);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }

        if(guibutton.id == 0)
        {
            if(!tileEntityDishwasher.isWashing())
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
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(isPointInRegion(37, 9, 11, 11, mouseX, mouseY))
        {
            if(tileEntityDishwasher.isWashing())
            {
                drawHoveringText(I18n.format("cfm.gui.run"), mouseX, mouseY);
            }
            else
            {
                drawHoveringText(I18n.format("cfm.gui.stop"), mouseX, mouseY);
            }
        }

        if(isPointInRegion(129, 39, 7, 55, mouseX, mouseY))
        {
            drawHoveringText(I18n.format("cfm.gui.soap_level", tileEntityDishwasher.timeRemaining), mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, 135, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

        if(tileEntityDishwasher.isWashing())
        {
            int superMode = tileEntityDishwasher.superMode ? 20 : 50;
            int percent = (tileEntityDishwasher.progress % superMode) * 55 / superMode;
            drawTexturedModalRect((l + 39), (i1 + 94) - percent, 176, 55 - percent, 9, percent);
        }

        int percent = tileEntityDishwasher.timeRemaining * 55 / 5000;
        int superMode = tileEntityDishwasher.superMode ? 14 : 0;
        drawTexturedModalRect((l + 129), (i1 + 94) - percent, 185 + superMode, 0, 7, percent);

        drawTexturedModalRect((l + 129), (i1 + 39), 192, 0, 7, 55);

        if(tileEntityDishwasher.isWashing())
        {
            button_start.displayString = I18n.format("cfm.button.stop");
            drawColour(l + 37, i1 + 9, 11, 11, -16711936);
        }
        else
        {
            button_start.displayString = I18n.format("cfm.button.start");
            drawColour(l + 37, i1 + 9, 11, 11, -65280);
        }
    }

    public void drawColour(int x, int y, int width, int height, int par4)
    {
        drawRect(x, y, x + width, y + height, par4);
        GlStateManager.color(1, 1, 1, 1);
    }

}
