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

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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

public class BlockToaster extends BlockFurnitureTile
{
	public BlockToaster(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeAnvil);
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		TileEntityToaster tileEntityToaster = (TileEntityToaster) world.getTileEntity(pos);
		if(!tileEntityToaster.isToasting() && world.isBlockPowered(pos))
		{
			tileEntityToaster.startToasting();
			world.markBlockForUpdate(pos);
			if (!world.isRemote)
			{
				world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "cfm:toaster_down", 0.75F, 1.0F);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack currentItem = player.getCurrentEquippedItem();
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntityToaster)
		{
			TileEntityToaster tileEntityToaster = (TileEntityToaster) tileEntity;
			if (currentItem != null && !tileEntityToaster.isToasting())
			{
				RecipeData data = RecipeAPI.getToasterRecipeFromInput(currentItem);
				if (data != null)
				{
					if(tileEntityToaster.addSlice(new ItemStack(currentItem.getItem(), 1)))
					{
						world.markBlockForUpdate(pos);
						currentItem.stackSize--;
					}
				}
				else
				{
					tileEntityToaster.removeSlice();
				}
			}
			else
			{
				if (player.isSneaking())
				{
					if (!tileEntityToaster.isToasting())
					{
						tileEntityToaster.startToasting();
						world.updateComparatorOutputLevel(pos, this);
						if (!world.isRemote)
						{
							world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "cfm:toaster_down", 0.75F, 1.0F);
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
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		int metadata = getMetaFromState(blockAccess.getBlockState(pos));
		float[] data = CollisionHelper.fixRotation(metadata, 5F * 0.0625F, 3F * 0.0625F, 11F * 0.0625F, 13F * 0.0625F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 0.45F, data[3]);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);
		float[] data = CollisionHelper.fixRotation(metadata, 5F * 0.0625F, 3F * 0.0625F, 11F * 0.0625F, 13F * 0.0625F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 0.4F, data[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityToaster();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemToaster;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemToaster);
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		TileEntityToaster toaster = (TileEntityToaster) world.getTileEntity(pos);
		return toaster.isToasting() ? 1 : 0;
	}
}
