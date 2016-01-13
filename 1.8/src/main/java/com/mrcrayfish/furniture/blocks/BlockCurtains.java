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

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCurtains extends BlockFurniture
{
	public static final PropertyEnum TYPE = PropertyEnum.create("type", Type.class);
	
	public BlockCurtains(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeCloth);
		if(!isOpen())
		{
			this.setLightOpacity(255);
		}
		else
		{
			this.setLightOpacity(0);
		}
	}
	
	@Override
	public boolean isTranslucent() 
	{
		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		IBlockState state = blockAccess.getBlockState(pos);
		int rotation = ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		float[] bounds = CollisionHelper.fixRotation(rotation, 0.875F, 0.0F, 1.0F, 1.0F);
		setBlockBounds(bounds[0], 0.0F, bounds[1], bounds[2], 1.0F, bounds[3]);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		int rotation = ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		float[] bounds = CollisionHelper.fixRotation(rotation, 0.875F, 0.0F, 1.0F, 1.0F);
		setBlockBounds(bounds[0], 0.0F, bounds[1], bounds[2], 1.0F, bounds[3]);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(isOpen())
		{
			return worldIn.setBlockState(pos, FurnitureBlocks.curtains_closed.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
		else
		{
			return worldIn.setBlockState(pos, FurnitureBlocks.curtains.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if(isOpen())
		{
			EnumFacing facing = (EnumFacing) state.getValue(FACING);
			IBlockState left_block = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
			IBlockState right_block = worldIn.getBlockState(pos.offset(facing.rotateY()));
			boolean left_open = left_block.getBlock() instanceof BlockCurtainsOpen && left_block.getValue(FACING).equals(facing);
			boolean right_open = right_block.getBlock() instanceof BlockCurtainsOpen && right_block.getValue(FACING).equals(facing);
			boolean left_closed = left_block.getBlock() instanceof BlockCurtainsClosed && left_block.getValue(FACING).equals(facing);
			boolean right_closed = right_block.getBlock() instanceof BlockCurtainsClosed && right_block.getValue(FACING).equals(facing);
			
			if(right_open)
			{
				if(left_open)
				{
					return state.withProperty(TYPE, Type.NONE);
				}
				else if(left_closed)
				{
					return state.withProperty(TYPE, Type.LEFT_CLOSED);
				}
				else
				{
					return state.withProperty(TYPE, Type.RIGHT_OPEN);
				}
			}
			else if(left_open)
			{
				if(right_open)
				{
					return state.withProperty(TYPE, Type.NONE);
				}
				else if(right_closed)
				{
					return state.withProperty(TYPE, Type.RIGHT_CLOSED);
				}
				else
				{
					return state.withProperty(TYPE, Type.LEFT_OPEN);
				}
			}
			else if(right_closed)
			{
				if(left_closed)
				{
					return state.withProperty(TYPE, Type.BOTH);
				}
				else if(left_open)
				{
					return state.withProperty(TYPE, Type.LEFT);
				}
				else
				{
					return state.withProperty(TYPE, Type.RIGHT);
				}
			}
			else if(left_closed)
			{
				if(right_closed)
				{
					return state.withProperty(TYPE, Type.BOTH);
				}
				else if(right_open)
				{
					return state.withProperty(TYPE, Type.LEFT_CLOSED);
				}
				else
				{
					return state.withProperty(TYPE, Type.LEFT);
				}
			}
			return state.withProperty(TYPE, Type.DEFAULT);
		}
		return state;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemCurtains;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemCurtains);
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return isOpen() ? new BlockState(this, new IProperty[] { FACING, TYPE}) : super.createBlockState();
	}
	
	public abstract boolean isOpen();
	
	public static enum Type implements IStringSerializable
	{
		DEFAULT, LEFT, RIGHT, LEFT_OPEN, RIGHT_OPEN, LEFT_CLOSED, RIGHT_CLOSED, BOTH, NONE;

		@Override
		public String getName()
		{
			return this.toString().toLowerCase();
		}
	}
}
