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

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil
{
	private static final Random RANDOM = new Random();

	/**
	 * Drops the items that are in a tile entity's inventory. Does nothing if the tile entity is not an instance of an {@link ISimpleInventory} or {@link IInventory}.
	 * 
	 * @param world
	 *            The world to drop the items in
	 * @param pos
	 *            The position to drop the items
	 * @param te
	 *            The tile entity to get the items from
	 */
	public static void dropTileEntityInventoryItems(World world, BlockPos pos, TileEntity te)
	{
		if (te instanceof ISimpleInventory) {
			dropInventoryItems(world, pos, (ISimpleInventory) te);
		}
		if(te instanceof IInventory) {
			InventoryHelper.dropInventoryItems(world, pos, (IInventory )te);
		}
	}

	/**
	 * Drops the items in an {@link ISimpleInventory}.
	 * 
	 * @param world
	 *            The world to drop the items in
	 * @param pos
	 *            The position to drop the items
	 * @param inv
	 *            The inventory to drop the items out of
	 */
	public static void dropInventoryItems(World world, BlockPos pos, ISimpleInventory inv)
	{
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack stack = inv.getItem(i);

			if (stack != null && !stack.isEmpty()) {
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
	}

	/**
	 * Calculates an IItemHandler's redstone capacity based on the amount of items inside.
	 * 
	 * @param inventory
	 *            The inventory to drop the items out of
	 * @return The redstone level from 0-15 based on how full the inventory is
	 */
	public static int calculateTileEntityRedstone(IItemHandler inventory)
	{
		if (inventory == null) {
			return 0;
		} else {
			int i = 0;
			float f = 0.0F;

			for (int j = 0; j < inventory.getSlots(); ++j) {
				ItemStack itemstack = inventory.getStackInSlot(j);

				if (!itemstack.isEmpty()) {
					f += (float) itemstack.getCount() / (float) Math.min(inventory.getSlotLimit(j), itemstack.getMaxStackSize());
					++i;
				}
			}

			f = f / (float) inventory.getSlots();
			return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
		}
	}
}
