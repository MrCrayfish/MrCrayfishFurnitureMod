package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.inventory.container.FreezerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Author: MrCrayfish
 */
public class FreezerScreen extends AbstractContainerScreen<FreezerMenu>
{
    private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/freezer.png");

    public FreezerScreen(FreezerMenu container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(FREEZER_GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.isFueling())
        {
            int fuelLeft = this.menu.getFuelLeftScaled();
            graphics.blit(FREEZER_GUI_TEXTURES, this.leftPos + 57, this.topPos + 37 + 12 - fuelLeft, 176, 12 - fuelLeft, 14, fuelLeft + 1);
        }
        int progress = this.menu.getSolidifyProgressionScaled();
        graphics.blit(FREEZER_GUI_TEXTURES, this.leftPos + 79, this.topPos + 34, 176, 14, progress + 1, 16);
    }
}
