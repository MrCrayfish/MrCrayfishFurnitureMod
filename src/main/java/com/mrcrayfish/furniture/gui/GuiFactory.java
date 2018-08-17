package com.mrcrayfish.furniture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class GuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance)
    {
        Minecraft.getMinecraft().getSession().getUsername();
    }

    @Override
    public boolean hasConfigGui()
    {
        return false;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return null;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }
}