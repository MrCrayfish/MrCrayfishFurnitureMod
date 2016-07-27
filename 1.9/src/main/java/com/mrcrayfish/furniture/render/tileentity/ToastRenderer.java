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

import com.mrcrayfish.furniture.tileentity.TileEntityToaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class ToastRenderer extends TileEntitySpecialRenderer<TileEntityToaster>
{
	private EntityItem[] slots = { new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D), new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D) };

	@Override
	public void renderTileEntityAt(TileEntityToaster tileEntityToaster, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_)
	{
		int metadata = tileEntityToaster.getBlockMetadata();

		for (int i = 0; i < 2; i++)
		{
			ItemStack slice = tileEntityToaster.getSlice(i);
			if (slice != null)
			{
				this.slots[i].setEntityItemStack(slice);
				GL11.glPushMatrix();
				slots[i].hoverStart = 0.0F;
				GL11.glTranslatef((float) posX + 0.5F, (float) posY + 0.05F, (float) posZ + 0.3F);

				double xOffset = 0.0D;
				double zOffset = 0.0D;

				switch (metadata)
				{
				case 0:
					if (i == 1)
					{
						zOffset += 0.27F;
					}
					else
					{
						zOffset += 0.14F;
					}
					break;
				case 1:
					xOffset += 0.2F;
					if (i == 1)
					{
						zOffset += 0.07F;
					}
					else
					{
						zOffset -= 0.06F;
					}
					GL11.glRotatef(270, 0, 1, 0);
					break;
				case 2:
					if (i == 1)
					{
						zOffset -= 0.13F;
					}
					else
					{
						zOffset -= 0.26F;
					}
					GL11.glRotatef(180, 0, 1, 0);
					break;
				case 3:
					xOffset -= 0.2F;
					if (i == 1)
					{
						zOffset += 0.07F;
					}
					else
					{
						zOffset -= 0.06F;
					}
					GL11.glRotatef(90, 0, 1, 0);
					break;
				}

				double yOffset = tileEntityToaster.isToasting() ? -0.075 : 0.0;
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(slots[i], 0.0D + xOffset, yOffset, 0.0D + zOffset, 0.0F, 0.0F, false);
				GL11.glPopMatrix();
			}
		}
	}
}
