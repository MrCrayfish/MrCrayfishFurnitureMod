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
package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.util.CollisionHelper;

public class BlockTap extends BlockFurniture
{
	public BlockTap(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.gardening);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			EnumFacing facing = (EnumFacing) state.getValue(FACING);
			if (hasWaterSource(world, pos))
			{
				if(world.isAirBlock(pos.offset(facing.getOpposite())))
				{
					world.setBlockState(pos.offset(facing.getOpposite()), Blocks.water.getDefaultState());
					world.setBlockToAir(pos.down(2));
					player.triggerAchievement(FurnitureAchievements.tapped);
				}
				else
				{
					player.addChatComponentMessage(new ChatComponentText("Something is blocking the tap."));
				}
			}
			else
			{
				player.addChatComponentMessage(new ChatComponentText("You need to have a water source under the block the tap is on to use it."));
			}
		}
		return true;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);
		float[] data = CollisionHelper.fixRotation(metadata, 0.125F, 0.4F, 0.5625F, 0.6F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 1.0F, data[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		int metadata = getMetaFromState(blockAccess.getBlockState(pos));
		float[] data = CollisionHelper.fixRotation(metadata, 0.125F, 0.4F, 0.5625F, 0.6F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 1.0F, data[3]);
	}

	public boolean hasWaterSource(World world, BlockPos pos)
	{
		if (world.getBlockState(pos.down(2)).getBlock() == Blocks.water)
		{
			if (((Integer) world.getBlockState(pos.down(2)).getValue(Blocks.water.LEVEL)).intValue() == 0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemTap;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemTap);
	}
}
