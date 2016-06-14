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

import com.mrcrayfish.furniture.tileentity.TileEntityBlender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BlenderRenderer extends TileEntitySpecialRenderer
{
	private EntityItem entityFood = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D);

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_)
	{
		TileEntityBlender blender = (TileEntityBlender) tileEntity;
		ItemStack[] ingredients = blender.getIngredients();

		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float) posX + 0.5F, (float) posY + 0.2F, (float) posZ + 0.5F);
			GL11.glScalef(0.65F, 0.65F, 0.65F);
			entityFood.hoverStart = 0.0F;
			for (int i = 0; i < ingredients.length; i++)
			{
				if (ingredients[i] != null)
				{
					entityFood.setEntityItemStack(ingredients[i]);
					GL11.glRotatef(i * -90F, 0, 1, 0);
					GL11.glRotatef(blender.progress * 18F, 0, 1, 0);
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityFood, 0.0D, 0.2D, 0.0D, 0.0F, 0.0F);
				}
			}
		}
		GL11.glPopMatrix();

		if (blender.isBlending() | blender.drinkCount > 0)
		{
			GlStateManager.pushMatrix();
			{
				GL11.glTranslatef((float) posX + 0.5F, (float) posY + 0.05F, (float) posZ + 0.5F);
	
				float alpha = blender.isBlending() ? (blender.progress / 200F) : (blender.drinkCount > 0 ? 1.0F : 0.0F);
				GlStateManager.color(blender.currentRed / 255F, blender.currentGreen / 255F, blender.currentBlue / 255F, alpha);
	
				float height = blender.isBlending() ? 0.8F : (0.275F + (0.525F * (blender.drinkCount / 6F)));
				
				GlStateManager.enableBlend();
				GlStateManager.enableAlpha();
				GlStateManager.disableLighting();
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				
				renderCuboid(-0.2F, 0.275F, -0.2F, 0.2F, height, 0.2F);
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GlStateManager.enableLighting();
				GlStateManager.disableAlpha();
				GlStateManager.disableBlend();
			}
			GlStateManager.popMatrix();
		}
		
		GlStateManager.color(1F, 1F, 1F);
	}
	
	public void renderCuboid(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex3f(x1, y1, z1);
			GL11.glVertex3f(x1, y1, z2);
			GL11.glVertex3f(x1, y2, z2);
			GL11.glVertex3f(x1, y2, z1);

			GL11.glVertex3f(x2, y1, z1);
			GL11.glVertex3f(x1, y1, z1);
			GL11.glVertex3f(x1, y2, z1);
			GL11.glVertex3f(x2, y2, z1);

			GL11.glVertex3f(x1, y1, z2);
			GL11.glVertex3f(x2, y1, z2);
			GL11.glVertex3f(x2, y2, z2);
			GL11.glVertex3f(x1, y2, z2);

			GL11.glVertex3f(x2, y1, z2);
			GL11.glVertex3f(x2, y1, z1);
			GL11.glVertex3f(x2, y2, z1);
			GL11.glVertex3f(x2, y2, z2);

			GL11.glVertex3f(x1, y2, z1);
			GL11.glVertex3f(x1, y2, z2);
			GL11.glVertex3f(x2, y2, z2);
			GL11.glVertex3f(x2, y2, z1);

			GL11.glVertex3f(x1, y1, z1);
			GL11.glVertex3f(x1, y1, z2);
			GL11.glVertex3f(x2, y1, z2);
			GL11.glVertex3f(x2, y1, z1);
		}
		GL11.glEnd();
	}
}
