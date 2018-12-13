package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockModernSlidingDoor;
import com.mrcrayfish.furniture.tileentity.TileEntityModernSlidingDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.model.animation.FastTESR;

/**
 * Author: MrCrayfish
 */
public class ModernSlidingDoorRenderer extends FastTESR<TileEntityModernSlidingDoor>
{
    @Override
    public void renderTileEntityFast(TileEntityModernSlidingDoor te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer)
    {
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableCull();
            BlockPos pos = te.getPos();
            IBlockState state = te.getWorld().getBlockState(pos);
            if(state.getPropertyKeys().contains(BlockModernSlidingDoor.FACING))
            {
                BlockRendererDispatcher rendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                IBakedModel model = rendererDispatcher.getBlockModelShapes().getModelForState(state);
                double slideProgress = te.getSlideProgress(partialTicks);
                Vec3i vec = state.getValue(BlockModernSlidingDoor.FACING).rotateYCCW().getDirectionVec();
                buffer.setTranslation(x - pos.getX() + slideProgress * vec.getX(), y - pos.getY(), z - pos.getZ() + slideProgress * vec.getZ());
                rendererDispatcher.getBlockModelRenderer().renderModel(te.getWorld(), model, state, pos, buffer, true);
            }
        }
        GlStateManager.popMatrix();
    }
}