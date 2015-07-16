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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemPlacer extends Item
{
	private Block block;

	public ItemPlacer(Block block)
	{
		this.block = block;
		setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (block == Blocks.snow_layer && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1)
		{
			side = EnumFacing.UP;
		}
		else if (!block.isReplaceable(world, pos))
		{
			pos = pos.offset(side);
		}

		if (!player.canPlayerEdit(pos, side, stack))
		{
			return false;
		}
		else if (stack.stackSize == 0)
		{
			return false;
		}
		else
		{
			if (world.canBlockBePlaced(this.block, pos, false, side, (Entity) null, stack))
			{
				IBlockState iblockstate1 = this.block.onBlockPlaced(world, pos, side, hitX, hitY, hitZ, 0, player);

				if (world.setBlockState(pos, iblockstate1, 3))
				{
					iblockstate1 = world.getBlockState(pos);

					if (iblockstate1.getBlock() == this.block)
					{
						ItemBlock.setTileEntityNBT(world, pos, stack);
						iblockstate1.getBlock().onBlockPlacedBy(world, pos, iblockstate1, player, stack);
					}

					world.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
					--stack.stackSize;
					return true;
				}
			}

			return false;
		}
	}
}
