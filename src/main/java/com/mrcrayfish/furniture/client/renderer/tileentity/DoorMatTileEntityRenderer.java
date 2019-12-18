package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.block.DoorMatBlock;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class DoorMatTileEntityRenderer extends TileEntityRenderer<DoorMatTileEntity>
{
    public DoorMatTileEntityRenderer()
    {
        super(TileEntityRendererDispatcher.instance);
    }

    @Override
    public void func_225616_a_(DoorMatTileEntity tileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        if(tileEntity.getMessage() != null)
        {
            BlockState state = tileEntity.getBlockState();
            if(state.getBlock() instanceof DoorMatBlock)
            {
                int rotation = state.get(DoorMatBlock.DIRECTION).getHorizontalIndex();
                RenderSystem.pushMatrix();
                RenderSystem.translated(0.5, 0.0626, 0.5);
                RenderSystem.rotatef(-90F * rotation, 0, 1, 0);
                RenderSystem.rotatef(180F, 0, 1, 0);
                RenderSystem.rotatef(-90F, 1, 0, 0);
                RenderSystem.scalef(1, -1, -1);
                RenderSystem.scalef(0.0125F, 0.0125F, 0.0125F);
                FontRenderer fontRenderer = this.field_228858_b_.getFontRenderer();
                List<String> lines = fontRenderer.listFormattedStringToWidth(tileEntity.getMessage(), 60);
                RenderSystem.translatef(0, -(lines.size() * fontRenderer.FONT_HEIGHT - 1) / 2, 0);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableLighting();
                RenderSystem.depthMask(false);
                for(int j = 0; j < lines.size(); j++)
                {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(-fontRenderer.getStringWidth(lines.get(j)) / 2F, (j * fontRenderer.FONT_HEIGHT), 0);
                    fontRenderer.drawString(lines.get(j), 0, 0, 0x382214);
                    RenderSystem.popMatrix();
                }
                RenderSystem.depthMask(true);
                RenderSystem.enableLighting();
                RenderSystem.popMatrix();
            }
        }
    }
}
