package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerEnvelope;
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

public class GuiEnvelope extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("cfm:textures/gui/envelope.png");
    private GuiButton btnSign;
    private EntityPlayer player;
    private ItemStack heldItem;

    public GuiEnvelope(InventoryPlayer playerInventory, ItemInventory itemInventory, EntityPlayer player)
    {
        super(new ContainerEnvelope(playerInventory, itemInventory));
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
            if(heldItem.getItem() == FurnitureItems.ENVELOPE)
            {
                btnSign.visible = true;
            }
        }
        if(heldItem != player.getHeldItemMainhand())
        {
            this.mc.player.closeScreen();
        }
        this.fontRenderer.drawString(I18n.format("item.item_envelope.name"), xSize / 2 - 22, 5, 9999999);
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
    protected void actionPerformed(GuiButton button)
    {
        if(!button.enabled)
            return;

        if(button == btnSign)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageSignItem());
        }
    }

}
