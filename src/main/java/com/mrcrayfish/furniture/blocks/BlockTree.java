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

import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTree extends BlockFurniture
{
	public BlockTree(Material material)
	{
		super(material);
		setStepSound(Block.soundTypeWood);
		setLightLevel(0.3F);
		setHardness(0.5F);
	}

	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 4764952;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state)
	{
		return 4764952;
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
	{
		return 4764952;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (this == FurnitureBlocks.tree_bottom)
			world.setBlockState(pos.up(), FurnitureBlocks.tree_top.getDefaultState());
		return super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (this == FurnitureBlocks.tree_top)
		{
			if (player.getCurrentEquippedItem() != null)
			{
				if (player.getCurrentEquippedItem().getItem() != Items.shears)
				{
					worldIn.destroyBlock(pos.down(), true);
				}
				else
				{
					player.getCurrentEquippedItem().damageItem(1, player);
				}
			}
			else
			{
				worldIn.destroyBlock(pos.down(), true);
				// worldIn.setBlockToAir(pos.down());
			}
		}
		else
		{
			worldIn.destroyBlock(pos.up(), true);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		if (this == FurnitureBlocks.tree_bottom)
		{
			if (blockAccess.getBlockState(pos.up()).getBlock() == FurnitureBlocks.tree_top)
			{
				setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 2.0F, 0.9375F);
			}
			else
			{
				setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
			}
		}
		else
		{
			setBlockBounds(0.0625F, -1.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.placeTree);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (this == FurnitureBlocks.tree_top)
			return null;
		return FurnitureItems.itemTree;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemTree);
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}
}
