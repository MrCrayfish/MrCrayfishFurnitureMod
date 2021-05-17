package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Author: MrCrayfish
 */
public class GrillTileEntityRenderer extends TileEntityRenderer<GrillTileEntity>
{
    public GrillTileEntityRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(GrillTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        NonNullList<ItemStack> grill = tileEntity.getGrill();
        for(int j = 0; j < grill.size(); j++)
        {
            ItemStack stack = grill.get(j);
            if(!stack.isEmpty())
            {
                matrixStack.push();

                if(tileEntity.isFlipping(j))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    matrixStack.translate(0.0, Math.sin(Math.toRadians(180 * progress)), 0.0);
                }

                matrixStack.translate(0.5, 1.0, 0.5);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90F));
                matrixStack.translate(-0.2 + 0.4 * (j % 2), -0.2 + 0.4 * (j / 2), 0.0);
                matrixStack.scale(0.375F, 0.375F, 0.375F);
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(90F * tileEntity.getRotations()[j]));

                if(tileEntity.isFlipping(j))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    matrixStack.rotate(Vector3f.XP.rotationDegrees(-540F * progress));
                }
                else if(tileEntity.isFlipped(j))
                {
                    matrixStack.rotate(Vector3f.XP.rotationDegrees(180F));
                }

                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, i, i1, matrixStack, renderTypeBuffer);

                matrixStack.pop();
            }
        }

        NonNullList<ItemStack> fuel = tileEntity.getFuel();
        for(int j = 0; j < fuel.size(); ++j)
        {
            ItemStack stack = fuel.get(j);
            if(!stack.isEmpty())
            {
                matrixStack.push();
                matrixStack.translate(0.5, 0.85, 0.5);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90F));
                matrixStack.translate(-0.2 + 0.2 * (j % 3), -0.2 + 0.2 * (j / 3), 0.0);
                matrixStack.scale(0.375F, 0.375F, 0.375F);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(10F));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(10F));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(5F));
                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, i, i1, matrixStack, renderTypeBuffer);
                matrixStack.pop();
            }
        }
    }
}
