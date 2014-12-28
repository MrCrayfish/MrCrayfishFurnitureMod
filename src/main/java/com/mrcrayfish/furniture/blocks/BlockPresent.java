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

import java.util.Date;
import java.util.List;
import java.util.Random;

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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;

public class BlockPresent extends BlockFurnitureTile
{
	public BlockPresent(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeCloth);
	}

	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntityPresent mailBox = (TileEntityPresent) world.getTileEntity(pos);
		if (mailBox != null)
		{
			player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Merry Christmas" + EnumChatFormatting.RESET + " from " + EnumChatFormatting.RED + mailBox.ownerName));
			player.triggerAchievement(FurnitureAchievements.unwrapPresent);
		}
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.35F, 0.75F);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.35F, 0.75F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (this == FurnitureBlocks.present_red)
			return FurnitureItems.itemPresentRed;
		if (this == FurnitureBlocks.present_green)
			return FurnitureItems.itemPresentGreen;
		return null;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		if (this == FurnitureBlocks.present_red)
			return new ItemStack(FurnitureItems.itemPresentRed);
		if (this == FurnitureBlocks.present_green)
			return new ItemStack(FurnitureItems.itemPresentGreen);
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPresent();
	}

	public int getDaysInbetween(Date date, Date date2)
	{
		return (int) (date2.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
	}
}
