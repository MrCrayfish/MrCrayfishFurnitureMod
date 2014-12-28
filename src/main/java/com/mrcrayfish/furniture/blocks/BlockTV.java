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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageTVServer;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;

public class BlockTV extends BlockFurnitureTile
{
	public BlockTV(Material material)
	{
		super(material);
		setStepSound(Block.soundTypeWood);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.modernTechnology);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = world.getTileEntity(pos);
		if (tile_entity instanceof TileEntityTV)
		{
			TileEntityTV tileEntityTV = (TileEntityTV) tile_entity;
			if (world.isRemote)
			{
				if (tileEntityTV.getChannel() == 3)
				{
					tileEntityTV.setChannel(0);
				}
				else
				{
					tileEntityTV.setChannel(tileEntityTV.getChannel() + 1);
				}
			}

			world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "cfm:static", 0.75F, 1.0F);
			world.markBlockForUpdate(pos);
			world.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), 20, 20, 20);

			if (tileEntityTV.getChannel() == 1)
			{
				player.triggerAchievement(FurnitureAchievements.heyeyey);
			}
			if (world.isRemote)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageTVServer(tileEntityTV.getChannel(), pos.getX(), pos.getY(), pos.getZ()));
			}
		}
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTV();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemTV;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemTV);
	}
}
