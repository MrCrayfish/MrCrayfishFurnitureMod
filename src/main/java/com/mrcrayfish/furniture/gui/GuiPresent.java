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

import com.mrcrayfish.furniture.gui.containers.ContainerPresent;
import com.mrcrayfish.furniture.gui.inventory.InventoryPresent;
import com.mrcrayfish.furniture.items.ItemPresent;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessagePresentContents;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiPresent extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/present.png");
    private GuiButton button_wrap;
    private EntityPlayer player;
    private InventoryPresent inventory;
    private ItemStack present;

    public GuiPresent(InventoryPlayer inventoryplayer, IInventory inventoryMail, EntityPlayer player, ItemStack present)
    {
        super(new ContainerPresent(inventoryplayer, inventoryMail));
        this.player = player;
        this.inventory = (InventoryPresent) inventoryMail;
        this.present = present;
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
        if(player.inventory.getCurrentItem() == null | (player.inventory.getCurrentItem() != null && !(player.inventory.getCurrentItem().getItem() instanceof ItemPresent)))
        {
            this.mc.player.closeScreen();
        }

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
        button_wrap = new GuiButton(0, posX, posY - 10, 40, 20, "Wrap");
        button_wrap.enabled = true;
        buttonList.add(button_wrap);
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
            NBTTagList var2 = (NBTTagList) NBTHelper.getCompoundTag(present, "Present").getTag("Items");
            if(var2.tagCount() > 0)
            {
                this.present.setTagInfo("Author", new NBTTagString(this.player.getName()));
                this.mc.displayGuiScreen(null);
                PacketHandler.INSTANCE.sendToServer(new MessagePresentContents(this.present));
            }
            else
            {
                this.player.sendMessage(new TextComponentString("You cannot sign an empty envelope."));
            }
        }
    }
}
