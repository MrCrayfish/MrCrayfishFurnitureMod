package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.block.FurnitureHorizontalBlock;
import com.mrcrayfish.furniture.tileentity.KitchenSinkTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeColors;
import org.lwjgl.opengl.GL11;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkTileEntityRenderer extends TileEntityRenderer<KitchenSinkTileEntity>
{
    @Override
    public void render(KitchenSinkTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage)
    {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translated(x, y, z);
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Direction direction = tileEntityIn.getBlockState().get(FurnitureHorizontalBlock.DIRECTION);
            GlStateManager.translated(0.5, 0.5, 0.5);
            GlStateManager.rotatef(direction.getHorizontalIndex() * -90F - 90F, 0, 1, 0);
            GlStateManager.translated(-0.5, -0.5, -0.5);
            this.drawFluid(tileEntityIn, 2 * 0.0625, 10 * 0.0625, 2 * 0.0625, 10 * 0.0625, 5 * 0.0625, 12 * 0.0625);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
    }

    private void drawFluid(KitchenSinkTileEntity te, double x, double y, double z, double width, double height, double depth)
    {
        Fluid fluid = te.getTank().getFluid().getFluid();
        if(fluid == Fluids.EMPTY)
            return;

        ResourceLocation resource = fluid.getAttributes().getStillTexture();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureMap().getAtlasSprite(resource.toString());
        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        double minU = sprite.getMinU();
        double maxU = Math.min(minU + (sprite.getMaxU() - minU) * depth, sprite.getMaxU());
        double minV = sprite.getMinV();
        double maxV = Math.min(minV + (sprite.getMaxV() - minV) * width, sprite.getMaxV());
        int light = getWorld().getCombinedLight(te.getPos(), fluid.getAttributes().getLuminosity());
        int lightX = light >> 0x10 & 0xFFFF;
        int lightY = light & 0xFFFF;
        int waterColor = fluid.getAttributes().getColor(te.getWorld(), te.getPos());
        float red = (float)(waterColor >> 16 & 255) / 255.0F;
        float green = (float)(waterColor >> 8 & 255) / 255.0F;
        float blue = (float)(waterColor & 255) / 255.0F;

        height *= ((double) te.getTank().getFluidAmount() / (double) te.getTank().getCapacity());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        buffer.pos(x, y + height, z).tex(maxU, minV).lightmap(lightX, lightY).color(red, green, blue, 1.0F).endVertex();
        buffer.pos(x, y + height, z + depth).tex(minU, minV).lightmap(lightX, lightY).color(red, green, blue, 1.0F).endVertex();
        buffer.pos(x + width, y + height, z + depth).tex(minU, maxV).lightmap(lightX, lightY).color(red, green, blue, 1.0F).endVertex();
        buffer.pos(x + width, y + height, z).tex(maxU, maxV).lightmap(lightX, lightY).color(red, green, blue, 1.0F).endVertex();
        tessellator.draw();
    }
}
