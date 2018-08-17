package com.mrcrayfish.furniture.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.gui.containers.ContainerMicrowave;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageMicrowave;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMicrowave extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/microwave.png");
    private TileEntityMicrowave tileEntityMicrowave;

    private GuiButton button_start;

    public GuiMicrowave(InventoryPlayer inventoryPlayer, TileEntityMicrowave tileEntityMicrowave)
    {
        super(new ContainerMicrowave(inventoryPlayer, tileEntityMicrowave));
        this.tileEntityMicrowave = tileEntityMicrowave;
        this.xSize = 176;
        this.ySize = 174;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(false);
        buttonList.clear();

        int posX = width / 2;
        int posY = height / 2;

        button_start = new GuiButton(0, posX + 30, posY - 48, 32, 20, I18n.format("cfm.button.start"));
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
            if(!tileEntityMicrowave.isCooking())
            {
                PacketHandler.INSTANCE.sendToServer(new MessageMicrowave(0, tileEntityMicrowave.getPos().getX(), tileEntityMicrowave.getPos().getY(), tileEntityMicrowave.getPos().getZ()));
            }
            else
            {
                PacketHandler.INSTANCE.sendToServer(new MessageMicrowave(1, tileEntityMicrowave.getPos().getX(), tileEntityMicrowave.getPos().getY(), tileEntityMicrowave.getPos().getZ()));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(isPointInRegion(120, 26, 27, 5, mouseX, mouseY))
        {
            drawHoveringText(I18n.format("cfm.gui.progress", tileEntityMicrowave.progress * 27 / 10, "100%"), mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, 80, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

        int percent = tileEntityMicrowave.progress * 27 / 40;
        drawTexturedModalRect(l + 120, i1 + 26, 176, 0, percent, 5);

        if(tileEntityMicrowave.isCooking())
        {
            button_start.displayString = I18n.format("cfm.button.stop");
        }
        else
        {
            button_start.displayString = I18n.format("cfm.button.start");
        }
    }

}
