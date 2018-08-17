package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiFurnitureConfig extends GuiConfig
{
    public GuiFurnitureConfig(GuiScreen parent)
    {
        super(parent, getConfigElements(), "cfm", false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> configs = new ArrayList<>();
        configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_SETTINGS)).getChildElements());
        configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_RECIPE_SETTINGS)).getChildElements());
        configs.addAll(new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_API)).getChildElements());
        return configs;
    }
}