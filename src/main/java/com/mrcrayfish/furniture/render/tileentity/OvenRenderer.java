package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityOven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class OvenRenderer extends TileEntitySpecialRenderer<TileEntityOven>
{
    private EntityItem ovenItem = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityOven oven, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        ovenItem.hoverStart = 0.0F;

        GlStateManager.pushMatrix();
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GlStateManager.translate(x + 0.3, y + 0.52, z + 0.5);
            GlStateManager.scale(0.66, 0.66, 0.66);

            for(int i = 0; i < oven.getSizeInventory(); i++)
            {
                double height = (i / 4) * -0.25D;
                double xOffset = (i % 2 != 0) ? 0.5 : 0;
                double zOffset = i >= (i / 4) + 2 && i <= (i / 4) + 4 ? 0.5 : 0;
                ItemStack food = oven.getStackInSlot(i);
                if(food != null)
                {
                    ovenItem.setItem(food);
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(xOffset, height, zOffset);
                        GlStateManager.rotate(-90F, 1, 0, 0);
                        GlStateManager.translate(0, -0.2, 0);
                        Minecraft.getMinecraft().getRenderManager().renderEntity(ovenItem, 0, 0, 0, 0.0F, 0.0F, false);
                    }
                    GlStateManager.popMatrix();
                }
            }
        }
        GlStateManager.popMatrix();
    }
}
