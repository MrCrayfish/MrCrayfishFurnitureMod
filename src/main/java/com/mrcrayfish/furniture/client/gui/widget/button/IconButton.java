package com.mrcrayfish.furniture.client.gui.widget.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class IconButton extends Button
{
    private ResourceLocation iconResource;
    private int iconU;
    private int iconV;

    public IconButton(int x, int y, OnPress onPress, ResourceLocation iconResource, int iconU, int iconV)
    {
        super(x, y, 20, 20, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    public void setIcon(ResourceLocation iconResource, int iconU, int iconV)
    {
        this.iconResource = iconResource;
        this.iconU = iconU;
        this.iconV = iconV;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        super.renderWidget(graphics, mouseX, mouseY, partialTicks);
        if(!this.active)
        {
            RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 1.0F);
        }
        graphics.blit(this.iconResource, this.getX() + 2, this.getY() + 2, this.iconU, this.iconV, 16, 16);
    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner()
    {
        return DefaultTooltipPositioner.INSTANCE;
    }
}
