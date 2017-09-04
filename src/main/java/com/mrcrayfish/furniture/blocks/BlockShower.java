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

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockShower extends BlockFurniture
{
	private static final AxisAlignedBB NOTHING = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	
	private static final AxisAlignedBB SIDE_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB SIDE_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB SIDE_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB SIDE_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
	
	public BlockShower(Material material, boolean top)
	{
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.STONE);
		if(top) this.setCreativeTab(null);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos.up());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos.up(), FurnitureBlocks.shower_top.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
		((EntityPlayer) placer).addStat(FurnitureAchievements.bathroom);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (this == FurnitureBlocks.shower_bottom)
		{
			worldIn.destroyBlock(pos.up(), false);
		}
		else
		{
			worldIn.destroyBlock(pos.down(), false);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState aboveState = worldIn.getBlockState(pos.up());
		Block block = aboveState.getBlock();
		int metadata = getMetaFromState(state);
		if (block == FurnitureBlocks.shower_head_off)
		{
			worldIn.setBlockState(pos.up(), FurnitureBlocks.shower_head_on.getDefaultState().withProperty(FACING, aboveState.getValue(FACING)), 2);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.6F, false);
		}
		else if (block == FurnitureBlocks.shower_head_on)
		{
			worldIn.setBlockState(pos.up(), FurnitureBlocks.shower_head_off.getDefaultState().withProperty(FACING, aboveState.getValue(FACING)), 2);
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F, false);
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		if(Minecraft.getMinecraft().player == null) return NOTHING;
		
		int camera = Minecraft.getMinecraft().gameSettings.thirdPersonView;
		EnumFacing facing = Minecraft.getMinecraft().player.getHorizontalFacing();
		int metadata = getMetaFromState(state);
		
		if (camera == 1 || camera == 2)
		{
			if (facing != state.getValue(FACING).getOpposite())
			{
				switch(Minecraft.getMinecraft().player.getHorizontalFacing())
				{
				case EAST:
					return SIDE_EAST;
				case NORTH:
					return SIDE_NORTH;
				case SOUTH:
					return SIDE_SOUTH;
				case WEST:
					return SIDE_WEST;
				default:
					break;
				}
			}
			else
			{
				return NOTHING;
			}
		}
		return FULL_BLOCK_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) 
	{
		switch(state.getValue(FACING))
		{
		case EAST:
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_NORTH);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_EAST);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_SOUTH);
			break;
		case NORTH:
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_WEST);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_NORTH);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_EAST);
			break;
		case SOUTH:
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_EAST);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_SOUTH);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_WEST);
			break;
		case WEST:
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_SOUTH);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_WEST);
			this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_NORTH);
			break;
		default:
			break;
		}
		
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(FurnitureBlocks.shower_bottom).getItem();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(FurnitureBlocks.shower_bottom);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
