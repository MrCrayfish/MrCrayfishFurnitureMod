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

import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityChoppingBoard;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
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

public class BlockChoppingBoard extends BlockFurnitureTile
{
	public BlockChoppingBoard(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeWood);
	} 

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack currentItem = player.getCurrentEquippedItem();
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntityChoppingBoard)
		{
			TileEntityChoppingBoard tileEntityChoppingBoard = (TileEntityChoppingBoard) tileEntity;
			if (currentItem != null)
			{
				if (Recipes.getChoppingBoardRecipeFromInput(currentItem) != null && tileEntityChoppingBoard.getFood() == null)
				{
					if(tileEntityChoppingBoard.getFood() == null)
					{
						tileEntityChoppingBoard.setFood(new ItemStack(currentItem.getItem(), 1));
						world.markBlockForUpdate(pos);
						world.updateComparatorOutputLevel(pos, this);
						currentItem.stackSize--;
						return true;
					}
					else
					{
						if (!world.isRemote)
						{
							EntityItem entityFood = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityChoppingBoard.getFood());
							world.spawnEntityInWorld(entityFood);
							tileEntityChoppingBoard.setFood(null);
							world.markBlockForUpdate(pos);
						}
						world.updateComparatorOutputLevel(pos, this);
						return true;
					}
				}
				else if (currentItem.getItem() == FurnitureItems.itemKnife && tileEntityChoppingBoard.getFood() != null)
				{
					if (tileEntityChoppingBoard.chopFood())
					{
						currentItem.damageItem(1, player);
					}
					return true;
				}
			}
			if (tileEntityChoppingBoard.getFood() != null)
			{
				if (!world.isRemote)
				{
					EntityItem entityFood = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityChoppingBoard.getFood());
					world.spawnEntityInWorld(entityFood);
				}
				tileEntityChoppingBoard.setFood(null);
				world.markBlockForUpdate(pos);
				world.updateComparatorOutputLevel(pos, this);
			}
		}
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		int metadata = getMetaFromState(blockAccess.getBlockState(pos));
		float[] data = CollisionHelper.fixRotation(metadata, 3F * 0.0625F, 0F * 0.0625F, 14F * 0.0625F, 16F * 0.0625F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 1.5F * 0.0625F, data[3]);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		if (!(collidingEntity instanceof EntitySittableBlock))
		{
			int metadata = getMetaFromState(state);
			float[] data = CollisionHelper.fixRotation(metadata, 4F * 0.0625F, 1F * 0.0625F, 13F * 0.0625F, 15F * 0.0625F);
			setBlockBounds(data[0], 0.0F, data[1], data[2], 0.0625F, data[3]);
		}
		else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		}
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityChoppingBoard();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemChoppingBoard;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemChoppingBoard);
	}

	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		TileEntityChoppingBoard tecb = (TileEntityChoppingBoard) world.getTileEntity(pos);
		return tecb.getFood() != null ? 1 : 0;
	}
}
