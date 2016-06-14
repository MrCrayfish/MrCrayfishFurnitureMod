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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHedge extends Block
{
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public BlockHedge(Material material)
	{
		super(Material.leaves);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeGrass);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean isNormalCube()
	{
		return false;
	}
	
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state)
    {
    	if(this == FurnitureBlocks.hedge_spruce)
    	{
    		return ColorizerFoliage.getFoliageColorPine();
    	}
    	if(this == FurnitureBlocks.hedge_birch)
    	{
    		return ColorizerFoliage.getFoliageColorBirch();
    	}
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
    	if(this == FurnitureBlocks.hedge_spruce)
    	{
    		return ColorizerFoliage.getFoliageColorPine();
    	}
    	if(this == FurnitureBlocks.hedge_birch)
    	{
    		return ColorizerFoliage.getFoliageColorBirch();
    	}
        return BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
    }

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.gardening);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		float f = 0.1875F;
		float f1 = 0.8125F;
		float f2 = 0.1875F;
		float f3 = 0.8125F;

		if (blockAccess.getBlockState(pos.east()).getBlock() instanceof BlockHedge | blockAccess.getBlockState(pos.east()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.east()).getBlock().isNormalCube())
		{
			f1 = 1.0F;
		}
		if (blockAccess.getBlockState(pos.west()).getBlock() instanceof BlockHedge | blockAccess.getBlockState(pos.west()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.west()).getBlock().isNormalCube())
		{
			f = 0.0F;
		}
		if (blockAccess.getBlockState(pos.south()).getBlock() instanceof BlockHedge | blockAccess.getBlockState(pos.south()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.south()).getBlock().isNormalCube())
		{
			f3 = 1.0F;
		}
		if (blockAccess.getBlockState(pos.north()).getBlock() instanceof BlockHedge | blockAccess.getBlockState(pos.north()).getBlock() instanceof BlockFenceGate | blockAccess.getBlockState(pos.north()).getBlock().isNormalCube())
		{
			f2 = 0.0F;
		}

		setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.5F, 0.8125F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);

		if (world.getBlockState(pos.east()).getBlock() instanceof BlockHedge | world.getBlockState(pos.east()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.8125F, 0.0F, 0.1875F, 1.0F, 1.5F, 0.8125F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.west()).getBlock() instanceof BlockHedge | world.getBlockState(pos.west()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.0F, 0.0F, 0.1875F, 0.1875F, 1.5F, 0.8125F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.south()).getBlock() instanceof BlockHedge | world.getBlockState(pos.south()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.1875F, 0.0F, 0.8125F, 0.8125F, 1.5F, 1.0F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}

		if (world.getBlockState(pos.north()).getBlock() instanceof BlockHedge | world.getBlockState(pos.north()).getBlock() instanceof BlockFenceGate)
		{
			setBlockBounds(0.1875F, 0.0F, 0.0F, 0.8125F, 1.5F, 0.1875F);
			super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, Boolean.valueOf(isHedge(world, pos.north()))).withProperty(EAST, Boolean.valueOf(isHedge(world, pos.east()))).withProperty(SOUTH, Boolean.valueOf(isHedge(world, pos.south()))).withProperty(WEST, Boolean.valueOf(isHedge(world, pos.west())));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { NORTH, EAST, SOUTH, WEST });
	}

	public boolean isHedge(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() instanceof BlockHedge | world.getBlockState(pos).getBlock().isNormalCube();
	}
}
