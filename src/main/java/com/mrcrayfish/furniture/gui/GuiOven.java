package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerOven;
import com.mrcrayfish.furniture.tileentity.TileEntityOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiOven extends GuiContainer
{
    private TileEntityOven oven;
    private static final ResourceLocation GUI = new ResourceLocation("cfm:textures/gui/oven.png");

    public GuiOven(InventoryPlayer inventoryplayer, TileEntityOven tileEntityFreezer)
    {
        super(new ContainerOven(inventoryplayer, tileEntityFreezer));
        this.oven = tileEntityFreezer;
        this.xSize = 176;
        this.ySize = 228;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(isPointInRegion(37, 9, 11, 11, mouseX, mouseY))
        {
            if(oven.isCooking())
            {
                drawHoveringText(I18n.format("cfm.gui.run"), mouseX, mouseY);
            }
            else
            {
                drawHoveringText(I18n.format("cfm.gui.stop"), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.format("tile.oven.name"), 70, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI);
        if(oven.isCooking())
        {
            int progress = oven.getCookProgressScaled(14);
            drawTexturedModalRect(54 + oven.getCookingItem() * 18, 55 + (14 - progress), 176, (14 - progress), 14, progress);
        }

        if(oven.isCooking())
        {
            drawColour(37, 9, 11, 11, -16711936);
        }
        else
        {
            drawColour(37, 9, 11, 11, -65280);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        int var7;

        var7 = this.oven.getCookProgressScaled(24);
        drawTexturedModalRect(l + 75, i1 + 20, 176, 14, var7 + 1, 16);

    }

    public void drawColour(int x, int y, int width, int height, int par4)
    {
        drawRect(x, y, x + width, y + height, par4);
    }
}