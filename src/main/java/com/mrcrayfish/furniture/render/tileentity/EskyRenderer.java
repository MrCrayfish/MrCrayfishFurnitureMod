package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockEsky;
import com.mrcrayfish.furniture.tileentity.TileEntityEsky;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class EskyRenderer extends TileEntitySpecialRenderer<TileEntityEsky>
{
	private EntityItem item = new EntityItem(Minecraft.getMinecraft().world);
	
	@Override
	public void render(TileEntityEsky eski, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		item.hoverStart = 0;
		
		IBlockState state = getWorld().getBlockState(eski.getPos());
		if(state.getBlock() instanceof BlockEsky)
		{
			int rotation = state.getValue(BlockEsky.FACING).getHorizontalIndex();
			if(state.getValue(BlockEsky.OPENED))
			{
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					GlStateManager.translate(0.5, 0, 0.5);
					GlStateManager.rotate(-90F * rotation, 0, 1, 0);
					GlStateManager.translate(0.22, 0, 0.15);
					for(int i = 0; i < eski.getSizeInventory(); i++)
					{
						if(!eski.getStackInSlot(i).isEmpty())
						{
							item.setItem(eski.getStackInSlot(i));
							GlStateManager.pushMatrix();
							{
								GlStateManager.translate(-0.15 * (i % 4), 0, 0);
								GlStateManager.translate(0, 0, Math.floor(i / 4) * -0.26);
								GlStateManager.rotate(45F, 0, 1, 0);
								Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, false);
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
