package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

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

                if(tileEntity.isFlipping(i))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(i) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    GlStateManager.translatef(0, (float) Math.sin(Math.toRadians(180 * progress)) * 1.0F, 0);
                }

                GlStateManager.translatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
                GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.translatef(-0.2F + 0.4F * (i % 2), -0.2F + 0.4F * (i / 2), 0.0F);
                GlStateManager.scalef(0.375F, 0.375F, 0.375F);
                GlStateManager.rotatef(90F * tileEntity.getRotations()[i], 0, 0, 1);

                if(tileEntity.isFlipping(i))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(i) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    GlStateManager.rotatef(-540F * progress, 1, 0, 0);
                }
                else if(tileEntity.isFlipped(i))
                {
                    GlStateManager.rotatef(180F, 1, 0, 0);
                }

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
                GlStateManager.rotatef(90.0F, 1, 0, 0);
                GlStateManager.translatef(-0.2F + 0.2F * (i % 3), -0.2F + 0.2F * (i / 3), 0.0F);
                GlStateManager.scalef(0.375F, 0.375F, 0.375F);
                GlStateManager.rotatef(10.0F, 0, 1, 1);
                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }
        }
    }
}
