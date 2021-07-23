package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrcrayfish.furniture.tileentity.GrillBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class GrillBlockEntityRenderer implements BlockEntityRenderer<GrillBlockEntity>
{
    public GrillBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(GrillBlockEntity tileEntity, float partialTicks, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        NonNullList<ItemStack> grill = tileEntity.getGrill();
        for(int j = 0; j < grill.size(); j++)
        {
            ItemStack stack = grill.get(j);
            if(!stack.isEmpty())
            {
                poseStack.pushPose();

                if(tileEntity.isFlipping(j))
                {
                    float progress = Mth.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillBlockEntity.MAX_FLIPPING_COUNTER) / GrillBlockEntity.MAX_FLIPPING_COUNTER;
                    poseStack.translate(0.0, Math.sin(Math.toRadians(180 * progress)), 0.0);
                }

                poseStack.translate(0.5, 1.0, 0.5);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90F));
                poseStack.translate(-0.2 + 0.4 * (j % 2), -0.2 + 0.4 * (j / 2), 0.0);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90F * tileEntity.getRotations()[j]));

                if(tileEntity.isFlipping(j))
                {
                    float progress = Mth.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillBlockEntity.MAX_FLIPPING_COUNTER) / GrillBlockEntity.MAX_FLIPPING_COUNTER;
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(-540F * progress));
                }
                else if(tileEntity.isFlipped(j))
                {
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(180F));
                }

                //TODO investigate how the last value changes the render
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, source, 0);

                poseStack.popPose();
            }
        }

        NonNullList<ItemStack> fuel = tileEntity.getFuel();
        for(int j = 0; j < fuel.size(); ++j)
        {
            ItemStack stack = fuel.get(j);
            if(!stack.isEmpty())
            {
                poseStack.pushPose();
                poseStack.translate(0.5, 0.85, 0.5);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90F));
                poseStack.translate(-0.2 + 0.2 * (j % 3), -0.2 + 0.2 * (j / 3), 0.0);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(10F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(10F));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(5F));
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, source, 0);
                poseStack.popPose();
            }
        }
    }
}
