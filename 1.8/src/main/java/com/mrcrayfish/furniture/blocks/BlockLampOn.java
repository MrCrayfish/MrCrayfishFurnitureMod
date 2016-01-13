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
package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockLampOn extends BlockLamp
{
	public BlockLampOn(Material material)
	{
		super(material);
		this.setLightLevel(1.0F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack currentItem = player.getCurrentEquippedItem();
		if (currentItem != null)
		{
			if (currentItem.getItem() instanceof ItemDye)
			{
				world.setBlockState(pos, state.withProperty(COLOUR, 15 - currentItem.getItemDamage()));
				if (!player.capabilities.isCreativeMode)
					currentItem.stackSize--;
				return true;
			}
		}

		world.setBlockState(pos, FurnitureBlocks.lamp_off.getDefaultState().withProperty(COLOUR, (Integer) state.getValue(COLOUR)), 3);
		return true;
	}
}
