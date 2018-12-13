package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class CupRenderer extends TileEntitySpecialRenderer<TileEntityCup>
{
    @Override
    public void render(TileEntityCup tileEntityCup, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(tileEntityCup.getDrink() != null)
        {
            GlStateManager.pushMatrix();
            {
                GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);

                GlStateManager.enableBlend();
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GlStateManager.disableLighting();
                GlStateManager.disableTexture2D();
                GlStateManager.color(tileEntityCup.red / 255F, tileEntityCup.green / 255F, tileEntityCup.blue / 255F, 1.0F);
                GlStateManager.enableRescaleNormal();

                renderCuboid(-0.124F, 0.5F * 0.0625F, -0.124F, 0.124F, 0.4F, 0.124F);

                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();
        }
    }

    public void renderCuboid(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex3f(x1, y1, z1);
            GL11.glVertex3f(x1, y1, z2);
            GL11.glVertex3f(x1, y2, z2);
            GL11.glVertex3f(x1, y2, z1);

            GL11.glVertex3f(x2, y1, z1);
            GL11.glVertex3f(x1, y1, z1);
            GL11.glVertex3f(x1, y2, z1);
            GL11.glVertex3f(x2, y2, z1);

            GL11.glVertex3f(x1, y1, z2);
            GL11.glVertex3f(x2, y1, z2);
            GL11.glVertex3f(x2, y2, z2);
            GL11.glVertex3f(x1, y2, z2);

            GL11.glVertex3f(x2, y1, z2);
            GL11.glVertex3f(x2, y1, z1);
            GL11.glVertex3f(x2, y2, z1);
            GL11.glVertex3f(x2, y2, z2);

            GL11.glVertex3f(x1, y2, z1);
            GL11.glVertex3f(x1, y2, z2);
            GL11.glVertex3f(x2, y2, z2);
            GL11.glVertex3f(x2, y2, z1);

            GL11.glVertex3f(x1, y1, z1);
            GL11.glVertex3f(x1, y1, z2);
            GL11.glVertex3f(x2, y1, z2);
            GL11.glVertex3f(x2, y1, z1);
        }
        GL11.glEnd();
    }
}