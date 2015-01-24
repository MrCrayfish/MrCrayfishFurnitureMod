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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityCouch;
import com.mrcrayfish.furniture.util.StateHelper;

public class BlockCouch extends BlockFurnitureTile
{
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	public static final PropertyBool FRONT_LEFT = PropertyBool.create("front-left");
	public static final PropertyBool FRONT_RIGHT = PropertyBool.create("front-right");
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
	
	public BlockCouch(Material material)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeCloth);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, Boolean.valueOf(false)).withProperty(RIGHT, Boolean.valueOf(false)).withProperty(FRONT_LEFT, Boolean.valueOf(false)).withProperty(FRONT_RIGHT, Boolean.valueOf(false)).withProperty(COLOUR, Integer.valueOf(0)));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		boolean left = false;
		boolean right = false;
		
		int colour = ((TileEntityCouch)world.getTileEntity(pos)).getColour();
		state = state.withProperty(COLOUR, Integer.valueOf(colour));

		if (StateHelper.getBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.DOWN) instanceof BlockCouch)
		{
			if (StateHelper.getRotation(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
			{
				return state.withProperty(FRONT_RIGHT, Boolean.valueOf(true));
			}
			else if (StateHelper.getRotation(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
			{
				return state.withProperty(FRONT_LEFT, Boolean.valueOf(true));
			}
			else
			{
				if (!StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.LEFT))
				{
					left = true;
				}
				if (!StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.RIGHT))
				{
					right = true;
				}
				return state.withProperty(LEFT, Boolean.valueOf(left)).withProperty(RIGHT, Boolean.valueOf(right));
			}
		}
		else
		{
			if (!StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.LEFT))
			{
				left = true;
			}
			if (!StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.RIGHT))
			{
				right = true;
			}
			return state.withProperty(LEFT, Boolean.valueOf(left)).withProperty(RIGHT, Boolean.valueOf(right));
		}
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		return state.withProperty(COLOUR, Integer.valueOf(0));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCouch();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemCouch;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemCouch);
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, LEFT, RIGHT, FRONT_LEFT, FRONT_RIGHT, COLOUR });
	}
}
