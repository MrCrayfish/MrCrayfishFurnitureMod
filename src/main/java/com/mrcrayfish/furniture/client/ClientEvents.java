package com.mrcrayfish.furniture.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ClientEvents
{
    @SubscribeEvent
    public static void onRenderOutline(DrawBlockHighlightEvent event)
    {
        if(FurnitureConfig.CLIENT.drawCollisionShapes.get())
        {
            event.setCanceled(true);
        }

        RayTraceResult result = event.getTarget();
        if(result.getType() == RayTraceResult.Type.BLOCK)
        {
            BlockPos pos = ((BlockRayTraceResult) result).getPos();
            BlockState state = Minecraft.getInstance().world.getBlockState(pos);
            VoxelShape collisionShape = state.getCollisionShape(Minecraft.getInstance().world, pos);
            ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
            double posX = renderInfo.getProjectedView().x;
            double posY = renderInfo.getProjectedView().y;
            double posZ = renderInfo.getProjectedView().z;
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.lineWidth(2.0F);
            GlStateManager.disableTexture();
            GlStateManager.depthMask(false);
            drawVoxelShapeParts(collisionShape, -posX + pos.getX(), -posY + pos.getY(), -posZ + pos.getZ(), 0.0F, 1.0F, 0.0F, 1.0F);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture();
            GlStateManager.disableBlend();
        }
    }

    public static void drawVoxelShapeParts(VoxelShape voxelShapeIn, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha)
    {
        List<AxisAlignedBB> boxes = voxelShapeIn.toBoundingBoxList();
        for(AxisAlignedBB box : boxes)
        {
            WorldRenderer.drawShape(VoxelShapes.create(box), xIn, yIn, zIn, red, green, blue, alpha);
        }
    }
}
