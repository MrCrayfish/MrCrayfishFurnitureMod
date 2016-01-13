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

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLamp extends Block
{
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	public BlockLamp(Material material)
	{
		super(material);
		setHardness(0.75F);
		setStepSound(Block.soundTypeCloth);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
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
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 14F * 0.0625F, 0.9375F);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.modernTechnology);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack currentItem = player.getCurrentEquippedItem();
		if (currentItem != null)
		{
			if (currentItem.getItem() instanceof ItemDye)
			{
				world.setBlockState(pos, state.withProperty(COLOUR, 15 - currentItem.getItemDamage()));
				if (!player.capabilities.isCreativeMode)
					currentItem.stackSize--;
				return true;
			}
		}

		if (!(world.getBlockState(pos.up()).getBlock() instanceof BlockLamp))
		{
			world.setBlockState(pos, FurnitureBlocks.lamp_on.getDefaultState().withProperty(BlockLampOn.COLOUR, (Integer) state.getValue(COLOUR)), 3);
		}
		else
		{
			int yOffset = 1;
			while (world.getBlockState(pos.up(++yOffset)).getBlock() instanceof BlockLamp)
				;

			int colour = (Integer) world.getBlockState(pos.up(yOffset).down()).getValue(COLOUR);

			if (world.getBlockState(pos.up(yOffset).down()).getBlock() instanceof BlockLampOn)
			{
				world.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.lamp_off.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
			}
			else
			{
				world.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.lamp_on.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
			}
		}
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemLamp;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemLamp);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		boolean up = worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLamp;
		boolean down = worldIn.getBlockState(pos.down()).getBlock() instanceof BlockLamp;
		return state.withProperty(UP, up).withProperty(DOWN, down);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(COLOUR, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Integer) state.getValue(COLOUR)).intValue();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { COLOUR, UP, DOWN });
	}
}
