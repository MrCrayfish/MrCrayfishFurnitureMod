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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;
import com.mrcrayfish.furniture.tileentity.TileEntityStereo;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStereo extends BlockFurnitureTile
{
	public static ArrayList records = new ArrayList();

	public BlockStereo(Material material)
	{
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeWood);
		records.add(new ItemStack(Items.record_13));
		records.add(new ItemStack(Items.record_blocks));
		records.add(new ItemStack(Items.record_cat));
		records.add(new ItemStack(Items.record_chirp));
		records.add(new ItemStack(Items.record_far));
		records.add(new ItemStack(Items.record_mall));
		records.add(new ItemStack(Items.record_mellohi));
		records.add(new ItemStack(Items.record_stal));
		records.add(new ItemStack(Items.record_strad));
		records.add(new ItemStack(Items.record_wait));
		records.add(new ItemStack(Items.record_ward));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.modernTechnology);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tile_entity = world.getTileEntity(pos);
			if (tile_entity instanceof TileEntityStereo)
			{
				TileEntityStereo tileEntityStereo = (TileEntityStereo) tile_entity;
				if (!player.isSneaking())
				{
					if (tileEntityStereo.count == 13)
					{
						tileEntityStereo.count = records.size();
					}
					else
					{
						tileEntityStereo.count++;
					}
					if (tileEntityStereo.count == records.size())
					{
						tileEntityStereo.count = 0;
						tileEntityStereo.setRecord((ItemStack) this.records.get(0));
					}
					else
					{
						tileEntityStereo.setRecord((ItemStack) this.records.get(tileEntityStereo.count));
					}
					player.triggerAchievement(FurnitureAchievements.houseParty);

					ItemStack itemstack = (ItemStack) BlockStereo.records.get(tileEntityStereo.count);
					world.playAuxSFXAtEntity((EntityPlayer) null, 1005, pos.add(0.5, 0.5, 0.5), Item.getIdFromItem(itemstack.getItem()));
				}
				else
				{
					if (tileEntityStereo.count != 13)
					{
						tileEntityStereo.count = 13;
						player.addChatComponentMessage(new ChatComponentText("Stereo is now turned off."));
						world.playAuxSFX(1005, pos.add(0.5, 0.5, 0.5), 0);
						world.playRecord(pos.add(0.5, 0.5, 0.5), (String) null);
						tileEntityStereo.setRecord((ItemStack) null);
					}
					else
					{
						player.addChatComponentMessage(new ChatComponentText("Stereo is already turned off."));
					}
				}
			}
		}
		return true;

	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		this.ejectRecord(world, pos);
		super.breakBlock(world, pos, state);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
	{
		int metadata = getMetaFromState(access.getBlockState(pos));
		float[] data = CollisionHelper.fixRotation(metadata, 0.3F, 0.0F, 0.7F, 1.0F);
		setBlockBounds(data[0], 0.0F, data[1], data[2], 0.5F, data[3]);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int metadata = getMetaFromState(state);
		float[] data = CollisionHelper.fixRotation(metadata, 0.0F, 0.3F, 1.0F, 0.7F);
		setBlockBounds(data[0], 0.15F, data[1], data[2], 0.45F, data[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityStereo();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemStereo;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemStereo);
	}

	public void ejectRecord(World world, BlockPos pos)
	{
		if (!world.isRemote)
		{
			TileEntityStereo tileEntityStereo = (TileEntityStereo) world.getTileEntity(pos);

			if (tileEntityStereo != null)
			{
				world.playAuxSFX(1005, pos.add(0.5, 0.5, 0.5), 0);
				world.playRecord(pos.add(0.5, 0.5, 0.5), (String) null);
				tileEntityStereo.setRecord((ItemStack) null);
			}
		}
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		TileEntityStereo stereo = (TileEntityStereo) world.getTileEntity(pos);
		return stereo.count;
	}
}
