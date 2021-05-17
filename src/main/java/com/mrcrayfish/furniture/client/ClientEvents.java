package com.mrcrayfish.furniture.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class ClientEvents
{
    @SubscribeEvent
    public static void onRenderOutline(DrawHighlightEvent.HighlightBlock event)
    {
        if(!FurnitureConfig.CLIENT.drawCollisionShapes.get())
        {
            return;
        }

        event.setCanceled(true);

        BlockRayTraceResult result = event.getTarget();
        BlockPos pos = result.getPos();
        BlockState state = Minecraft.getInstance().world.getBlockState(pos);
        VoxelShape collisionShape = state.getCollisionShape(Minecraft.getInstance().world, pos);
        ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        double posX = renderInfo.getProjectedView().x;
        double posY = renderInfo.getProjectedView().y;
        double posZ = renderInfo.getProjectedView().z;
        IVertexBuilder builder = event.getBuffers().getBuffer(RenderType.getLines());
        drawShape(event.getMatrix(), builder, collisionShape, -posX + pos.getX(), -posY + pos.getY(), -posZ + pos.getZ(), 0.0F, 1.0F, 0.0F, 1.0F);
    }

    private static void drawShape(MatrixStack matrixStack, IVertexBuilder builder, VoxelShape voxelShape, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha)
    {
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        voxelShape.forEachEdge((x1, y1, z1, x2, y2, z2) ->
        {
            builder.pos(matrix4f, (float) (x1 + xIn), (float) (y1 + yIn), (float) (z1 + zIn)).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, (float) (x2 + xIn), (float) (y2 + yIn), (float) (z2 + zIn)).color(red, green, blue, alpha).endVertex();
        });
    }
}
