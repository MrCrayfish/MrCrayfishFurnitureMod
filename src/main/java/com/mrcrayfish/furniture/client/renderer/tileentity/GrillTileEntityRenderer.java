package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

/**
 * Author: MrCrayfish
 */
public class GrillTileEntityRenderer extends TileEntityRenderer<GrillTileEntity>
{
    public GrillTileEntityRenderer()
    {
        super(TileEntityRendererDispatcher.instance);
    }

    @Override
    public void func_225616_a_(GrillTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        NonNullList<ItemStack> grill = tileEntity.getGrill();
        for(int j = 0; j < grill.size(); j++)
        {
            ItemStack stack = grill.get(j);
            if(!stack.isEmpty())
            {
                RenderSystem.pushMatrix();

                if(tileEntity.isFlipping(j))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    RenderSystem.translatef(0, (float) Math.sin(Math.toRadians(180 * progress)) * 1.0F, 0);
                }

                //RenderSystem.translatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
                RenderSystem.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
                RenderSystem.translatef(-0.2F + 0.4F * (j % 2), -0.2F + 0.4F * (j / 2), 0.0F);
                RenderSystem.scalef(0.375F, 0.375F, 0.375F);
                RenderSystem.rotatef(90F * tileEntity.getRotations()[j], 0, 0, 1);

                if(tileEntity.isFlipping(j))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    RenderSystem.rotatef(-540F * progress, 1, 0, 0);
                }
                else if(tileEntity.isFlipped(j))
                {
                    RenderSystem.rotatef(180F, 1, 0, 0);
                }

                Minecraft.getInstance().getItemRenderer().func_229110_a_(stack, ItemCameraTransforms.TransformType.FIXED, i, i1, matrixStack, renderTypeBuffer);
                RenderSystem.popMatrix();
            }
        }

        NonNullList<ItemStack> fuel = tileEntity.getFuel();
        for(int j = 0; j < fuel.size(); ++j)
        {
            ItemStack stack = fuel.get(j);
            if(!stack.isEmpty())
            {
                RenderSystem.pushMatrix();
                //RenderSystem.translatef((float) x + 0.5F, (float) y + 0.85F, (float) z + 0.5F);
                RenderSystem.rotatef(90.0F, 1, 0, 0);
                RenderSystem.translatef(-0.2F + 0.2F * (j % 3), -0.2F + 0.2F * (j / 3), 0.0F);
                RenderSystem.scalef(0.375F, 0.375F, 0.375F);
                RenderSystem.rotatef(10.0F, 0, 1, 1);
                Minecraft.getInstance().getItemRenderer().func_229110_a_(stack, ItemCameraTransforms.TransformType.FIXED, i, i1, matrixStack, renderTypeBuffer);
                RenderSystem.popMatrix();
            }
        }
    }
}
