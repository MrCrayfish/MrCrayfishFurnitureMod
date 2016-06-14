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

import com.mrcrayfish.furniture.tileentity.TileEntityCup;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class CupRenderer extends TileEntitySpecialRenderer
{

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_)
	{
		TileEntityCup cup = (TileEntityCup) tileEntity;
		
		if (cup.getDrink() != null)
		{
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float) posX + 0.5F, (float) posY, (float) posZ + 0.5F);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glEnable(GL11.GL_BLEND);
	
				GL11.glColor4f(cup.red / 255F, cup.green / 255F, cup.blue / 255F, 1.0F);
	
				GL11.glBegin(GL11.GL_QUADS);
	
				// North Face
				GL11.glVertex3d(-0.125, 0.5 * 0.0625, -0.125);
				GL11.glVertex3d(0.125, 0.5 * 0.0625, -0.125);
				GL11.glVertex3d(0.125, 0.4, -0.125);
				GL11.glVertex3d(-0.125, 0.4, -0.125);
	
				// South Face
				GL11.glVertex3d(-0.125, 0.5 * 0.0625, 0.125);
				GL11.glVertex3d(0.125, 0.5 * 0.0625, 0.125);
				GL11.glVertex3d(0.125, 0.4, 0.125);
				GL11.glVertex3d(-0.125, 0.4, 0.125);
	
				// West Face
				GL11.glVertex3d(-0.125, 0.5 * 0.0625, -0.125);
				GL11.glVertex3d(-0.125, 0.5 * 0.0625, 0.125);
				GL11.glVertex3d(-0.125, 0.4, 0.125);
				GL11.glVertex3d(-0.125, 0.4, -0.125);
	
				// East Face
				GL11.glVertex3d(0.125, 0.5 * 0.0625, -0.125);
				GL11.glVertex3d(0.125, 0.5 * 0.0625, 0.125);
				GL11.glVertex3d(0.125, 0.4, 0.125);
				GL11.glVertex3d(0.125, 0.4, -0.125);
	
				// Top Face
				GL11.glVertex3d(-0.125, 0.4, -0.125);
				GL11.glVertex3d(0.125, 0.4, -0.125);
				GL11.glVertex3d(0.125, 0.4, 0.125);
				GL11.glVertex3d(-0.125, 0.4, 0.125);
	
				// Bottom Face
				GL11.glVertex3d(-0.125, 0.5 * 0.0625, -0.125);
				GL11.glVertex3d(0.125, 0.5 * 0.0625, -0.125);
				GL11.glVertex3d(0.125, 0.5 * 0.0625, 0.125);
				GL11.glVertex3d(-0.125, 0.5 * 0.0625, 0.125);
	
				GL11.glEnd();
	
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
			GL11.glPopMatrix();
		}
	}
}
