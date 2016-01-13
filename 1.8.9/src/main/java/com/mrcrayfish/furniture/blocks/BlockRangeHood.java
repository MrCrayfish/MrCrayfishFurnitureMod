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

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRangeHood extends BlockFurniture
{
	public BlockRangeHood(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeMetal);
		setLightLevel(0.5F);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.applianceCity);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);
		float[] data = CollisionHelper.fixRotation(metadata, 0.5F, 0.2F, 1.0F, 0.8F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 1.0F, data[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemOvenRangehood;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemOvenRangehood);
	}
}
