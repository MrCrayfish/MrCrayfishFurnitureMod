package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityGrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class GrillRenderer extends TileEntitySpecialRenderer
{
	private ItemStack coal = new ItemStack(Items.coal, 1, 1);
	private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, coal);
	
	private final float MAX_ANIM_TIME = 100F;
	private final float FLIP_HEIGHT = 0.5F;
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage)
	{
		TileEntityGrill grill = (TileEntityGrill) tileEntity;
		
		entityItem.hoverStart = 0;

		int rotation = grill.getBlockMetadata();
		
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0.5, 0.85, 0.5);
			GlStateManager.rotate(rotation * -90F, 0, 1, 0);
			GlStateManager.translate(0.18, 0, -0.35);
			GlStateManager.rotate(90F, 1, 0, 0);
			for(int i = 0; i < grill.getCoal(); i++)
			{
				GlStateManager.pushMatrix();
				{
					GlStateManager.rotate(15F, 0, 1, 0);
					entityItem.setEntityItemStack(coal);
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				}
				GlStateManager.popMatrix();
				GlStateManager.translate(-0.2, 0, 0);
			}
		}
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0.5, 1, 0.5);
			GlStateManager.rotate(rotation * -90F, 0, 1, 0);
			GlStateManager.translate(0.2, 0, -0.32);
			GlStateManager.rotate(90F, 1, 0, 0);
			
			/* Left */
			if(grill.getItem(0) != null)
			{
				GlStateManager.pushMatrix();
				{
					if(grill.flippedLeft)
					{
						float percent = (grill.leftFlipCount + partialTicks) / (float) grill.FLIP_DURATION;
						
						if(grill.leftFlipCount < grill.FLIP_DURATION / 2)
						{
							grill.leftCurrentHeight = (FLIP_HEIGHT / (grill.FLIP_DURATION / 2)) * (grill.leftFlipCount + partialTicks);
						}
						else if(grill.leftCurrentHeight > 0F)
						{
							grill.leftCurrentHeight = (FLIP_HEIGHT * 2) - (FLIP_HEIGHT / (grill.FLIP_DURATION / 2)) * (grill.leftFlipCount + partialTicks);
						}
						
						if(grill.leftCurrentHeight >= 0F)
						{
							GlStateManager.translate(0, 0, -Math.sqrt(grill.leftCurrentHeight));
						}
						
						if(grill.leftFlipCount < grill.FLIP_DURATION)
						{
							GlStateManager.rotate(180F * percent, 0, 1, 0);
						}
						else
						{
							GlStateManager.rotate(180F, 0, 1, 0);
							grill.leftCurrentHeight = 0F;
						}
					}

					entityItem.setEntityItemStack(grill.getItem(0));
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				}
				GlStateManager.popMatrix();
			}
			else
			{
				grill.leftFlipCount = 0;
			}
			
			GlStateManager.translate(-0.4, 0, 0);
			
			/* Right */
			if(grill.getItem(1) != null)
			{
				GlStateManager.pushMatrix();
				{
					if(grill.flippedRight)
					{
						float percent = (grill.rightFlipCount + partialTicks) / (float) grill.FLIP_DURATION;
						
						if(grill.rightFlipCount < grill.FLIP_DURATION / 2)
						{
							grill.rightCurrentHeight = (FLIP_HEIGHT / (grill.FLIP_DURATION / 2)) * (grill.rightFlipCount + partialTicks);
						}
						else if(grill.rightCurrentHeight > 0F)
						{
							grill.rightCurrentHeight = (FLIP_HEIGHT * 2) - (FLIP_HEIGHT / (grill.FLIP_DURATION / 2)) * (grill.rightFlipCount + partialTicks);
						}
						
						if(grill.rightCurrentHeight >= 0F)
						{
							GlStateManager.translate(0, 0, -Math.sqrt(grill.rightCurrentHeight));
						}
						
						if(grill.rightFlipCount < grill.FLIP_DURATION)
						{
							GlStateManager.rotate(180F * percent, 0, 1, 0);
						}
						else
						{
							GlStateManager.rotate(180F, 0, 1, 0);
							grill.rightCurrentHeight = 0F;
						}
					}
					
					entityItem.setEntityItemStack(grill.getItem(1));
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				}
				GlStateManager.popMatrix();
			}
			else
			{
				grill.rightFlipCount = 0;
			}
		}
		GlStateManager.popMatrix();
	}
}
