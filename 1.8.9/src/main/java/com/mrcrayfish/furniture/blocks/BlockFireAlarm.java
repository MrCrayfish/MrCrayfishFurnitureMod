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

import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFireAlarm extends BlockFurniture
{
	private boolean on = false;

	public BlockFireAlarm(Material material, boolean on)
	{
		super(material);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeStone);
		this.on = on;
		if (on)
		{
			this.setLightLevel(1.0F);
			this.setCreativeTab(null);
		}
		else
		{
			this.setLightLevel(0.2F);
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(5F * 0.0625F, 0.875F, 5F * 0.0625F, 11F * 0.0625F, 1.0F, 11F * 0.0625F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (on)
		{
			world.setBlockState(pos, FurnitureBlocks.fire_alarm_off.getDefaultState(), 2);
		}
		return true;
	}
	
	@Override
	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) 
	{
		return side == EnumFacing.DOWN && this == FurnitureBlocks.fire_alarm_on ? 15 : 0;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!on)
		{
			int radius = 9;
			scanner:
			{
				for (int x = 0; x < radius; x++)
				{
					for (int y = 0; y < radius; y++)
					{
						for (int z = 0; z < radius; z++)
						{
							if (world.getBlockState(pos.add(-4 + x, -4 + y, -4 + z)).getBlock() == Blocks.fire)
							{
								world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:firealarm", 5.0F, 1.0F);
								world.setBlockState(pos, FurnitureBlocks.fire_alarm_on.getDefaultState(), 2);
								break scanner;
							}
							if (x == 8 && y == 8 && z == 8)
							{
								world.scheduleUpdate(pos, this, this.tickRate(world));
								break scanner;
							}
						}
					}
				}
			}
		}
		if (on)
		{
			world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:firealarm", 5.0F, 1.0F);
			world.scheduleUpdate(pos, this, 34);
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if (on)
		{
			world.scheduleUpdate(pos, this, 34);
		}
		if (!on)
		{
			world.scheduleUpdate(pos, this, 1);
		}
		world.notifyBlockOfStateChange(pos, this);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!canBlockStay(world, pos))
		{
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.DOWN;
	}

	public boolean canBlockStay(World par1World, BlockPos pos)
	{
		return !par1World.isAirBlock(pos.up());
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemFireAlarm;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemFireAlarm);
	}
}
