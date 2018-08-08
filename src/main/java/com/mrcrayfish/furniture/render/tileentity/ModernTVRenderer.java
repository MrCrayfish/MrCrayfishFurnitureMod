package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockFurnitureTile;
import com.mrcrayfish.furniture.client.AnimatedTexture;
import com.mrcrayfish.furniture.client.GifCache;
import com.mrcrayfish.furniture.tileentity.TileEntityModernTV;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
            BlockPos pos = te.getPos();
            IBlockState state = te.getWorld().getBlockState(pos);
            if(!state.getPropertyKeys().contains(BlockFurnitureTile.FACING))
                return;

            if(te.isLoaded())
            {
                AnimatedTexture texture = GifCache.INSTANCE.get(te.getUrl());
                if(texture != null)
                {
                    GlStateManager.translate(x, y, z);

                    GlStateManager.enableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
                    GlStateManager.disableLighting();
                    GlStateManager.bindTexture(texture.getTextureId());

                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    //Calculates the positioning and scale so the GIF keeps its ratio and renders within the screen
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

                    //Setups translations
                    GlStateManager.translate(8 * 0.0625, 4 * 0.0625, 8 * 0.0625);
                    EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                    GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.translate(-te.getWidth() / 2 * 0.0625, 0, 0);
                    GlStateManager.translate(0, 0, -0.35 * 0.0625);

                    //Render a black quad
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.getBuffer();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                    buffer.pos(0, 0, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(0, te.getHeight() * 0.0625, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(te.getWidth() * 0.0625, te.getHeight() * 0.0625, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(te.getWidth() * 0.0625, 0, 0).color(0, 0, 0, 255).endVertex();
                    tessellator.draw();

                    //Render the GIF
                    GlStateManager.translate(0, 0, -0.01 * 0.0625);
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
