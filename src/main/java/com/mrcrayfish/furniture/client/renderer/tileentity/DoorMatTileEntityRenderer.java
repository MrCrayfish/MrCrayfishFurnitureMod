package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.block.DoorMatBlock;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

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
    public void func_225616_a_(DoorMatTileEntity tileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        if(tileEntity.getMessage() != null)
        {
            BlockState state = tileEntity.getBlockState();
            if(state.getBlock() instanceof DoorMatBlock)
            {
                matrixStack.func_227860_a_(); //Push

                matrixStack.func_227861_a_(0.5, 0.0626, 0.5);

                int rotation = state.get(DoorMatBlock.DIRECTION).getHorizontalIndex();
                matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-90F * rotation + 180F));
                matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-90F));

                matrixStack.func_227862_a_(1.0F, -1.0F, -1.0F);
                matrixStack.func_227862_a_(0.0125F, 0.0125F, 0.0125F);

                FontRenderer fontRenderer = this.field_228858_b_.getFontRenderer();
                List<String> lines = fontRenderer.listFormattedStringToWidth(tileEntity.getMessage(), 60);
                matrixStack.func_227861_a_(0.0, -(lines.size() * fontRenderer.FONT_HEIGHT - 1.0) / 2.0, 0);

                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                
                RenderSystem.depthMask(false);
                for(int j = 0; j < lines.size(); j++)
                {
                    matrixStack.func_227860_a_();
                    matrixStack.func_227861_a_(-fontRenderer.getStringWidth(lines.get(j)) / 2.0, (j * fontRenderer.FONT_HEIGHT), 0.0);
                    fontRenderer.func_228079_a_(lines.get(j), 0, 0, i1, false, matrixStack.func_227866_c_().func_227870_a_(), renderTypeBuffer, false, 0, i);
                    matrixStack.func_227865_b_();
                }
                RenderSystem.depthMask(true);

                matrixStack.func_227865_b_(); //Pop
            }
        }
    }
}
