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

import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageTVPlaySound;
import com.mrcrayfish.furniture.network.message.MessageTVStopSound;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTV extends BlockFurnitureTile
{
	public static final PropertyInteger CHANNEL = PropertyInteger.create("channel", 0, 4);

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

	public BlockTV(Material material) {
		super(material);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CHANNEL, 0));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				PacketHandler.INSTANCE.sendToAllAround(new MessageTVStopSound(pos), new TargetPoint(playerIn.dimension, pos.getX(), pos.getY(), pos.getZ(), 64));
				return true;
			}

			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof TileEntityTV) {
				TileEntityTV tileEntityTelevision = (TileEntityTV) tileEntity;
				tileEntityTelevision.nextChannel();
		        worldIn.updateComparatorOutputLevel(pos, this);
				worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.white_noise, SoundCategory.BLOCKS, 0.75F, 1.0F);
				PacketHandler.INSTANCE.sendToAllAround(new MessageTVPlaySound(pos.add(0.5, 0.5, 0.5), Channels.getChannel(tileEntityTelevision.getChannel()).getChannelName()), new TargetPoint(playerIn.dimension, pos.getX(), pos.getY(), pos.getZ(), 64));
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!worldIn.isRemote) {
			worldIn.updateComparatorOutputLevel(pos, this);
			PacketHandler.INSTANCE.sendToAllAround(new MessageTVStopSound(pos), new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 64));
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntityTV tileEntityTV = (TileEntityTV) worldIn.getTileEntity(pos);
		return super.getActualState(state, worldIn, pos).withProperty(CHANNEL, tileEntityTV.getChannel());
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
		return new BlockStateContainer(this, FACING, CHANNEL);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4));
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		if (world.getTileEntity(pos) instanceof TileEntityTV) {
			TileEntityTV tv = (TileEntityTV) world.getTileEntity(pos);
			return tv.getChannel() + 1;
		}
		return 0;
	}
}
