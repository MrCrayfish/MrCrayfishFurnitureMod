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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlate extends Block implements ITileEntityProvider
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(3 * 0.0625, 0.0, 3 * 0.0625, 13 * 0.0625, 0.125, 13 * 0.0625);
	
	public BlockPlate(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setStepSound(SoundType.STONE);
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityPlate)
		{
			TileEntityPlate tileEntityPlate = (TileEntityPlate) tileEntity;
			if (heldItem != null && tileEntityPlate.getFood() == null)
			{
				if (heldItem.getItem() instanceof ItemFood)
				{
					tileEntityPlate.setFood(new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
					tileEntityPlate.setRotation(playerIn.getHorizontalFacing().getHorizontalIndex());
					TileEntityUtil.markBlockForUpdate(worldIn, pos);
					heldItem.stackSize--;
					return true;
				}
			}
			if (tileEntityPlate.getFood() != null)
			{
				if (!worldIn.isRemote)
				{
					EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityPlate.getFood());
					worldIn.spawnEntityInWorld(entityFood);
				}
				tileEntityPlate.setFood(null);
				TileEntityUtil.markBlockForUpdate(worldIn, pos);
			}
		}
		return true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB axisAligned, List<AxisAlignedBB> axisAlignedList, Entity collidingEntity) 
	{
		super.addCollisionBoxToList(pos, axisAligned, axisAlignedList, BOUNDING_BOX);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPlate();
	}
}
