package com.mrcrayfish.furniture.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.Reference;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class ClientEvents
{
    @SubscribeEvent
    public static void onRenderOutline(RenderHighlightEvent.Block event)
    {
        if(!FurnitureConfig.CLIENT.drawCollisionShapes.get())
        {
            return;
        }

        event.setCanceled(true);

        BlockHitResult result = event.getTarget();
        BlockPos pos = result.getBlockPos();
        BlockState state = Minecraft.getInstance().level.getBlockState(pos);
        VoxelShape collisionShape = state.getCollisionShape(Minecraft.getInstance().level, pos);
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        double posX = camera.getPosition().x;
        double posY = camera.getPosition().y;
        double posZ = camera.getPosition().z;
        VertexConsumer builder = event.getMultiBufferSource().getBuffer(RenderType.lines());
        drawShape(event.getPoseStack(), builder, collisionShape, -posX + pos.getX(), -posY + pos.getY(), -posZ + pos.getZ(), 0.0F, 1.0F, 0.0F, 1.0F);
    }

    private static void drawShape(PoseStack poseStack, VertexConsumer consumer, VoxelShape voxelShape, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha)
    {
        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f matrix3f = poseStack.last().normal();
        voxelShape.forAllEdges((x1, y1, z1, x2, y2, z2) ->
        {
            consumer.vertex(matrix4f, (float) (x1 + xIn), (float) (y1 + yIn), (float) (z1 + zIn)).color(red, green, blue, alpha).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            consumer.vertex(matrix4f, (float) (x2 + xIn), (float) (y2 + yIn), (float) (z2 + zIn)).color(red, green, blue, alpha).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        });
    }
}
