/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.render.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class CookieRenderer extends TileEntitySpecialRenderer
{
	private ItemStack cookie = new ItemStack(Items.cookie);
	private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, cookie);

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_)
	{	
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_LIGHTING);
		WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
		//renderer.setBrightness(15728880);
		
		this.entityItem.hoverStart = 0.0F;
		GL11.glTranslatef((float) posX + 0.5F, (float) posY + 0.05F, (float) posZ + 0.18F);
		GL11.glRotatef(180, 0, 1, 1);
		GL11.glScalef(0.9F, 0.9F, 0.9F);
		
		Block block = tileEntity.getBlockType();
		if(block.isAir(getWorld(), tileEntity.getPos()))
			return;

		int metadata = block.getMetaFromState(tileEntity.getWorld().getBlockState(tileEntity.getPos()));
		for (int i = 0; i < metadata; i++)
		{
			Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.1D * i, 0.0F, 0.0F);
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();
	}

}
