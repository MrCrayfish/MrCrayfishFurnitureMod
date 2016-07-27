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

import com.mrcrayfish.furniture.init.FurnitureSounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockCounterSink extends BlockFurniture
{
	public static final PropertyBool FILLED = PropertyBool.create("filled");
	
	public BlockCounterSink(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setStepSound(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(FILLED, Boolean.valueOf(false)));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			if (heldItem != null)
			{
				if (heldItem.getItem() == Items.bucket)
				{
					if (hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							if (heldItem.stackSize > 1)
							{
								if (playerIn.inventory.addItemStackToInventory(new ItemStack(Items.water_bucket)))
								{
									heldItem.stackSize--;
								}
							}
							else
							{
								playerIn.setHeldItem(hand, new ItemStack(Items.water_bucket));
							}
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(false)));
					}
				}
				else if (heldItem.getItem() == Items.water_bucket)
				{
					if (!hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							playerIn.setHeldItem(hand, new ItemStack(Items.bucket));
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
					}
				}
				else if (heldItem.getItem() == Items.glass_bottle)
				{
					if (hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							if (heldItem.stackSize > 1)
							{
								if (playerIn.inventory.addItemStackToInventory(new ItemStack(Items.potionitem, 1, 0)))
								{
									heldItem.stackSize--;
								}
							}
							else
							{
								playerIn.setHeldItem(hand, new ItemStack(Items.potionitem, 1, 0));
							}
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(false)), 2);
					}
				}
				else if (heldItem.getItem() == Items.potionitem && heldItem.getItemDamage() == 0)
				{
					if (!hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							playerIn.setHeldItem(hand, new ItemStack(Items.glass_bottle));
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
					}
				}
				else
				{
					if (!hasWater(state))
					{
						if (hasWaterSource(worldIn, pos))
						{
							worldIn.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
							worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
							worldIn.setBlockToAir(pos.add(0, -2, 0));
						}
						else
						{
							playerIn.addChatComponentMessage(new TextComponentString("You need to have a water source under the block the basin is on to fill it. Alternatively you can use a water bucket to fill it."));
						}
					}
				}
			}
			else
			{
				if (!hasWater(state))
				{
					if (hasWaterSource(worldIn, pos))
					{
						worldIn.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
						worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
						worldIn.setBlockToAir(pos.add(0, -2, 0));
					}
					else
					{
						playerIn.addChatComponentMessage(new TextComponentString("You need to have a water source under the block the basin is on to fill it. Alternatively you can use a water bucket to fill it."));
					}
				}
			}
		}
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getIndex() + (((Boolean) state.getValue(FILLED)).booleanValue() ? 0 : 4);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, FILLED });
	}
	
	public boolean hasWater(IBlockState state)
	{
		return ((Boolean) state.getValue(FILLED)).booleanValue();
	}

	public boolean hasWaterSource(World world, BlockPos pos)
	{
		return world.getBlockState(pos.add(0, -2, 0)) == Blocks.water.getDefaultState();
	}
}
