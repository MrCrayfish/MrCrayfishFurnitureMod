package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockDigitalClock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.items.ItemColored;
import com.mrcrayfish.furniture.tileentity.TileEntityDigitalClock;
import com.mrcrayfish.furniture.util.TimeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import java.awt.*;

/**
 * Author: MrCrayfish
 */
public class DigitalClockRenderer extends TileEntitySpecialRenderer<TileEntityDigitalClock>
{
    @Override
    public void render(TileEntityDigitalClock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        IBlockState state = te.getWorld().getBlockState(te.getPos());
        if(state.getBlock() != FurnitureBlocks.DIGITAL_CLOCK)
            return;

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.depthMask(false);
            GlStateManager.translate(0.5, 0.5, 0.5);
            GlStateManager.rotate(state.getValue(BlockDigitalClock.FACING).getHorizontalIndex() * -90F, 0, 1, 0);
            GlStateManager.rotate(180F, 0, 1, 0);
            GlStateManager.translate(0.0675, 0.005, -0.032);
            GlStateManager.translate(-4.2 * 0.0625, -5.0 * 0.0625, 1.55 * 0.0625);
            GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
            GlStateManager.scale(1.5, 1.5, 1.5);
            GlStateManager.enableRescaleNormal();
            GlStateManager.glNormal3f(0F, 1.0F, 0F);
            Minecraft.getMinecraft().fontRenderer.drawString(ItemColored.getFromColor(te.getTextColor()) + TimeUtil.getFormattedTime(Minecraft.getMinecraft().world.getWorldTime()), 0, 0, Color.WHITE.getRGB());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.depthMask(true);
            RenderHelper.enableStandardItemLighting();
        }
        GlStateManager.popMatrix();
    }
}