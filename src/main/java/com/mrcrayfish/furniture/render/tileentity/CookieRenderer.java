package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityCookieJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class CookieRenderer extends TileEntitySpecialRenderer<TileEntityCookieJar>
{
    private ItemStack cookie = new ItemStack(Items.COOKIE);
    private EntityItem entityItem = new EntityItem(null, 0D, 0D, 0D);

    @Override
    public void render(TileEntityCookieJar cookieJar, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        entityItem.setItem(cookie);

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);

        this.entityItem.hoverStart = 0.0F;
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.05F, (float) z + 0.18F);
        GL11.glRotatef(180, 0, 1, 1);
        GlStateManager.translate(0, -0.1, 0);
        GL11.glScalef(0.9F, 0.9F, 0.9F);


        int metadata = cookieJar.getBlockMetadata();
        for(int i = 0; i < metadata; i++)
        {
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.1D * i, 0.0F, 0.0F, false);
        }

        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }
}