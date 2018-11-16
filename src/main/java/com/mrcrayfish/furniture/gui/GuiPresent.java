package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerPresent;
import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageSignItem;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiPresent extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("cfm:textures/gui/present.png");
    private GuiButton btnWrap;
    private EntityPlayer player;
    private ItemStack heldItem;

    public GuiPresent(InventoryPlayer playerInventory, ItemInventory itemInventory, EntityPlayer player)
    {
        super(new ContainerPresent(playerInventory, itemInventory));
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
        }
        if(heldItem != player.getHeldItemMainhand())
        {
            this.mc.player.closeScreen();
        }
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
        Keyboard.enableRepeatEvents(false);
        buttonList.clear();
        int posX = width / 2 + 40;
        int posY = height / 2 - 50;
        btnWrap = new GuiButton(0, posX, posY - 10, 40, 20, I18n.format("cfm.button.wrap"));
        btnWrap.visible = true;
        buttonList.add(btnWrap);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
            return;

        if(guibutton == btnWrap)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageSignItem());
        }
    }
}
