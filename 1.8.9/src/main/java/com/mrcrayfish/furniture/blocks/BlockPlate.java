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

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlate extends Block implements ITileEntityProvider
{
	public BlockPlate(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeStone);
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack currentItem = player.getCurrentEquippedItem();
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntityPlate)
		{
			TileEntityPlate tileEntityPlate = (TileEntityPlate) tileEntity;
			if (currentItem != null && tileEntityPlate.getFood() == null)
			{
				if (currentItem.getItem() instanceof ItemFood)
				{
					tileEntityPlate.setFood(new ItemStack(currentItem.getItem(), 1, currentItem.getItemDamage()));
					tileEntityPlate.setRotation(MathHelper.floor_double(((player.rotationYaw * 4F) / 360F) + 0.5D) & 3);
					world.markBlockForUpdate(pos);
					currentItem.stackSize--;
					return true;
				}
			}
			if (tileEntityPlate.getFood() != null)
			{
				if (!world.isRemote)
				{
					EntityItem entityFood = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityPlate.getFood());
					world.spawnEntityInWorld(entityFood);
				}
				tileEntityPlate.setFood(null);
				world.markBlockForUpdate(pos);
			}
		}
		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(3F * 0.0625F, 0.0F, 3F * 0.0625F, 13F * 0.0625F, 0.125F, 13F * 0.0625F);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(3F * 0.0625F, 0.0F, 3F * 0.0625F, 13F * 0.0625F, 0.125F, 13F * 0.0625F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPlate();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemPlate;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemPlate);
	}
}
