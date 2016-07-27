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
package com.mrcrayfish.furniture.util;

import java.util.Random;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InventoryUtil
{
	private static final Random RANDOM = new Random();

	public static void dropInventoryItems(World world, BlockPos pos, ISimpleInventory inv)
	{
		for (int i = 0; i < inv.getSize(); i++)
		{
			ItemStack stack = inv.getItem(i);

			if (stack != null)
			{
				spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
	}

	private static void spawnItemStack(World world, double posX, double posY, double posZ, ItemStack stack)
	{
		float f = RANDOM.nextFloat() * 0.8F + 0.1F;
		float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
		float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

		while (stack.stackSize > 0)
		{
			int i = RANDOM.nextInt(21) + 10;

			if (i > stack.stackSize)
			{
				i = stack.stackSize;
			}

			stack.stackSize -= i;
			EntityItem entityitem = new EntityItem(world, posX + (double) f, posY + (double) f1, posZ + (double) f2, new ItemStack(stack.getItem(), i, stack.getMetadata()));

			if (stack.hasTagCompound())
			{
				entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
			}

			float f3 = 0.05F;
			entityitem.motionX = RANDOM.nextGaussian() * (double) f3;
			entityitem.motionY = RANDOM.nextGaussian() * (double) f3 + 0.20000000298023224D;
			entityitem.motionZ = RANDOM.nextGaussian() * (double) f3;
			world.spawnEntityInWorld(entityitem);
		}
	}
}
