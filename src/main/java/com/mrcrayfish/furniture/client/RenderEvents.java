package com.mrcrayfish.furniture.client;

import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.blocks.BlockModernSlidingDoor;
import com.mrcrayfish.furniture.blocks.IWireFrame;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityModernSlidingDoor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class RenderEvents
{
	@SubscribeEvent
	public void onRenderOverlayEvent(DrawBlockHighlightEvent event)
	{
		RayTraceResult result = event.getTarget();
		float partialTicks = event.getPartialTicks();

		if (result.typeOfHit == RayTraceResult.Type.BLOCK)
		{
			EntityPlayer player = event.getPlayer();

			World world = player.world;
			BlockPos pos = result.getBlockPos();

			IBlockState state = world.getBlockState(pos);
			if (world.getWorldBorder().contains(pos))
			{
				if (state.getBlock() == FurnitureBlocks.MODERN_SLIDING_DOOR)
				{
					event.setCanceled(true);

					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					GlStateManager.glLineWidth(2.0F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);

					Vec3i vec = state.getValue(BlockModernSlidingDoor.FACING).rotateYCCW().getDirectionVec();
					double offset = 0;
					if (world.getTileEntity(pos) instanceof TileEntityModernSlidingDoor)
					{
						TileEntityModernSlidingDoor te = (TileEntityModernSlidingDoor) world.getTileEntity(pos);
						offset = te.getSlideProgress(partialTicks);
					}
					
					double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
					double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
					double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
					RenderGlobal.drawSelectionBoundingBox(state.getSelectedBoundingBox(world, pos).grow(0.0020000000949949026D).offset(-d3, -d4, -d5).offset(vec.getX() * offset, 0, vec.getZ() * offset), 0.0F, 0.0F, 0.0F, 0.4F);

					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.disableBlend();
				} else if(state.getBlock() instanceof IWireFrame) {
					event.setCanceled(true);

					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					GlStateManager.glLineWidth(2.0F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);
					
					IWireFrame wireframe = (IWireFrame) state.getBlock();
					List<AxisAlignedBB> boxes = Lists.newArrayList();
					wireframe.addWireframeBoxes(state.getBlock().getActualState(state, world, pos), world, pos, boxes);
					
					for(AxisAlignedBB box : boxes) {
						double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
						double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
						double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
						RenderGlobal.drawSelectionBoundingBox(box.offset(pos).grow(0.0020000000949949026D).offset(-d3, -d4, -d5), 0.0F, 0.0F, 0.0F, 0.4F);
					}

					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.disableBlend();
				}
			}
		}
	}
}