package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.block.DoorMatBlock;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class DoorMatTileEntityRenderer extends TileEntityRenderer<DoorMatTileEntity>
{
    @Override
    public void render(DoorMatTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if(tileEntity.getMessage() != null)
        {
            BlockState state = tileEntity.getBlockState();
            if(state.getBlock() instanceof DoorMatBlock)
            {
                int rotation = state.get(DoorMatBlock.DIRECTION).getHorizontalIndex();
                GlStateManager.pushMatrix();
                GlStateManager.translated(x, y, z);
                GlStateManager.translated(0.5, 0.0626, 0.5);
                GlStateManager.rotatef(-90F * rotation, 0, 1, 0);
                GlStateManager.rotatef(180F, 0, 1, 0);
                GlStateManager.rotatef(-90F, 1, 0, 0);
                GlStateManager.scalef(1, -1, -1);
                GlStateManager.scalef(0.0125F, 0.0125F, 0.0125F);
                FontRenderer fontRenderer = this.getFontRenderer();
                List<String> lines = fontRenderer.listFormattedStringToWidth(tileEntity.getMessage(), 60);
                GlStateManager.translatef(0, -(lines.size() * fontRenderer.FONT_HEIGHT - 1) / 2, 0);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                for(int i = 0; i < lines.size(); i++)
                {
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef(-fontRenderer.getStringWidth(lines.get(i)) / 2F, (i * fontRenderer.FONT_HEIGHT), 0);
                    fontRenderer.drawString(lines.get(i), 0, 0, 0x382214);
                    GlStateManager.popMatrix();
                }
                GlStateManager.depthMask(true);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
}
