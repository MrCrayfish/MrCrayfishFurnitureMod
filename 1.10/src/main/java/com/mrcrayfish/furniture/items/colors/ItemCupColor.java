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
package com.mrcrayfish.furniture.items.colors;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;

@SideOnly(Side.CLIENT)
public class ItemCupColor implements IItemColor {

	public int getColorFromCompound(ItemStack cup)
	{
		if (cup.hasTagCompound())
		{
			if (cup.getTagCompound().hasKey("Colour"))
			{
				int[] colour = cup.getTagCompound().getIntArray("Colour");
				Color color = new Color(colour[0], colour[1], colour[2]);
				return color.getRGB();
			}
		}
		return 16777215;
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) 
	{
		return this.getColorFromCompound(stack);
	}

}
