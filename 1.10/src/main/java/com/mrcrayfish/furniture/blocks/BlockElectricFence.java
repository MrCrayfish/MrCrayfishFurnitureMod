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

import com.mrcrayfish.furniture.DamageSourceFence;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockElectricFence extends Block
{
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public DamageSource electricFence = new DamageSourceFence("electricFence");

	public BlockElectricFence(Material material)
	{
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.ANVIL);
		this.setLightLevel(0.2F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).addStat(FurnitureAchievements.modernTechnology);
	}

	//TODO FINISH
	/*@Override
	@SideOnly(Side.CLIENT)
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;

		if (blockAccess.getBlockState(pos.east()).getBlock() == FurnitureBlocks.electric_fence || blockAccess.getBlockState(pos.east()).getBlock() instanceof BlockFenceGate || blockAccess.getBlockState(pos.east()).getBlock().isNormalCube())
		{
			f1 = 1.0F;
		}
		if (blockAccess.getBlockState(pos.west()).getBlock() == FurnitureBlocks.electric_fence || blockAccess.getBlockState(pos.west()).getBlock() instanceof BlockFenceGate || blockAccess.getBlockState(pos.west()).getBlock().isNormalCube())
		{
			f = 0.0F;
		}
		if (blockAccess.getBlockState(pos.south()).getBlock() == FurnitureBlocks.electric_fence || blockAccess.getBlockState(pos.south()).getBlock() instanceof BlockFenceGate || blockAccess.getBlockState(pos.south()).getBlock().isNormalCube())
		{
			f3 = 1.0F;
		}
		if (blockAccess.getBlockState(pos.north()).getBlock() == FurnitureBlocks.electric_fence || blockAccess.getBlockState(pos.north()).getBlock() instanceof BlockFenceGate || blockAccess.getBlockState(pos.north()).getBlock().isNormalCube())
		{
			f2 = 0.0F;
		}

		setBlockBounds(f, 0.0F, f2, f1, 1.1F, f3);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);

		if (world.getBlockState(pos.east()).getBlock() == FurnitureBlocks.electric_fence || world.getBlockState(pos.east()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.4375F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.west()).getBlock() == FurnitureBlocks.electric_fence || world.getBlockState(pos.west()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.south()).getBlock() == FurnitureBlocks.electric_fence || world.getBlockState(pos.south()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.north()).getBlock() == FurnitureBlocks.electric_fence || world.getBlockState(pos.north()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
	}*/

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (!(entity instanceof EntityItem) && !entity.getName().equals("unknown"))
		{
			if (entity instanceof EntityCreeper)
			{
				EntityCreeper creeper = (EntityCreeper) entity;
				EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false);
				if(!creeper.getPowered())
				{
					creeper.setFire(1);
					creeper.onStruckByLightning(lightning);
				}
			}
			else if (entity instanceof EntityPlayer)
			{
				if (!((EntityPlayer) entity).capabilities.isCreativeMode)
				{
					entity.attackEntityFrom(this.electricFence, (int) 2.0F);
					world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.zap, SoundCategory.BLOCKS, 0.2F, 1.0F, false);
					
					this.sparkle(world, pos);
					((EntityPlayer) entity).addStat(FurnitureAchievements.careful);
				}
			}
			else
			{
				entity.attackEntityFrom(this.electricFence, (int) 2.0F);
				world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.zap, SoundCategory.BLOCKS, 0.2F, 1.0F, false);
				this.sparkle(world, pos);
			}
		}
	}

	private void sparkle(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		double d0 = 0.0625D;

		for (int l = 0; l < 6; ++l)
		{
			double d1 = (pos.getX() + RANDOM.nextFloat());
			double d2 = (pos.getY() + RANDOM.nextFloat());
			double d3 = (pos.getZ() + RANDOM.nextFloat());

			if (l == 0 && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
			{
				d2 = (pos.getY() + 1) + d0;
			}

			if (l == 1 && !worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube(state))
			{
				d2 = (pos.getY() + 0) - d0;
			}

			if (l == 2 && !worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube(state))
			{
				d3 = (pos.getZ() + 1) + d0;
			}

			if (l == 3 && !worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube(state))
			{
				d3 = (pos.getZ() + 0) - d0;
			}

			if (l == 4 && !worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube(state))
			{
				d1 = (pos.getX() + 1) + d0;
			}

			if (l == 5 && !worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube(state))
			{
				d1 = (pos.getX() + 0) - d0;
			}

			if (d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1))
			{
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, Boolean.valueOf(isFence(world, pos.north()))).withProperty(EAST, Boolean.valueOf(isFence(world, pos.east()))).withProperty(SOUTH, Boolean.valueOf(isFence(world, pos.south()))).withProperty(WEST, Boolean.valueOf(isFence(world, pos.west())));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST });
	}

	public boolean isFence(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() == this;
	}
}
