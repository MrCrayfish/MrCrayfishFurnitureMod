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
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlinds extends BlockFurniture
{
	public static final PropertyBool LEFT = PropertyBool.create("left");

	private boolean open = false;
	
	public BlockBlinds(Material material, boolean open)
	{
		super(material);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeWood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LEFT, false));
		this.open = open;
		if(!open)
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
		if(open)
		{
			return worldIn.setBlockState(pos, FurnitureBlocks.blinds_closed.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
		else
		{
			return worldIn.setBlockState(pos, FurnitureBlocks.blinds.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemBlinds;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemBlinds);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		return state.withProperty(LEFT, worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() instanceof BlockBlinds);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, LEFT });
	}
}
