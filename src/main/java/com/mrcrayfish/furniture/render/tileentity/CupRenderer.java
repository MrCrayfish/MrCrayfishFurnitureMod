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

import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class CupRenderer extends TileEntitySpecialRenderer<TileEntityCup>
{

	@Override
	public void renderTileEntityAt(TileEntityCup tileEntityCup, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_)
	{
		if (tileEntityCup.getDrink() != null)
		{
			GlStateManager.pushMatrix();
			{
				GL11.glTranslatef((float) posX + 0.5F, (float) posY, (float) posZ + 0.5F);

				GlStateManager.enableBlend();
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GlStateManager.disableLighting();
				GlStateManager.disableTexture2D();
				GlStateManager.color(tileEntityCup.red / 255F, tileEntityCup.green / 255F, tileEntityCup.blue / 255F, 1.0F);
				GlStateManager.enableRescaleNormal();

				renderCuboid(-0.124F, 0.5F * 0.0625F, -0.124F, 0.124F, 0.4F, 0.124F);

				GlStateManager.disableRescaleNormal();
				GlStateManager.disableBlend();
				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
			}
			GlStateManager.popMatrix();
		}
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
