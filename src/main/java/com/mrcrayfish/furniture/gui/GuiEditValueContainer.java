package com.mrcrayfish.furniture.gui;


import com.mrcrayfish.furniture.gui.components.TextFieldComponent;
import com.mrcrayfish.furniture.gui.components.ToggleComponent;
import com.mrcrayfish.furniture.gui.components.ValueComponent;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageUpdateValueContainer;
import com.mrcrayfish.furniture.tileentity.IValueContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class GuiEditValueContainer extends GuiScreen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("cfm:textures/gui/value_container.png");

    public static final int WIDTH = 176;
    public static final int PADDING = 10;
    public static final int VALUE_HEIGHT = 35;

    private List<ValueComponent> values = new ArrayList<>();
    private IValueContainer valueContainer;
    private int containerHeight;

    public GuiEditValueContainer(IValueContainer valueContainer)
    {
        this.valueContainer = valueContainer;
        valueContainer.getEntries().forEach(entry ->
        {
            if(entry.getType() != null)
            {
                switch(entry.getType())
                {
                    case TEXT_FIELD:
                        values.add(new TextFieldComponent(entry));
                        break;
                    case TOGGLE:
                        values.add(new ToggleComponent(entry));
                        break;
                }
            }
        });
        this.containerHeight = values.size() * VALUE_HEIGHT + 10 * 2;
    }

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);

        int startX = (this.width - WIDTH) / 2;
        int startY = (this.height - this.containerHeight) / 2;
        this.buttonList.add(new GuiButton(0, startX + WIDTH + 5, startY + 5, 20, 20, "X"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 0)
        {
            this.mc.displayGuiScreen(null);
            if(this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        int startX = (this.width - WIDTH) / 2;
        int startY = (this.height - this.containerHeight) / 2;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);

        //Top
        drawScaledCustomSizeModalRect(startX, startY, 0, 0, WIDTH, 10, WIDTH, 10, 256, 256);

        //Middle
        drawScaledCustomSizeModalRect(startX, startY + 10, 0, 10, WIDTH, 1, WIDTH, values.size() * VALUE_HEIGHT, 256, 256);

        //Bottom
        drawScaledCustomSizeModalRect(startX, startY + values.size() * VALUE_HEIGHT + 10, 0, 10, WIDTH, 10, WIDTH, 10, 256, 256);

        for(int i = 0; i < values.size(); i++)
        {
            values.get(i).render(startX + PADDING, startY + i * VALUE_HEIGHT + 10, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(ValueComponent value : values)
        {
            value.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        for(ValueComponent value : values)
        {
            value.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        PacketHandler.INSTANCE.sendToServer(new MessageUpdateValueContainer(values, valueContainer));
    }
}