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
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoorBell extends BlockFurniture
{
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };

	public BlockDoorBell(Material material)
	{
		super(material);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.valueOf(true)));
		this.setTickRandomly(true);
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState worldIn, World pos, BlockPos state) 
	{
		return NULL_AABB;
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 30;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		EnumFacing[] aenumfacing = EnumFacing.values();
		int i = aenumfacing.length;

		for (int j = 2; j < i; ++j)
		{
			EnumFacing enumfacing = aenumfacing[j];

			if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube(worldIn.getBlockState(pos)))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(POWERED, Boolean.valueOf(false));
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		if (this.func_176583_e(worldIn, pos, state))
		{
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (!worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube(state, worldIn, pos))
			{
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	private boolean func_176583_e(World worldIn, BlockPos p_176583_2_, IBlockState p_176583_3_)
	{
		if (!this.canPlaceBlockAt(worldIn, p_176583_2_))
		{
			this.dropBlockAsItem(worldIn, p_176583_2_, p_176583_3_, 0);
			worldIn.setBlockToAir(p_176583_2_);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (state.getValue(POWERED))
		{
			return true;
		}
		else
		{
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)), 3);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.door_bell, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			playerIn.addStat(FurnitureAchievements.dingDong);
			return true;
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			if (((Boolean) state.getValue(POWERED)).booleanValue())
			{
				this.handleArrow(worldIn, pos, state);
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (!worldIn.isRemote)
		{
			if (!((Boolean) state.getValue(POWERED)).booleanValue())
			{
				this.handleArrow(worldIn, pos, state);
			}
		}
	}

	private void handleArrow(World worldIn, BlockPos pos, IBlockState state)
	{
		List <? extends Entity > list = worldIn.<Entity>getEntitiesWithinAABB(EntityArrow.class, state.getBoundingBox(worldIn, pos).offset(pos));
		boolean flag = !list.isEmpty();
		boolean flag1 = state.getValue(POWERED);

		if (flag && !flag1)
		{
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)));
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.6F, false);
		}

		if (!flag && flag1)
		{
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F, false);
		}

		if (flag)
		{
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing;

		switch (meta)
		{
		case 0:
			enumfacing = EnumFacing.NORTH;
			break;
		case 1:
			enumfacing = EnumFacing.EAST;
			break;
		case 2:
			enumfacing = EnumFacing.SOUTH;
			break;
		case 3:
			enumfacing = EnumFacing.WEST;
			break;
		default:
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED, Boolean.valueOf(meta > 3));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = ((EnumFacing) state.getValue(FACING)).getIndex();
		if (((Boolean) state.getValue(POWERED)).booleanValue())
		{
			meta += 4;
		}
		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}
}
