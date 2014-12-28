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
package com.mrcrayfish.furniture.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;

public class ItemFridge extends Item
{
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		int rotate = MathHelper.floor_double(((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;

		if (!player.isSneaking())
		{
			if (world.isAirBlock(pos.up(1)) && world.isAirBlock(pos.up(2)))
			{
				world.setBlockState(pos.up(1), FurnitureBlocks.freezer.onBlockPlaced(world, pos.up(1), side, hitX, hitY, hitZ, 0, player));
				world.setBlockState(pos.up(2), FurnitureBlocks.fridge.onBlockPlaced(world, pos.up(2), side, hitX, hitY, hitZ, 0, player));
				stack.stackSize--;
			}
		}
		else
		{
			System.out.println("ID: " + world.getBlockState(pos));
			System.out.println("X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ());
		}
		return true;

	}
}
