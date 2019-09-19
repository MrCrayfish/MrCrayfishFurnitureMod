package com.mrcrayfish.furniture.client.gui.widget.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.client.event.CreativeScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class TagButton extends Button
{
    private static final ResourceLocation TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private CreativeScreenEvents.TagFilter category;
    private ItemStack stack;
    private boolean toggled;

    public TagButton(int x, int y, CreativeScreenEvents.TagFilter category, IPressable pressable)
    {
        super(x, y, 32, 28, "", pressable);
        this.category = category;
        this.stack = category.getIcon();
        this.toggled = category.isEnabled();
    }

    public CreativeScreenEvents.TagFilter getCategory()
    {
        return category;
    }

    @Override
    public void onPress()
    {
        this.toggled = !this.toggled;
        this.category.setEnabled(this.toggled);
        super.onPress();
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(TABS);

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        int width = this.toggled ? 32 : 28;
        int textureX = 28;
        int textureY = this.toggled ? 32 : 0;
        this.drawRotatedTexture(this.x, this.y, textureX, textureY, width, 28);

        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        ItemRenderer renderer = mc.getItemRenderer();
        renderer.zLevel = 100.0F;
        renderer.renderItemAndEffectIntoGUI(this.stack, x + 8, y + 6);
        renderer.renderItemOverlays(mc.fontRenderer, this.stack, x + 8, y + 6);
        renderer.zLevel = 100.0F;
    }

    private void drawRotatedTexture(int x, int y, int textureX, int textureY, int width, int height)
    {
        float scaleX = 0.00390625F;
        float scaleY = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x), (double)(y + height), 0.0).tex((double)((float)(textureX + height) * scaleX), (double)((float)(textureY) * scaleY)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((float)(textureX + height) * scaleX), (double)((float)(textureY + width) * scaleY)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y), 0.0).tex((double)((float)(textureX) * scaleX), (double)((float)(textureY + width) * scaleY)).endVertex();
        bufferbuilder.pos((double)(x), (double)(y), 0.0).tex((double)((float)(textureX) * scaleX), (double)((float)(textureY) * scaleY)).endVertex();
        tessellator.draw();
    }

    public void updateState()
    {
        this.toggled = category.isEnabled();
    }
}
