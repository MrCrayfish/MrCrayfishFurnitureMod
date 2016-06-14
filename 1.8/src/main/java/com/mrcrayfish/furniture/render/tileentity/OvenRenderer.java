package com.mrcrayfish.furniture.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.furniture.tileentity.TileEntityOven;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class OvenRenderer extends TileEntitySpecialRenderer
{
	private EntityItem ovenItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D);
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_)
	{
		TileEntityOven oven = (TileEntityOven) tileEntity;
		ovenItem.hoverStart = 0.0F;
		
		GlStateManager.pushMatrix();
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			GlStateManager.translate(posX + 0.3, posY + 0.52, posZ + 0.5);
			GlStateManager.scale(0.66, 0.66, 0.66);

			for (int i = 0; i < oven.getSizeInventory(); i++)
			{
				double height = (i / 4) * -0.25D;
				double xOffset = (i % 2 != 0) ? 0.5 : 0;
				double zOffset = i >= (i / 4) + 2 && i <= (i / 4) + 4 ? 0.5 : 0;
				ItemStack food = oven.getStackInSlot(i);
				if (food != null)
				{
					ovenItem.setEntityItemStack(food);
					GlStateManager.pushMatrix();
					{
						GlStateManager.translate(xOffset, height, zOffset);
						GlStateManager.rotate(-90F, 1, 0, 0);
						Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(ovenItem, 0, 0, 0, 0.0F, 0.0F);
					}
					GlStateManager.popMatrix();
				}
			}
		}
		GlStateManager.popMatrix();
	}
}
