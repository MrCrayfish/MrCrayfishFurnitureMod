package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockEski;
import com.mrcrayfish.furniture.tileentity.TileEntityEski;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class EskiRenderer extends TileEntitySpecialRenderer<TileEntityEski>
{
	private EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld);
	
	@Override
	public void renderTileEntityAt(TileEntityEski eski, double x, double y, double z, float partialTicks, int destroyStage)
	{
		item.hoverStart = 0;
		
		IBlockState state = getWorld().getBlockState(eski.getPos());
		if(state.getBlock() instanceof BlockEski)
		{
			int rotation = state.getValue(BlockEski.FACING).getHorizontalIndex();
			if(state.getValue(BlockEski.OPENED))
			{
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					GlStateManager.translate(0.5, 0, 0.5);
					GlStateManager.rotate(-90F * rotation, 0, 1, 0);
					GlStateManager.translate(0.22, 0, 0.15);
					for(int i = 0; i < eski.inventory.length; i++)
					{
						if(eski.inventory[i] != null)
						{
							item.setEntityItemStack(eski.inventory[i]);
							GlStateManager.pushMatrix();
							{
								GlStateManager.translate(-0.15 * (i % 4), 0, 0);
								GlStateManager.translate(0, 0, Math.floor(i / 4) * -0.26);
								GlStateManager.rotate(45F, 0, 1, 0);
								Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(item, 0, 0, 0, 0, 0);
							}
							GlStateManager.popMatrix();
						}
					}
				}
				GlStateManager.popMatrix();
			}
		}
	}
}
