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
import com.mrcrayfish.furniture.tileentity.TileEntityShowerHead;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockShowerHead extends BlockFurnitureTile
{
	public BlockShowerHead(Material material)
	{
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.bathroom);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (this.canPlaceCheck(world, pos, state))
		{
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (!world.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
			{
				this.dropBlockAsItem(world, pos, state, 0);
				world.setBlockToAir(pos);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (this == FurnitureBlocks.shower_head_off)
		{
			world.setBlockState(pos, FurnitureBlocks.shower_head_on.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
			world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
		}
		else
		{
			world.setBlockState(pos, FurnitureBlocks.shower_head_off.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
			world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
	{
		return (side == EnumFacing.UP | side == EnumFacing.DOWN) ? false : world.isSideSolid(pos.offset(side.getOpposite()), side, true);
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		return state.withProperty(FACING, facing.getOpposite());
	}


	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		EnumFacing[] aenumfacing = EnumFacing.values();
		int i = aenumfacing.length;

		for (int j = 2; j < i; ++j)
		{
			EnumFacing enumfacing = aenumfacing[j];

			if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
	{
		int metadata = getMetaFromState(access.getBlockState(pos));
		float[] data = CollisionHelper.fixRotation(metadata, 0.35F, 0.35F, 1.0F, 0.65F);
		setBlockBounds(data[0], 0.15F, data[1], data[2], 0.45F, data[3]);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);
		float[] data = CollisionHelper.fixRotation(metadata, 0.35F, 0.35F, 1.0F, 0.65F);
		setBlockBounds(data[0], 0.15F, data[1], data[2], 0.45F, data[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		if (this == FurnitureBlocks.shower_head_on)
		{
			return new TileEntityShowerHead();
		}
		return null;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemShowerHead;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemShowerHead);
	}

	private boolean canPlaceCheck(World world, BlockPos pos, IBlockState state)
	{
		if (!this.canPlaceBlockAt(world, pos))
		{
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
			return false;
		}
		else
		{
			return true;
		}
	}
}
