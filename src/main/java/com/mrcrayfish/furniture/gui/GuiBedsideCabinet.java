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

import com.mrcrayfish.furniture.blocks.BlockBedsideCabinet;
import com.mrcrayfish.furniture.gui.containers.ContainerBedsideCabinet;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBedsideCabinet extends GuiContainer
{
    private static final ResourceLocation GUI_CABINET_OAK = new ResourceLocation("cfm:textures/gui/bedside_cabinet_oak.png");
    private static final ResourceLocation GUI_CABINET_SPRUCE = new ResourceLocation("cfm:textures/gui/bedside_cabinet_spruce.png");
    private static final ResourceLocation GUI_CABINET_BIRCH = new ResourceLocation("cfm:textures/gui/bedside_cabinet_birch.png");
    private static final ResourceLocation GUI_CABINET_JUNGLE = new ResourceLocation("cfm:textures/gui/bedside_cabinet_jungle.png");
    private static final ResourceLocation GUI_CABINET_ACACIA = new ResourceLocation("cfm:textures/gui/bedside_cabinet_acacia.png");
    private static final ResourceLocation GUI_CABINET_DARK_OAK = new ResourceLocation("cfm:textures/gui/bedside_cabinet_dark_oak.png");

    private final int type;

    public GuiBedsideCabinet(IInventory playerInventory, IInventory bedsideCabinetInventory, BlockBedsideCabinet block)
    {
        super(new ContainerBedsideCabinet(playerInventory, bedsideCabinetInventory));
        this.xSize = 176;
        this.ySize = 168;
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
        int color = 14540253;
        if (this.type == 2) color = 4210752;
        this.fontRenderer.drawString(I18n.format("container.bedside_cabinet"), (this.xSize / 2) - 28, 6, color);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, color);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.bindGuiTexture();
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
    }

    public void bindGuiTexture()
    {
        ResourceLocation resource;
        switch(type)
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

    public int getType(BlockBedsideCabinet block)
    {
        if(block == FurnitureBlocks.BEDSIDE_CABINET_SPRUCE) return 1;
        if(block == FurnitureBlocks.BEDSIDE_CABINET_BIRCH) return 2;
        if(block == FurnitureBlocks.BEDSIDE_CABINET_JUNGLE) return 3;
        if(block == FurnitureBlocks.BEDSIDE_CABINET_ACACIA) return 4;
        if(block == FurnitureBlocks.BEDSIDE_CABINET_DARK_OAK) return 5;
        return 0;
    }
}
