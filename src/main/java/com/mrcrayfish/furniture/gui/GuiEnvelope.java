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

import com.mrcrayfish.furniture.gui.containers.ContainerEnvelope;
import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageEnvelope;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiEnvelope extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/envelope.png");
    private GuiButton buttonOk;
    private EntityPlayer player;
    private InventoryEnvelope inventory;
    private ItemStack mail;

    public GuiEnvelope(InventoryPlayer inventoryplayer, IInventory inventoryMail, EntityPlayer player, ItemStack mail)
    {
        super(new ContainerEnvelope(inventoryplayer, inventoryMail));
        this.player = player;
        this.inventory = (InventoryEnvelope) inventoryMail;
        this.mail = mail;
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
        this.fontRenderer.drawString("Envelope", xSize / 2 - 22, 5, 9999999);
        this.fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(false);

        buttonList.clear();

        int posX = width / 2 + 40;
        int posY = height / 2 - 50;

        if(mail.getItem() == FurnitureItems.itemEnvelope)
        {
            buttonOk = new GuiButton(0, posX, posY, 40, 20, "Sign");
            buttonOk.enabled = true;
            buttonList.add(buttonOk);
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        this.inventory.saveInventory();
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
            NBTTagList list = (NBTTagList) NBTHelper.getCompoundTag(mail, "Envelope").getTag("Items");
            if(list.tagCount() > 0)
            {
                PacketHandler.INSTANCE.sendToServer(new MessageEnvelope(this.mail));
                this.mc.displayGuiScreen(null);
            }
            else
            {
                this.player.sendMessage(new TextComponentString("You cannot sign an empty envelope."));
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
    }
}
