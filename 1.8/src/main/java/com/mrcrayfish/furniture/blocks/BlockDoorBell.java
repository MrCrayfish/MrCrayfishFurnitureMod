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

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoorBell extends BlockFurniture
{
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockDoorBell(Material material)
	{
		super(material);
		setStepSound(Block.soundTypeWood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.valueOf(true)));
		this.setTickRandomly(true);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return null;
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 30;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		EnumFacing[] aenumfacing = EnumFacing.values();
		int i = aenumfacing.length;

		for (int j = 2; j < i; ++j)
		{
			EnumFacing enumfacing = aenumfacing[j];

			if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(POWERED, Boolean.valueOf(false));
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (this.func_176583_e(worldIn, pos, state))
		{
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (!worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube())
			{
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	private boolean func_176583_e(World worldIn, BlockPos p_176583_2_, IBlockState p_176583_3_)
	{
		if (!this.canPlaceBlockAt(worldIn, p_176583_2_))
		{
			this.dropBlockAsItem(worldIn, p_176583_2_, p_176583_3_, 0);
			worldIn.setBlockToAir(p_176583_2_);
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
	{
		this.func_180681_d(access.getBlockState(pos));
	}

	private void func_180681_d(IBlockState state)
	{
		float f = 0.3F;
		float f1 = 0.7F;
		float f2 = 0.1F;
		float f3 = 0.15F;

		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
		switch (enumfacing.getIndex())
		{
		case 5:
			this.setBlockBounds(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
			break;
		case 4:
			this.setBlockBounds(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
			break;
		case 2:
			this.setBlockBounds(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
			break;
		case 3:
			this.setBlockBounds(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
			break;
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (((Boolean) state.getValue(POWERED)).booleanValue())
		{
			return true;
		}
		else
		{
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)), 3);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "cfm:doorbell", 1.0F, 1.0F);
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			return true;
		}
	}

	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			if (((Boolean) state.getValue(POWERED)).booleanValue())
			{
				this.handleArrow(worldIn, pos, state);
			}
		}
	}

	@Override
	public void setBlockBoundsForItemRender()
	{
		float f = 0.1875F;
		float f1 = 0.125F;
		float f2 = 0.125F;
		this.setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (!worldIn.isRemote)
		{
			if (!((Boolean) state.getValue(POWERED)).booleanValue())
			{
				this.handleArrow(worldIn, pos, state);
			}
		}
	}

	private void handleArrow(World worldIn, BlockPos p_180680_2_, IBlockState p_180680_3_)
	{
		this.func_180681_d(p_180680_3_);
		List list = worldIn.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB((double) p_180680_2_.getX() + this.minX, (double) p_180680_2_.getY() + this.minY, (double) p_180680_2_.getZ() + this.minZ, (double) p_180680_2_.getX() + this.maxX, (double) p_180680_2_.getY() + this.maxY, (double) p_180680_2_.getZ() + this.maxZ));
		boolean flag = !list.isEmpty();
		boolean flag1 = ((Boolean) p_180680_3_.getValue(POWERED)).booleanValue();

		if (flag && !flag1)
		{
			worldIn.setBlockState(p_180680_2_, p_180680_3_.withProperty(POWERED, Boolean.valueOf(true)));
			worldIn.markBlockRangeForRenderUpdate(p_180680_2_, p_180680_2_);
			worldIn.playSoundEffect((double) p_180680_2_.getX() + 0.5D, (double) p_180680_2_.getY() + 0.5D, (double) p_180680_2_.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
		}

		if (!flag && flag1)
		{
			worldIn.setBlockState(p_180680_2_, p_180680_3_.withProperty(POWERED, Boolean.valueOf(false)));
			worldIn.markBlockRangeForRenderUpdate(p_180680_2_, p_180680_2_);
			worldIn.playSoundEffect((double) p_180680_2_.getX() + 0.5D, (double) p_180680_2_.getY() + 0.5D, (double) p_180680_2_.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
		}

		if (flag)
		{
			worldIn.scheduleUpdate(p_180680_2_, this, this.tickRate(worldIn));
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemDoorBell;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemDoorBell);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing;

		switch (meta)
		{
		case 0:
			enumfacing = EnumFacing.NORTH;
			break;
		case 1:
			enumfacing = EnumFacing.EAST;
			break;
		case 2:
			enumfacing = EnumFacing.SOUTH;
			break;
		case 3:
			enumfacing = EnumFacing.WEST;
			break;
		default:
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED, Boolean.valueOf(meta > 3));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = ((EnumFacing) state.getValue(FACING)).getIndex();
		if (((Boolean) state.getValue(POWERED)).booleanValue())
		{
			meta += 4;
		}
		return meta;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, POWERED });
	}
}
