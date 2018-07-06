package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityCeilingFan;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CeilingFanRenderer extends TileEntitySpecialRenderer<TileEntityCeilingFan>
{
    @Override
    public void render(TileEntityCeilingFan te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.5, 0.5);
            GlStateManager.scale(1.35, 1.0, 1.35);

            float rotation = te.prevFanRotation + (te.fanRotation - te.prevFanRotation) * partialTicks;
            GlStateManager.rotate(-rotation, 0, 1, 0);

            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FurnitureItems.CEILING_FAN_FANS), ItemCameraTransforms.TransformType.NONE);
        }
        GlStateManager.popMatrix();
    }
}
