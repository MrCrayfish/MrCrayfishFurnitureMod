package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.block.OfficeChairBlock;
import com.mrcrayfish.furniture.tileentity.OfficeChairTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;

public class OfficeChairTileEntityRenderer extends TileEntityRenderer<OfficeChairTileEntity>
{
    public OfficeChairTileEntityRenderer(TileEntityRendererDispatcher dispatcher) { super(dispatcher); }

    private Minecraft minecraft = Minecraft.getInstance();

    @Override
    public void render(OfficeChairTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int i, int i1)
    {
        BlockPos pos = tileEntity.getPos();
        BlockState tempState = tileEntity.getWorld().getBlockState(pos);

        matrixStack.push();
        {
            matrixStack.translate(pos.getX(), pos.getY(), pos.getZ());

            matrixStack.translate(0.5, 0.0, 0.5);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(tileEntity.getRotation()));
            matrixStack.translate(-0.5, 0.0, -0.5);

            BlockState state = tempState.getBlockState().with(OfficeChairBlock.DIRECTION, Direction.NORTH);

            GlStateManager.disableLighting();
            GlStateManager.enableTexture();

            Tessellator tessellator = Tessellator.getInstance();

            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.BLOCK);
            matrixStack.translate(-tileEntity.getPos().getX(), -tileEntity.getPos().getY(), -tileEntity.getPos().getZ());

            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            IBakedModel ibakedmodel = minecraft.getBlockRendererDispatcher().getBlockModelShapes().getModel(state);

            matrixStack.translate(0.0D, 0.0D, 0.0D);
            tessellator.draw();

            GlStateManager.enableLighting();

        }
        matrixStack.pop();
    }

}
