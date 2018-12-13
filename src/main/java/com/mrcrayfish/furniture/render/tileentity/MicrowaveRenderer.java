package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;

public class MicrowaveRenderer extends TileEntitySpecialRenderer<TileEntityMicrowave>
{
    private EntityItem entityFood = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityMicrowave microwave, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int metadata = microwave.getBlockMetadata();

        if(microwave.getItem() != null)
        {
            entityFood.setItem(microwave.getItem());

            GL11.glPushMatrix();
            this.entityFood.hoverStart = 0.0F;

            float xOffset = 0.0F;
            float zOffset = 0.0F;

            switch(metadata)
            {
                case 0:
                    xOffset += 0.1F;
                    break;
                case 1:
                    xOffset += 0.2F;
                    zOffset += 0.3F;
                    break;
                case 2:
                    xOffset -= 0.1F;
                    zOffset += 0.4F;
                    break;
                case 3:
                    xOffset -= 0.2F;
                    zOffset += 0.1F;
                    break;
            }

            GL11.glTranslatef((float) x + 0.5F + xOffset, (float) y + 0.075F, (float) z + 0.3F + zOffset);
            GL11.glRotatef(metadata * -90F, 0, 1, 0);
            GL11.glRotatef(180, 0, 1, 1);
            GlStateManager.translate(0, -0.3, 0);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityFood, 0.0D, 0.0D, 0.075D, 0.0F, 0.0F, false);
            GL11.glPopMatrix();
        }
    }
}