package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerPackage;
import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageSignItem;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiPackage extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("cfm:textures/gui/package.png");
    private GuiButton btnSign;
    private EntityPlayer player;
    private ItemStack heldItem;

    public GuiPackage(InventoryPlayer playerInventory, ItemInventory itemInventory, EntityPlayer player)
    {
        super(new ContainerPackage(playerInventory, itemInventory));
        this.player = player;
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
        if(heldItem == null)
        {
            heldItem = player.getHeldItemMainhand();
            if(heldItem.getItem() == FurnitureItems.PACKAGE)
            {
                btnSign.visible = true;
            }
        }
        if(heldItem != player.getHeldItemMainhand())
        {
            this.mc.player.closeScreen();
        }
        this.fontRenderer.drawString(I18n.format("item.item_package.name"), xSize / 2 - 19, 5, 9999999);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        int posX = width / 2 + 40;
        int posY = height / 2 - 50;
        btnSign = new GuiButton(0, posX, posY, 40, 20, I18n.format("cfm.button.sign"));
        btnSign.visible = false;
        buttonList.add(btnSign);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton == btnSign)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageSignItem());
        }
    }
}