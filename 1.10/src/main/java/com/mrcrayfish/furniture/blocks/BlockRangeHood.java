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

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRangeHood extends BlockFurniture
{
	private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
	private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
	private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
	private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
	private static final AxisAlignedBB[] COLLISION_BOX = { COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST };
	
	public BlockRangeHood(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.METAL);
		this.setLightLevel(0.5F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).addStat(FurnitureAchievements.applianceCity);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB axisAligned, List<AxisAlignedBB> axisAlignedList, Entity collidingEntity) 
	{
		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, COLLISION_BOX[facing.getHorizontalIndex()]);
	}
}
