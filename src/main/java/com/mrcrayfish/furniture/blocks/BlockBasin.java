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
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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

public class BlockBasin extends BlockFurniture
{
	public static final PropertyBool FILLED = PropertyBool.create("filled");

	public BlockBasin(Material material)
	{
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(FILLED, Boolean.valueOf(false)));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).addStat(FurnitureAchievements.bathroom);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		world.updateComparatorOutputLevel(pos, this);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) 
	{
		world.updateComparatorOutputLevel(pos, this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!worldIn.isRemote)
		{
			if (!heldItem.isEmpty())
			{
				if (heldItem.getItem() == Items.BUCKET)
				{
					if (hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							if (heldItem.getCount() > 1)
							{
								if (playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
								{
									heldItem.shrink(1);
								}
							}
							else
							{
								playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
							}
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, false));
						worldIn.updateComparatorOutputLevel(pos, this);
					}
				}
				else if (heldItem.getItem() == Items.WATER_BUCKET)
				{
					if (!hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
						worldIn.updateComparatorOutputLevel(pos, this);
					}
				}
				else if (heldItem.getItem() == Items.GLASS_BOTTLE)
				{
					if (hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							if (heldItem.getCount() > 1)
							{
								if (playerIn.inventory.addItemStackToInventory(new ItemStack(Items.POTIONITEM, 1, 0)))
								{
									heldItem.shrink(1);
								}
							}
							else
							{
								playerIn.setHeldItem(hand, new ItemStack(Items.POTIONITEM, 1, 0));
							}
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, false), 2);
						worldIn.updateComparatorOutputLevel(pos, this);
					}
				}
				else if (heldItem.getItem() == Items.POTIONITEM && heldItem.getItemDamage() == 0)
				{
					if (!hasWater(state))
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							playerIn.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
						}
						worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
						worldIn.updateComparatorOutputLevel(pos, this);
					}
				}
				else
				{
					if (!hasWater(state))
					{
						if (hasWaterSource(worldIn, pos))
						{
							worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
							worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 1.0F);
							worldIn.setBlockToAir(pos.add(0, -2, 0));
							worldIn.updateComparatorOutputLevel(pos, this);
						}
						else
						{
							playerIn.sendMessage(new TextComponentString("You need to have a water source under the block the basin is on to fill it. Alternatively you can use a water bucket to fill it."));
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
						worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
						worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 1.0F);
						worldIn.setBlockToAir(pos.add(0, -2, 0));
						worldIn.updateComparatorOutputLevel(pos, this);
					}
					else
					{
						playerIn.sendMessage(new TextComponentString("You need to have a water source under the block the basin is on to fill it. Alternatively you can use a water bucket to fill it."));
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(FILLED, meta > 3);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex() + (state.getValue(FILLED) ? 0 : 4);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, FILLED);
	}

	private boolean hasWater(IBlockState state)
	{
		return state.getValue(FILLED);
	}

	private boolean hasWaterSource(World world, BlockPos pos)
	{
		return world.getBlockState(pos.add(0, -2, 0)) == Blocks.WATER.getDefaultState();
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) 
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		return world.getBlockState(pos).getValue(FILLED) ? 1 : 0;
	}
}
