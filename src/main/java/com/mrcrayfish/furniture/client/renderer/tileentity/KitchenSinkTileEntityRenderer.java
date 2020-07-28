package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrcrayfish.furniture.block.FurnitureHorizontalBlock;
import com.mrcrayfish.furniture.tileentity.KitchenSinkTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkTileEntityRenderer extends TileEntityRenderer<KitchenSinkTileEntity>
{
    public KitchenSinkTileEntityRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(KitchenSinkTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, int i1)
    {
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        Direction direction = tileEntity.getBlockState().get(FurnitureHorizontalBlock.DIRECTION);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(direction.getHorizontalIndex() * -90F - 90F));
        matrixStack.translate(-0.5, -0.5, -0.5);
        this.drawFluid(tileEntity, matrixStack, renderTypeBuffer, 2F * 0.0625F, 10F * 0.0625F, 2F * 0.0625F, 10F * 0.0625F, 5F * 0.0625F, 12F * 0.0625F, light);
        matrixStack.pop();
    }

    private void drawFluid(KitchenSinkTileEntity te, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, float x, float y, float z, float width, float height, float depth, int light)
    {
        Fluid fluid = te.getTank().getFluid().getFluid();
        if(fluid == Fluids.EMPTY)
            return;

        TextureAtlasSprite sprite = ForgeHooksClient.getFluidSprites(te.getWorld(), te.getPos(), fluid.getDefaultState())[0];

        float minU = sprite.getMinU();
        float maxU = Math.min(minU + (sprite.getMaxU() - minU) * depth, sprite.getMaxU());
        float minV = sprite.getMinV();
        float maxV = Math.min(minV + (sprite.getMaxV() - minV) * width, sprite.getMaxV());
        int waterColor = fluid.getAttributes().getColor(te.getWorld(), te.getPos());
        float red = (float)(waterColor >> 16 & 255) / 255.0F;
        float green = (float)(waterColor >> 8 & 255) / 255.0F;
        float blue = (float)(waterColor & 255) / 255.0F;

        height *= ((double) te.getTank().getFluidAmount() / (double) te.getTank().getCapacity());

        IVertexBuilder buffer = renderTypeBuffer.getBuffer(RenderType.getTranslucent());
        Matrix4f matrix = matrixStack.getLast().getMatrix();

        buffer.pos(matrix, x, y + height, z).color(red, green, blue, 1.0F).tex(maxU, minV).lightmap(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(matrix, x, y + height, z + depth).color(red, green, blue, 1.0F).tex(minU, minV).lightmap(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(matrix, x + width, y + height, z + depth).color(red, green, blue, 1.0F).tex(minU, maxV).lightmap(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(matrix, x + width, y + height, z).color(red, green, blue, 1.0F).tex(maxU, maxV).lightmap(light).normal(0.0F, 1.0F, 0.0F).endVertex();
    }
}
