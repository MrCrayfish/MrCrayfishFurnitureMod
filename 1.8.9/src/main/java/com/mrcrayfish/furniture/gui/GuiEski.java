package com.mrcrayfish.furniture.gui;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerEski;
import com.mrcrayfish.furniture.tileentity.TileEntityEski;
import com.mrcrayfish.furniture.tileentity.TileEntityFreezer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiEski extends GuiContainer
{
	private static final ResourceLocation GUI = new ResourceLocation("cfm:textures/gui/eski.png");
	
	public GuiEski(InventoryPlayer playerInventory, TileEntityEski eskiInventory)
	{
		super(new ContainerEski(playerInventory, eskiInventory));
		this.xSize = 176;
		this.ySize = 167;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI);
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
	}
}
