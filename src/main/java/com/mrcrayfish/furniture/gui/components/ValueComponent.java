package com.mrcrayfish.furniture.gui.components;

import com.mrcrayfish.furniture.tileentity.IValueContainer;
import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * Author: MrCrayfish
 */
public abstract class ValueComponent
{
    protected String id;
    protected String name;

    public ValueComponent(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void render(int x, int y, int mouseX, int mouseY)
    {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(name, x, y, Color.WHITE.getRGB());
    }

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void keyTyped(char typedChar, int keyCode);

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public abstract String getValue();

    public abstract IValueContainer.Entry toEntry();
}