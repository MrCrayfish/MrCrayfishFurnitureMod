package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityCoffeeTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * Author: MrCrayfish
 */
public class CoffeeTableRenderer extends TileEntitySpecialRenderer<TileEntityCoffeeTable>
{
    @Override
    public void render(TileEntityCoffeeTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(te.getFood().isEmpty())
            return;

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.5, 0.5);
            GlStateManager.rotate(-90F * te.getRotation(), 0, 1, 0);
            GlStateManager.translate(-0.5, -0.5, -0.5);
            GlStateManager.rotate(90F, 1, 0, 0);
            GlStateManager.translate(0.5, 0.5, -0.5 - 0.03125);

            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(te.getFood(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
        }
        GlStateManager.popMatrix();
    }
}
