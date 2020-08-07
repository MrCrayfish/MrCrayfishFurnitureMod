package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockFurnitureTile;
import com.mrcrayfish.furniture.client.ImageCache;
import com.mrcrayfish.furniture.client.ImageDownloadThread;
import com.mrcrayfish.furniture.client.Texture;
import com.mrcrayfish.furniture.tileentity.TileEntityPhotoFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class PhotoFrameRenderer extends TileEntitySpecialRenderer<TileEntityPhotoFrame>
{
    private static final ResourceLocation NOISE = new ResourceLocation("cfm:textures/noise.png");
    private static final Random RAND = new Random();

    @Override
    public void render(TileEntityPhotoFrame te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(te.getPhoto() == null)
            return;

        BlockPos pos = te.getPos();
        IBlockState state = te.getWorld().getBlockState(pos);
        if(!state.getPropertyKeys().contains(BlockFurnitureTile.FACING))
            return;

        GlStateManager.pushMatrix();
        {
            double frameWidth = 14;
            double frameHeight = 14;
            double frameYOffset = 1;
            double frameZOffset = 7.49;

            ImageDownloadThread.ImageDownloadResult result = te.getResult();
            if(result != null && result != ImageDownloadThread.ImageDownloadResult.SUCCESS)
            {
                GlStateManager.translate(x, y, z);
                GlStateManager.translate(8 * 0.0625, frameYOffset * 0.0625, 8 * 0.0625);
                EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                GlStateManager.translate(-frameWidth / 2 * 0.0625, 0, 0);
                GlStateManager.translate(0, 0, frameZOffset * 0.0625);
                GlStateManager.translate(frameWidth * 0.0625 - 0.0625, frameHeight * 0.0625 - 0.0625, 0);
                GlStateManager.scale(1, -1, -1);
                GlStateManager.scale(0.01F, 0.01F, 0.01F);
                GlStateManager.rotate(180F, 0, 1, 0);

                String message = I18n.format(result.getKey());
                FontRenderer renderer = this.getFontRenderer();
                List<String> lines = renderer.listFormattedStringToWidth(message, (int) ((frameWidth - 2.0) * 6.3));
                for(int i = 0; i < lines.size(); i++)
                {
                    renderer.drawString(lines.get(i), 0, renderer.FONT_HEIGHT * i, 16777215);
                }
            }
            else
            {
                GlStateManager.translate(x, y, z);
                GlStateManager.enableBlend();
                GlStateManager.color(0.65F, 0.65F, 0.65F, 1.0F);
                OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
                GlStateManager.disableLighting();

                double startX = 0.0;
                double startY = 0.0;
                double width = 0.0;
                double height = 0.0;

                boolean loading = te.isLoading();
                if (!loading)
                {
                    if (te.isLoaded())
                    {
                        Texture texture = ImageCache.INSTANCE.get(te.getPhoto());
                        if (texture != null)
                        {
                            width = texture.getWidth();
                            height = texture.getHeight();
                            loading = !texture.bind();
                        }
                        else
                        {
                            String photo = te.getPhoto();
                            if(photo != null)
                            {
                                te.loadUrl(photo);
                            }
                        }
                    }
                }

                if(loading)
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(NOISE);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    //Setups translations
                    GlStateManager.translate(8 * 0.0625, frameYOffset * 0.0625, 8 * 0.0625);
                    EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                    GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.translate(-frameWidth / 2 * 0.0625, 0, 0);
                    GlStateManager.translate(0, 0, frameZOffset * 0.0625);

                    double pixelScale = 1.0 / 256;
                    double scaledWidth = frameWidth * 4;
                    double scaledHeight = frameHeight * 4;
                    double u = ((int)((256 - scaledWidth) * RAND.nextDouble()) * pixelScale);
                    double v = ((int)((256 - scaledHeight) * RAND.nextDouble()) * pixelScale);

                    startX *= 0.0625;
                    startY *= 0.0625;
                    frameWidth *= 0.0625;
                    frameHeight *= 0.0625;

                    //Render the Image
                    GlStateManager.translate(0, 0, -0.01 * 0.0625);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.getBuffer();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(startX, startY, 0).tex(u, v).endVertex();
                    buffer.pos(startX, startY + frameHeight, 0).tex(u, v + scaledHeight * pixelScale).endVertex();
                    buffer.pos(startX + frameWidth, startY + frameHeight, 0).tex(u + scaledWidth * pixelScale, v + scaledHeight * pixelScale).endVertex();
                    buffer.pos(startX + frameWidth, startY, 0).tex(u + scaledWidth * pixelScale, v).endVertex();
                    tessellator.draw();
                }
                else
                {
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    double imageWidth = frameWidth;
                    double imageHeight = frameHeight;

                    if(!te.isStretched())
                    {
                        //Calculates the positioning and scale so the GIF keeps its ratio and renders within the screen
                        double scaleWidth = frameWidth / width;
                        double scaleHeight = frameWidth / height;
                        double scale = Math.min(scaleWidth, scaleHeight);
                        imageWidth = width * scale;
                        imageHeight = height * scale;
                        startX = (frameWidth - imageWidth) / 2.0;
                        startY = (frameHeight - imageHeight) / 2.0;
                    }

                    startX *= 0.0625;
                    startY *= 0.0625;
                    imageWidth *= 0.0625;
                    imageHeight *= 0.0625;

                    //Setups translations
                    GlStateManager.translate(8 * 0.0625, frameYOffset * 0.0625, 8 * 0.0625);
                    EnumFacing facing = state.getValue(BlockFurnitureTile.FACING);
                    GlStateManager.rotate(facing.getHorizontalIndex() * -90F, 0, 1, 0);
                    GlStateManager.translate(-frameWidth / 2 * 0.0625, 0, 0);
                    GlStateManager.translate(0, 0, frameZOffset * 0.0625);

                    //Render a black quad
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.getBuffer();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                    buffer.pos(0, 0, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(0, imageHeight * 0.0625, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(imageWidth * 0.0625, imageHeight * 0.0625, 0).color(0, 0, 0, 255).endVertex();
                    buffer.pos(imageWidth * 0.0625, 0, 0).color(0, 0, 0, 255).endVertex();
                    tessellator.draw();

                    //Render the Image
                    GlStateManager.translate(0, 0, -0.01 * 0.0625);
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    buffer.pos(startX, startY, 0).tex(0, 0).endVertex();
                    buffer.pos(startX, startY + imageHeight, 0).tex(0, 1).endVertex();
                    buffer.pos(startX + imageWidth, startY + imageHeight, 0).tex(1, 1).endVertex();
                    buffer.pos(startX + imageWidth, startY, 0).tex(1, 0).endVertex();
                    tessellator.draw();
                }
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
            }
        }
        GlStateManager.popMatrix();
    }
}
