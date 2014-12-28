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

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWhiteFence extends Block
{
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	
	public BlockWhiteFence(Material material)
	{
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeWood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
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
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.gardening);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;

		if (blockAccess.getBlockState(pos.east()).getBlock() == FurnitureBlocks.white_fence | blockAccess.getBlockState(pos.east()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.east()).getBlock().isNormalCube())
		{
			f1 = 1.0F;
		}
		if (blockAccess.getBlockState(pos.west()).getBlock() == FurnitureBlocks.white_fence | blockAccess.getBlockState(pos.west()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.west()).getBlock().isNormalCube())
		{
			f = 0.0F;
		}
		if (blockAccess.getBlockState(pos.south()).getBlock() == FurnitureBlocks.white_fence | blockAccess.getBlockState(pos.south()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.south()).getBlock().isNormalCube())
		{
			f3 = 1.0F;
		}
		if (blockAccess.getBlockState(pos.north()).getBlock() == FurnitureBlocks.white_fence | blockAccess.getBlockState(pos.north()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.north()).getBlock().isNormalCube())
		{
			f2 = 0.0F;
		}

		setBlockBounds(f, 0.0F, f2, f1, 1.1F, f3);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);

		if (world.getBlockState(pos.east()).getBlock() == FurnitureBlocks.white_fence | world.getBlockState(pos.east()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.4375F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.west()).getBlock() == FurnitureBlocks.white_fence | world.getBlockState(pos.west()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.south()).getBlock() == FurnitureBlocks.white_fence | world.getBlockState(pos.south()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.north()).getBlock() == FurnitureBlocks.white_fence | world.getBlockState(pos.north()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemWhiteFence;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemWhiteFence);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, Boolean.valueOf(isFence(world, pos.north()))).withProperty(EAST, Boolean.valueOf(isFence(world, pos.east()))).withProperty(SOUTH, Boolean.valueOf(isFence(world, pos.south()))).withProperty(WEST, Boolean.valueOf(isFence(world, pos.west())));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { NORTH, EAST, SOUTH, WEST });
	}

	public boolean isFence(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() == this | world.getBlockState(pos).getBlock().isNormalCube();
	}
}
