/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
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
package com.mrcrayfish.furniture.util;

import net.minecraft.block.Block;

public class CollisionHelper
{

	public static void setBlockBounds(Block block, int blockMetadata, float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float[] bounds = fixRotation(blockMetadata, x1, z1, x2, z2);
		block.setBlockBounds(bounds[0], y1, bounds[1], bounds[2], y2, bounds[3]);
	}

	public static float[] fixRotation(int metadata, float var1, float var2, float var3, float var4)
	{
		switch (metadata)
		{
		case 1:
			float var_temp_1 = var1;
			var1 = 1.0F - var3;
			float var_temp_2 = var2;
			var2 = 1.0F - var4;
			var3 = 1.0F - var_temp_1;
			var4 = 1.0F - var_temp_2;
			break;
		case 2:
			float var_temp_3 = var1;
			var1 = var2;
			var2 = 1.0F - var3;
			var3 = var4;
			var4 = 1.0F - var_temp_3;
			break;
		case 0:
			float var_temp_4 = var1;
			var1 = 1.0F - var4;
			float var_temp_5 = var2;
			var2 = var_temp_4;
			float var_temp_6 = var3;
			var3 = 1.0F - var_temp_5;
			var4 = var_temp_6;
			break;
		}
		return new float[] { var1, var2, var3, var4 };
	}
}
