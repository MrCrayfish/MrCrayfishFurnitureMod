package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerFreezer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFreezer;
import com.mrcrayfish.furniture.tileentity.TileEntityFreezer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class GuiFreezer extends GuiContainer
{
    private TileEntityFreezer freezer;
    private static final ResourceLocation GUI = new ResourceLocation("cfm:textures/gui/freezer.png");

    private GuiButton button_start;

    public GuiFreezer(InventoryPlayer playerInventory, TileEntityFreezer freezerInventory)
    {
        super(new ContainerFreezer(playerInventory, freezerInventory));
        this.freezer = freezerInventory;
        this.xSize = 176;
        this.ySize = 159;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(false);
        buttonList.clear();

        int posX = width / 2;
        int posY = height / 2;

        button_start = new GuiButton(0, posX - 40, posY - 76, 32, 20, I18n.format("cfm.button.start"));
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
            if(!freezer.isFreezing())
            {
                PacketHandler.INSTANCE.sendToServer(new MessageFreezer(0, freezer.getPos().getX(), freezer.getPos().getY(), freezer.getPos().getZ()));
            }
            else
            {
                PacketHandler.INSTANCE.sendToServer(new MessageFreezer(1, freezer.getPos().getX(), freezer.getPos().getY(), freezer.getPos().getZ()));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(isPointInRegion(110, 6, 16, 16, mouseX, mouseY))
        {
            drawHoveringText(I18n.format("cfm.gui.ice_level", freezer.timeRemaining, freezer.fuelTime), mouseX, mouseY);
        }

        if(isPointInRegion(107, 31, 4, 18, mouseX, mouseY))
        {
            drawHoveringText(I18n.format("cfm.gui.progress", freezer.progress, 200), mouseX, mouseY);
        }

        if(isPointInRegion(32, 8, 11, 11, mouseX, mouseY))
        {
            if(freezer.isFreezing())
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
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, (ySize - 95) + 2, 4210752);

        if(freezer.isFreezing())
        {
            drawColour(32, 8, 11, 11, -16711936);
        }
        else
        {
            drawColour(32, 8, 11, 11, -65280);
        }

        int progress = freezer.progress * 16 / 200;
        drawColour(108, 48 - progress, 2, progress, -7736321);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI);
        int guiLeft = (width - xSize) / 2;
        int guiTop = (height - ySize) / 2;
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int var7;

        if(freezer.isFreezing())
        {
            button_start.displayString = I18n.format("cfm.button.stop");
            drawTexturedModalRect(guiLeft + 61, guiTop + 31, 176, 0, freezer.progress % 25 + 1, 15);
        }
        else
        {
            button_start.displayString = I18n.format("cfm.button.start");
        }

        if(freezer.fuelTime > 0)
        {
            var7 = (int) Math.ceil(freezer.timeRemaining * 16 / freezer.fuelTime);
            drawTexturedModalRect(guiLeft + 110, (guiTop + 22) - var7, 176, 31 - var7, 16, var7);
        }


    }

    public void drawColour(int x, int y, int width, int height, int par4)
    {
        drawRect(x, y, x + width, y + height, par4);
    }
}
