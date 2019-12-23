package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkTileEntityRenderer extends TileEntityRenderer<KitchenSinkTileEntity>
{
    public KitchenSinkTileEntityRenderer()
    {
        super(TileEntityRendererDispatcher.instance);
    }

    @Override
    public void func_225616_a_(KitchenSinkTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        matrixStack.func_227860_a_();
        matrixStack.func_227861_a_(0.5, 0.5, 0.5);
        Direction direction = tileEntity.getBlockState().get(FurnitureHorizontalBlock.DIRECTION);
        matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(direction.getHorizontalIndex() * -90F - 90F));
        matrixStack.func_227861_a_(-0.5, -0.5, -0.5);
        this.drawFluid(tileEntity, matrixStack, 2 * 0.0625, 10 * 0.0625, 2 * 0.0625, 10 * 0.0625, 5 * 0.0625, 12 * 0.0625);
        matrixStack.func_227865_b_();
    }

    private void drawFluid(KitchenSinkTileEntity te, MatrixStack matrixStack, double x, double y, double z, double width, double height, double depth)
    {
        Fluid fluid = te.getTank().getFluid().getFluid();
        if(fluid == Fluids.EMPTY)
            return;

        ResourceLocation resource = fluid.getAttributes().getStillTexture();
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().func_229356_a_(AtlasTexture.LOCATION_BLOCKS_TEXTURE).getSprite(resource);
        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        float minU = sprite.getMinU();
        float maxU = (float) Math.min(minU + (sprite.getMaxU() - minU) * depth, sprite.getMaxU());
        float minV = sprite.getMinV();
        float maxV = (float) Math.min(minV + (sprite.getMaxV() - minV) * width, sprite.getMaxV());
        int light = te.getWorld().func_226659_b_(te.getPos(), fluid.getAttributes().getLuminosity());
        int lightX = light >> 0x10 & 0xFFFF;
        int lightY = light & 0xFFFF;
        int waterColor = fluid.getAttributes().getColor(te.getWorld(), te.getPos());
        float red = (float)(waterColor >> 16 & 255) / 255.0F;
        float green = (float)(waterColor >> 8 & 255) / 255.0F;
        float blue = (float)(waterColor & 255) / 255.0F;

        height *= ((double) te.getTank().getFluidAmount() / (double) te.getTank().getCapacity());

        RenderSystem.pushMatrix();

        RenderSystem.multMatrix(matrixStack.func_227866_c_().func_227870_a_());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderHelper.func_227780_a_();
        RenderSystem.enableDepthTest();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.func_225582_a_(x, y + height, z).func_225583_a_(maxU, minV).func_227885_a_(red, green, blue, 1.0F).endVertex();
        buffer.func_225582_a_(x, y + height, z + depth).func_225583_a_(minU, minV).func_227885_a_(red, green, blue, 1.0F).endVertex();
        buffer.func_225582_a_(x + width, y + height, z + depth).func_225583_a_(minU, maxV).func_227885_a_(red, green, blue, 1.0F).endVertex();
        buffer.func_225582_a_(x + width, y + height, z).func_225583_a_(maxU, maxV).func_227885_a_(red, green, blue, 1.0F).endVertex();
        tessellator.draw();

        RenderSystem.disableDepthTest();
        RenderHelper.disableStandardItemLighting();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }
}
