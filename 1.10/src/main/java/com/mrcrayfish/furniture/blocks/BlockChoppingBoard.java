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

import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityChoppingBoard;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChoppingBoard extends BlockFurnitureTile
{
	private static final AxisAlignedBB BOUNDING_BOX_ONE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 3 * 0.0625, 0.0, 0.0, 14 * 0.0625, 1.5 * 0.0625, 16 * 0.0625);
	private static final AxisAlignedBB BOUNDING_BOX_TWO = CollisionHelper.getBlockBounds(EnumFacing.EAST, 3 * 0.0625, 0.0, 0.0, 14 * 0.0625, 1.5 * 0.0625, 16 * 0.0625);
	
	private static final AxisAlignedBB COLLISION_BOX_ONE = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 4 * 0.0625, 0.0, 1 * 0.0625, 13 * 0.0625, 1 * 0.0625, 15 * 0.0625);
	private static final AxisAlignedBB COLLISION_BOX_TWO = CollisionHelper.getBlockBounds(EnumFacing.EAST, 4 * 0.0625, 0.0, 1 * 0.0625, 13 * 0.0625, 1 * 0.0625, 15 * 0.0625);
	
	public BlockChoppingBoard(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
	} 

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityChoppingBoard)
		{
			TileEntityChoppingBoard tileEntityChoppingBoard = (TileEntityChoppingBoard) tileEntity;
			if (heldItem != null)
			{
				if (Recipes.getChoppingBoardRecipeFromInput(heldItem) != null && tileEntityChoppingBoard.getFood() == null)
				{
					if(tileEntityChoppingBoard.getFood() == null)
					{
						tileEntityChoppingBoard.setFood(new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
						TileEntityUtil.markBlockForUpdate(worldIn, pos);
						worldIn.updateComparatorOutputLevel(pos, this);
						heldItem.stackSize--;
						return true;
					}
					else
					{
						if (!worldIn.isRemote)
						{
							EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityChoppingBoard.getFood());
							worldIn.spawnEntityInWorld(entityFood);
							tileEntityChoppingBoard.setFood(null);
							TileEntityUtil.markBlockForUpdate(worldIn, pos);
						}
						worldIn.updateComparatorOutputLevel(pos, this);
						return true;
					}
				}
				else if (heldItem.getItem() == FurnitureItems.itemKnife && tileEntityChoppingBoard.getFood() != null)
				{
					if (tileEntityChoppingBoard.chopFood())
					{
						heldItem.damageItem(1, playerIn);
					}
					return true;
				}
			}
			if (tileEntityChoppingBoard.getFood() != null)
			{
				if (!worldIn.isRemote)
				{
					EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityChoppingBoard.getFood());
					worldIn.spawnEntityInWorld(entityFood);
				}
				tileEntityChoppingBoard.setFood(null);
				TileEntityUtil.markBlockForUpdate(worldIn, pos);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}
		return true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		if(getRotationState(source, pos) == 1)
		{
			return BOUNDING_BOX_ONE;
		}
		return BOUNDING_BOX_TWO;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB axisAligned, List<AxisAlignedBB> axisAlignedList, Entity collidingEntity) 
	{
		if(getRotationState(worldIn, pos) == 1)
		{
			super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, COLLISION_BOX_ONE);
			return;
		}
		super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, COLLISION_BOX_TWO);
	}
	
	public int getRotationState(IBlockAccess source, BlockPos pos) 
	{
		if (source.isAirBlock(pos))
		{
			return 0;
		}

		return getMetaFromState(source.getBlockState(pos)) % 2;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityChoppingBoard();
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		TileEntityChoppingBoard tecb = (TileEntityChoppingBoard) world.getTileEntity(pos);
		return tecb.getFood() != null ? 1 : 0;
	}
}
