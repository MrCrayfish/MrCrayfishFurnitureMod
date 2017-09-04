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

import com.mrcrayfish.furniture.tileentity.TileEntityStereo;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockStereo extends BlockFurnitureTile
{
	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.3, 0.0, 0.0, 0.7, 0.5, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.3, 0.0, 0.0, 0.7, 0.5, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.3, 0.0, 0.0, 0.7, 0.5, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.3, 0.0, 0.0, 0.7, 0.5, 1.0);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };
	
	private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.0, 0.3, 1.0, 0.45, 0.7);
	private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.0, 0.0, 0.3, 1.0, 0.45, 0.7);
	private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.0, 0.0, 0.3, 1.0, 0.45, 0.7);
	private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.0, 0.15, 0.3, 1.0, 0.45, 0.7);
	private static final AxisAlignedBB[] COLLISION_BOX = { COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST };
	
	public static ArrayList<ItemRecord> records = new ArrayList<ItemRecord>();

	public BlockStereo(Material material)
	{
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
		records.add((ItemRecord) Items.RECORD_13);
		records.add((ItemRecord) Items.RECORD_BLOCKS);
		records.add((ItemRecord) Items.RECORD_CAT);
		records.add((ItemRecord) Items.RECORD_CHIRP);
		records.add((ItemRecord) Items.RECORD_FAR);
		records.add((ItemRecord) Items.RECORD_MALL);
		records.add((ItemRecord) Items.RECORD_MELLOHI);
		records.add((ItemRecord) Items.RECORD_STAL);
		records.add((ItemRecord) Items.RECORD_STRAD);
		records.add((ItemRecord) Items.RECORD_WAIT);
		records.add((ItemRecord) Items.RECORD_WARD);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = worldIn.getTileEntity(pos);
		if(tile_entity instanceof TileEntityStereo)
		{
			TileEntityStereo tileEntityStereo = (TileEntityStereo) tile_entity;
			if(!playerIn.isSneaking())
			{
				if(tileEntityStereo.count == 13)
				{
					tileEntityStereo.count = records.size();
				}
				else
				{
					tileEntityStereo.count++;
				}
				if(tileEntityStereo.count == records.size())
				{
					tileEntityStereo.count = 0;
				}
				worldIn.playRecord(pos, records.get(tileEntityStereo.count).getSound());
			}
			else
			{
				if(tileEntityStereo.count != 13)
				{
					tileEntityStereo.count = 13;
					if(!worldIn.isRemote) 
					{
						playerIn.sendMessage(new TextComponentString("Stereo is now turned off."));
					}
					this.ejectRecord(worldIn, pos);
				}
				else
				{
					if(!worldIn.isRemote)
						playerIn.sendMessage(new TextComponentString("Stereo is already turned off."));
				}
			}
			if(!worldIn.isRemote)
			{
				TileEntityUtil.markBlockForUpdate(worldIn, pos);
			}
		}
		return true;

	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		this.ejectRecord(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) 
	{
		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX[facing.getHorizontalIndex()]);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityStereo();
	}

	public void ejectRecord(World worldIn, BlockPos pos)
	{
		worldIn.playEvent(1010, pos, 0);
        worldIn.playRecord(pos, (SoundEvent)null);
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		TileEntityStereo stereo = (TileEntityStereo) world.getTileEntity(pos);
		return stereo.count;
	}
}
