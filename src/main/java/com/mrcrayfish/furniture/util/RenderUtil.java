package com.mrcrayfish.furniture.util;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

/**
 * Author: MrCrayfish
 */
public class RenderUtil
{
    public static void scissor(int x, int y, int width, int height)
    {
        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();
        double scale = window.getGuiScale();
        int boxX = (int) (x * scale);
        int boxY = (int) ((window.getGuiScaledHeight() - y - height) * scale);
        int boxWidth = (int) (width * scale);
        int boxHeight = (int) (height * scale);
        RenderSystem.enableScissor(boxX, boxY, boxWidth, boxHeight);
    }

    public static void endScissor()
    {
        RenderSystem.disableScissor();
    }

    public static boolean isMouseInArea(int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
