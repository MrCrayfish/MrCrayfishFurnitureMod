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

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockToaster extends BlockFurnitureTile
{
	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };
	
	private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
	private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
	private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
	private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
	private static final AxisAlignedBB[] COLLISION_BOX = { COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST };
	
	public BlockToaster(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.ANVIL);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityToaster)
		{
			TileEntityToaster tileEntityToaster = (TileEntityToaster) tileEntity;
			if (heldItem != null && !tileEntityToaster.isToasting())
			{
				RecipeData data = RecipeAPI.getToasterRecipeFromInput(heldItem);
				if (data != null)
				{
					if(tileEntityToaster.addSlice(new ItemStack(heldItem.getItem(), 1)))
					{
						TileEntityUtil.markBlockForUpdate(worldIn, pos);
						heldItem.stackSize--;
					}
				}
				else
				{
					tileEntityToaster.removeSlice();
				}
			}
			else
			{
				if (playerIn.isSneaking())
				{
					if (!tileEntityToaster.isToasting())
					{
						tileEntityToaster.startToasting();
						worldIn.updateComparatorOutputLevel(pos, this);
						if (!worldIn.isRemote)
						{
							worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.toaster_down, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
						}
					}
				}
				else if (!tileEntityToaster.isToasting())
				{
					tileEntityToaster.removeSlice();
				}
			}
		}
		return true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB axisAligned, List<AxisAlignedBB> axisAlignedList, Entity collidingEntity) 
	{
		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, COLLISION_BOX[facing.getHorizontalIndex()]);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityToaster();
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		TileEntityToaster toaster = (TileEntityToaster) world.getTileEntity(pos);
		return toaster.isToasting() ? 1 : 0;
	}
}
