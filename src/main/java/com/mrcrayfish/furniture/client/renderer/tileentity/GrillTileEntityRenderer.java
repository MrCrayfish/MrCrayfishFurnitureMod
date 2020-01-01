package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class GrillTileEntityRenderer extends TileEntityRenderer<GrillTileEntity> implements Function<TileEntityRendererDispatcher, TileEntityRenderer<? super TileEntity>> {
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
                matrixStack.func_227860_a_();

                if(tileEntity.isFlipping(j))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    matrixStack.func_227861_a_(0.0, Math.sin(Math.toRadians(180 * progress)), 0.0);
                }

                matrixStack.func_227861_a_(0.5, 1.0, 0.5);
                matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90F));
                matrixStack.func_227861_a_(-0.2 + 0.4 * (j % 2), -0.2 + 0.4 * (j / 2), 0.0);
                matrixStack.func_227862_a_(0.375F, 0.375F, 0.375F);
                matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90F * tileEntity.getRotations()[j]));

                if(tileEntity.isFlipping(j))
                {
                    float progress = MathHelper.clamp(tileEntity.getFlippingCount(j) + partialTicks, 0F, GrillTileEntity.MAX_FLIPPING_COUNTER) / GrillTileEntity.MAX_FLIPPING_COUNTER;
                    matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-540F * progress));
                }
                else if(tileEntity.isFlipped(j))
                {
                    matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180F));
                }

                Minecraft.getInstance().getItemRenderer().func_229110_a_(stack, ItemCameraTransforms.TransformType.FIXED, i, i1, matrixStack, renderTypeBuffer);

                matrixStack.func_227865_b_();
            }
        }

        NonNullList<ItemStack> fuel = tileEntity.getFuel();
        for(int j = 0; j < fuel.size(); ++j)
        {
            ItemStack stack = fuel.get(j);
            if(!stack.isEmpty())
            {
                matrixStack.func_227860_a_();
                matrixStack.func_227861_a_(0.5, 0.85, 0.5);
                matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90F));
                matrixStack.func_227861_a_(-0.2 + 0.2 * (j % 3), -0.2 + 0.2 * (j / 3), 0.0);
                matrixStack.func_227862_a_(0.375F, 0.375F, 0.375F);
                matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(10F));
                matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(10F));
                Minecraft.getInstance().getItemRenderer().func_229110_a_(stack, ItemCameraTransforms.TransformType.FIXED, i, i1, matrixStack, renderTypeBuffer);
                matrixStack.func_227865_b_();
            }
        }
    }

    @Override
    public TileEntityRenderer<? super TileEntity> apply(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        return null;
    }
}
