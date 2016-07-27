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
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShower extends BlockFurniture
{
	public BlockShower(Material material, boolean top)
	{
		super(material);
		this.setHardness(1.0F);
		this.setStepSound(SoundType.STONE);
		if(top) this.setCreativeTab(null);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos.up());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos.up(), FurnitureBlocks.shower_top.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
		((EntityPlayer) placer).addStat(FurnitureAchievements.bathroom);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (this == FurnitureBlocks.shower_bottom)
		{
			worldIn.destroyBlock(pos.up(), false);
		}
		else
		{
			worldIn.destroyBlock(pos.down(), false);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		IBlockState aboveState = worldIn.getBlockState(pos.up());
		Block block = aboveState.getBlock();
		int metadata = getMetaFromState(state);
		if (block == FurnitureBlocks.shower_head_off)
		{
			worldIn.setBlockState(pos.up(), FurnitureBlocks.shower_head_on.getDefaultState().withProperty(FACING, aboveState.getValue(FACING)), 2);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.block_lever_click, SoundCategory.BLOCKS, 0.3F, 0.6F, false);
		}
		else if (block == FurnitureBlocks.shower_head_on)
		{
			worldIn.setBlockState(pos.up(), FurnitureBlocks.shower_head_off.getDefaultState().withProperty(FACING, aboveState.getValue(FACING)), 2);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.block_lever_click, SoundCategory.BLOCKS, 0.3F, 0.5F, false);
		}
		return true;
	}

	//TODO FINISH
	/*@Override
	@SideOnly(Side.CLIENT)
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		int camera = Minecraft.getMinecraft().gameSettings.thirdPersonView;
		int direction = MathHelper.floor_double(((Minecraft.getMinecraft().thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		int metadata = getMetaFromState(blockAccess.getBlockState(pos));

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		switch (metadata)
		{
		case 1:
			metadata = 3;
			break;
		case 3:
			metadata = 1;
			break;
		case 2:
			metadata = 0;
			break;
		case 0:
			metadata = 2;
			break;
		}

		if (camera == 1 | camera == 2)
		{
			float[] data = CollisionHelper.fixRotation(direction, 0.9F, 0.0F, 1.0F, 1.0F);
			if (direction != metadata)
			{
				this.setBlockBounds(data[0], 0.0F, data[1], data[2], 1.0F, data[3]);
			}
			else
			{
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			}
		}
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);

		if (metadata == 3 || metadata == 2 || metadata == 0)
		{
			setBlockBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
		if (metadata == 3 || metadata == 2 || metadata == 1)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (metadata == 3 || metadata == 1 || metadata == 0)
		{
			setBlockBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
		if (metadata == 2 || metadata == 1 || metadata == 0)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
	}*/

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(FurnitureBlocks.shower_bottom).getItem();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(FurnitureBlocks.shower_bottom);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
