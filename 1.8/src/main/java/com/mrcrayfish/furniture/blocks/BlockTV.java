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

import java.util.Random;

import com.mrcrayfish.furniture.blocks.tv.Channel;
import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageTVClient;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTV extends BlockFurnitureTile
{
	public static final PropertyInteger CHANNEL = PropertyInteger.create("channel", 0, 4);
	
	public BlockTV(Material material)
	{
		super(material);
		this.setStepSound(Block.soundTypeWood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CHANNEL, 0));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.modernTechnology);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			if(player.isSneaking())
			{
				Channel.stopSound(pos);
				return true;
			}
		}
		
		TileEntity tile_entity = world.getTileEntity(pos);
		if (tile_entity instanceof TileEntityTV)
		{
			TileEntityTV tileEntityTV = (TileEntityTV) tile_entity;
			
			int nextChannel = 0;
			if(tileEntityTV.getChannel() < Channels.getChannelCount() - 1)
			{
				nextChannel = tileEntityTV.getChannel() + 1;
			}
			tileEntityTV.setChannel(nextChannel);
			
			world.markBlockForUpdate(pos);
			world.updateComparatorOutputLevel(pos, this);
			world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "cfm:static", 0.75F, 1.0F);
			
			if(world.isRemote)
			{
				Channels.getChannel(nextChannel).play(pos);
			}
			else
			{
				PacketHandler.INSTANCE.sendToAllAround(new MessageTVClient(tileEntityTV.getChannel(), pos), new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 16));
			}
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		Channel.stopSound(pos);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityTV tileEntityTV = (TileEntityTV) worldIn.getTileEntity(pos);
		return state.withProperty(CHANNEL, tileEntityTV.getChannel());
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTV();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, CHANNEL });
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		TileEntityTV tv = (TileEntityTV) world.getTileEntity(pos);
		return tv.getChannel() + 1;
	}
}
