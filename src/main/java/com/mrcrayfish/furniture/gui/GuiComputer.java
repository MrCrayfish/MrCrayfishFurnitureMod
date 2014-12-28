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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageMineBayBuy;
import com.mrcrayfish.furniture.network.message.MessageMineBayClosed;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;

public class GuiComputer extends GuiContainer
{
	private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/computer.png");

	private GuiButton left, right;
	private GuiButton button_buy;

	private int itemNum;
	private ItemStack buySlot;
	private TileEntityComputer tileEntityComputer;
	private RecipeData[] itemdata;

	public GuiComputer(InventoryPlayer inventoryplayer, TileEntityComputer tileEntityComputer)
	{
		super(new ContainerComputer(inventoryplayer, tileEntityComputer));
		this.tileEntityComputer = tileEntityComputer;
		this.xSize = 176;
		this.ySize = 187;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);

		buttonList.clear();

		int posX = width / 2;
		int posY = height / 2;

		left = new GuiButton(0, posX - 48, posY - 80, 15, 20, "<");
		right = new GuiButton(1, posX + 34, posY - 80, 15, 20, ">");
		button_buy = new GuiButton(2, posX - 48, posY - 57, 29, 20, "Buy");

		buttonList.add(left);
		buttonList.add(right);
		buttonList.add(button_buy);

		this.itemNum = tileEntityComputer.getBrowsingInfo();
		itemdata = Recipes.getMineBayItems();
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
			itemNum--;
			if (itemNum < 0)
			{
				itemNum = 0;
			}
			this.tileEntityComputer.setBrowsingInfo(itemNum);
		}
		if (guibutton.id == 1)
		{
			itemNum++;
			if (itemNum > itemdata.length - 1)
			{
				itemNum = itemdata.length - 1;
			}
			this.tileEntityComputer.setBrowsingInfo(itemNum);
		}
		if (guibutton.id == 2)
		{
			this.buySlot = this.tileEntityComputer.getStackInSlot(0);
			if (buySlot != null)
			{
				ItemStack money = itemdata[itemNum].getCurrency();
				if (buySlot.getItem() == money.getItem())
				{
					if (buySlot.getItemDamage() == money.getItemDamage())
					{
						int price = itemdata[itemNum].getPrice();
						if (buySlot.stackSize == price)
						{
							PacketHandler.INSTANCE.sendToServer(new MessageMineBayBuy(this.itemNum, this.tileEntityComputer.getPos().getX(), this.tileEntityComputer.getPos().getY(), this.tileEntityComputer.getPos().getZ(), true));
						}
						else if (buySlot.stackSize > price && buySlot.stackSize > 1)
						{
							PacketHandler.INSTANCE.sendToServer(new MessageMineBayBuy(this.itemNum, this.tileEntityComputer.getPos().getX(), this.tileEntityComputer.getPos().getY(), this.tileEntityComputer.getPos().getZ(), false));
						}
						if (buySlot.stackSize == 0 && price == 1)
						{
							PacketHandler.INSTANCE.sendToServer(new MessageMineBayBuy(this.itemNum, this.tileEntityComputer.getPos().getX(), this.tileEntityComputer.getPos().getY(), this.tileEntityComputer.getPos().getZ(), true));
						}
					}
				}
			}
		}
	}

	@Override
	public void onGuiClosed()
	{
		PacketHandler.INSTANCE.sendToServer(new MessageMineBayClosed(tileEntityComputer.getPos().getX(), tileEntityComputer.getPos().getY(), tileEntityComputer.getPos().getZ()));
		super.onGuiClosed();
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString("Inventory", 8, (ySize - 103), 4210752);

		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		itemRender.zLevel = 100.0F;

		if ((itemNum - 1) >= 0)
		{
			ItemStack pre = itemdata[itemNum - 1].getInput();
			itemRender.renderItemAndEffectIntoGUI(pre, 57, 16);
			itemRender.renderItemOverlays(this.fontRendererObj, pre, 57, 16);
		}

		ItemStack stock = itemdata[itemNum].getInput();
		itemRender.renderItemAndEffectIntoGUI(stock, 80, 16);
		itemRender.renderItemOverlays(this.fontRendererObj, stock, 80, 16);

		if ((itemNum + 1) < itemdata.length)
		{
			ItemStack post = itemdata[itemNum + 1].getInput();
			itemRender.renderItemAndEffectIntoGUI(post, 103, 16);
			itemRender.renderItemOverlays(this.fontRendererObj, post, 103, 16);
		}

		ItemStack currency = itemdata[itemNum].getCurrency();
		itemRender.renderItemAndEffectIntoGUI(currency, 73, 40);
		itemRender.renderItemOverlays(this.fontRendererObj, currency, 73, 40);
		itemRender.zLevel = 0.0F;
		GL11.glDisable(GL11.GL_LIGHTING);

		int price = itemdata[itemNum].getPrice();
		this.fontRendererObj.drawString("x" + Integer.toString(price), 90, 44, 0);

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		ItemStack stock = itemdata[itemNum].getInput();
		if (this.isPointInRegion(80, 16, 16, 16, par1, par2))
		{
			this.renderToolTip(stock, par1, par2);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
	}
}
