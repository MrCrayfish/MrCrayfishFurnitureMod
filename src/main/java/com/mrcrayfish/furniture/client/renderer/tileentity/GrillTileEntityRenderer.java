package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Author: MrCrayfish
 */
public class GrillTileEntityRenderer extends TileEntityRenderer<GrillTileEntity>
{
    @Override
    public void render(GrillTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        NonNullList<ItemStack> grill = tileEntity.getGrill();
        for(int i = 0; i < grill.size(); ++i)
        {
            ItemStack stack = grill.get(i);
            if(!stack.isEmpty())
            {
                GlStateManager.pushMatrix();
                GlStateManager.translatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
                GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.translatef(-0.2F + 0.4F * (i % 2), -0.2F + 0.4F * (i / 2), 0.0F);
                GlStateManager.scalef(0.375F, 0.375F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }
        }

        NonNullList<ItemStack> fuel = tileEntity.getFuel();
        for(int i = 0; i < fuel.size(); ++i)
        {
            ItemStack stack = fuel.get(i);
            if(!stack.isEmpty())
            {
                GlStateManager.pushMatrix();
                GlStateManager.translatef((float) x + 0.5F, (float) y + 0.85F, (float) z + 0.5F);
                GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.translatef(-0.2F + 0.2F * (i % 3), -0.2F + 0.2F * (i / 3), 0.0F);
                GlStateManager.scalef(0.375F, 0.375F, 0.375F);
                GlStateManager.rotatef(10.0F, 0.0F, 1.0F, 1.0F);
                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }
        }
    }
}
