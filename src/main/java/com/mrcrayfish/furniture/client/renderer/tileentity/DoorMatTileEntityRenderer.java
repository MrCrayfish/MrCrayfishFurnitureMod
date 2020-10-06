package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.block.DoorMatBlock;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.text.ITextProperties;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class DoorMatTileEntityRenderer extends TileEntityRenderer<DoorMatTileEntity>
{
    public DoorMatTileEntityRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(DoorMatTileEntity tileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        if(tileEntity.getMessage() != null)
        {
            BlockState state = tileEntity.getBlockState();
            if(state.getBlock() instanceof DoorMatBlock)
            {
                matrixStack.push(); //Push

                matrixStack.translate(0.5, 0.0626, 0.5);

                int rotation = state.get(DoorMatBlock.DIRECTION).getHorizontalIndex();
                matrixStack.rotate(Vector3f.YP.rotationDegrees(-90F * rotation + 180F));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-90F));

                matrixStack.scale(1.0F, -1.0F, -1.0F);
                matrixStack.scale(0.0125F, 0.0125F, 0.0125F);

                FontRenderer fontRenderer = this.renderDispatcher.getFontRenderer();
                List<IReorderingProcessor> lines = fontRenderer.trimStringToWidth(ITextProperties.func_240652_a_(tileEntity.getMessage()), 60);
                matrixStack.translate(0.0, -(lines.size() * fontRenderer.FONT_HEIGHT - 1.0) / 2.0, 0);

                for(int j = 0; j < lines.size(); j++)
                {
                    matrixStack.push();
                    matrixStack.translate(-fontRenderer.func_243245_a(lines.get(j)) / 2.0, (j * fontRenderer.FONT_HEIGHT), 0.0);
                    fontRenderer.func_238416_a_(lines.get(j), 0, 0, i1, false, matrixStack.getLast().getMatrix(), renderTypeBuffer, false, 0, i);
                    matrixStack.pop();
                }
                matrixStack.pop(); //Pop
            }
        }
    }
}
