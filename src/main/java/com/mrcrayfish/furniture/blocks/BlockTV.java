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

import com.mrcrayfish.furniture.blocks.tv.Channel;
import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageTVClient;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTV extends BlockFurnitureTile
{
	public static final PropertyInteger CHANNEL = PropertyInteger.create("channel", 0, 4);
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
	
	public BlockTV(Material material)
	{
		super(material);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CHANNEL, 0));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).addStat(FurnitureAchievements.modernTechnology);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote)
		{
			if(playerIn.isSneaking())
			{
				Channel.stopSound(pos);
				return true;
			}
		}
		
		TileEntity tile_entity = worldIn.getTileEntity(pos);
		if (tile_entity instanceof TileEntityTV)
		{
			TileEntityTV tileEntityTV = (TileEntityTV) tile_entity;
			
			int nextChannel = 0;
			if(tileEntityTV.getChannel() < Channels.getChannelCount() - 1)
			{
				nextChannel = tileEntityTV.getChannel() + 1;
			}
			tileEntityTV.setChannel(nextChannel);
			
			TileEntityUtil.markBlockForUpdate(worldIn, pos);
			worldIn.updateComparatorOutputLevel(pos, this);
			worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.channel_switch, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
			
			if(worldIn.isRemote)
			{
				Channels.getChannel(nextChannel).play(pos);
			}
			else
			{
				PacketHandler.INSTANCE.sendToAllAround(new MessageTVClient(tileEntityTV.getChannel(), pos), new TargetPoint(playerIn.dimension, pos.getX(), pos.getY(), pos.getZ(), 16));
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTV();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, CHANNEL });
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		TileEntityTV tv = (TileEntityTV) world.getTileEntity(pos);
		return tv.getChannel() + 1;
	}
}
