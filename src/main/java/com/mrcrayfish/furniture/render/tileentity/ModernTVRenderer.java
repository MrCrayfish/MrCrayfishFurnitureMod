package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.client.AnimatedTexture;
import com.mrcrayfish.furniture.client.GifCache;
import com.mrcrayfish.furniture.tileentity.TileEntityModernTV;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

/**
 * Author: MrCrayfish
 */
public class ModernTVRenderer extends TileEntitySpecialRenderer<TileEntityModernTV>
{
    @Override
    public void render(TileEntityModernTV te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.pushMatrix();
        {
            if(te.isLoaded())
            {
                AnimatedTexture texture = GifCache.INSTANCE.get(te.getUrl());
                if(texture != null)
                {
                    GlStateManager.enableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
                    GlStateManager.disableLighting();
                    GlStateManager.bindTexture(texture.getTextureId());

                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    double scaleWidth = (double) te.getWidth() / (double) texture.getWidth();
                    double scaleHeight = (double) te.getHeight() / (double) texture.getHeight();
                    double scale = Math.min(scaleWidth, scaleHeight);
                    double width = texture.getWidth() * scale;
                    double height = texture.getHeight() * scale;
                    double startX = (te.getWidth() - width) / 2.0;
                    double startY = (te.getHeight() - height) / 2.0;

                    startX *= 0.0625;
                    startY *= 0.0625;
                    width *= 0.0625;
                    height *= 0.0625;

                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.getBuffer();
                    buffer.setTranslation(x - 3 * 0.0625, y + 4 * 0.0625, z + 7.61 * 0.0625);
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                    buffer.pos(0, 0, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(0, te.getHeight() * 0.0625, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(te.getWidth() * 0.0625, te.getHeight() * 0.0625, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(te.getWidth() * 0.0625, 0, 0).color(0, 0, 0, 255).endVertex();
                    tessellator.draw();

                    buffer.setTranslation(x - 3 * 0.0625, y + 4 * 0.0625, z + 7.6 * 0.0625);
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(startX, startY, 0).tex(0, 0).endVertex();
                    buffer.pos(startX, startY + height, 0).tex(0, 1).endVertex();
                    buffer.pos(startX + width, startY + height, 0).tex(1, 1).endVertex();
                    buffer.pos(startX + width, startY, 0).tex(1, 0).endVertex();
                    tessellator.draw();
                }
            }
        }
        GlStateManager.popMatrix();
    }
}
