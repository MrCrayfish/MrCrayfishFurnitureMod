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
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityBath;

public class BlockBasin extends BlockFurniture
{
	public static final PropertyBool FILLED = PropertyBool.create("filled");

	public BlockBasin(Material material)
	{
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeStone);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(FILLED, Boolean.valueOf(false)));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		((EntityPlayer) placer).triggerAchievement(FurnitureAchievements.bathroom);
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack currentItem = player.getCurrentEquippedItem();

			if (currentItem != null)
			{
				if (currentItem.getItem() == Items.bucket)
				{
					if (hasWater(state))
					{
						if (!player.capabilities.isCreativeMode)
						{
							if (currentItem.stackSize > 1)
							{
								if (player.inventory.addItemStackToInventory(new ItemStack(Items.water_bucket)))
								{
									player.getHeldItem().stackSize--;
								}
							}
							else
							{
								player.setCurrentItemOrArmor(0, new ItemStack(Items.water_bucket));
							}
						}
						world.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(false)));
						world.updateComparatorOutputLevel(pos, this);
					}
				}
				else if (currentItem.getItem() == Items.water_bucket)
				{
					if (!hasWater(state))
					{
						if (!player.capabilities.isCreativeMode)
						{
							player.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
						}
						world.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
						world.updateComparatorOutputLevel(pos, this);
					}
				}
				else if (currentItem.getItem() == Items.glass_bottle)
				{
					if (hasWater(state))
					{
						if (!player.capabilities.isCreativeMode)
						{
							if (currentItem.stackSize > 1)
							{
								if (player.inventory.addItemStackToInventory(new ItemStack(Items.potionitem, 1, 0)))
								{
									player.getHeldItem().stackSize--;
								}
							}
							else
							{
								player.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, 0));
							}
						}
						world.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(false)), 2);
						world.updateComparatorOutputLevel(pos, this);
					}
				}
				else if (currentItem.getItem() == Items.potionitem && currentItem.getItemDamage() == 0)
				{
					if (!hasWater(state))
					{
						if (!player.capabilities.isCreativeMode)
						{
							player.setCurrentItemOrArmor(0, new ItemStack(Items.glass_bottle));
						}
						world.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
						world.updateComparatorOutputLevel(pos, this);
					}
				}
				else
				{
					if (!hasWater(state))
					{
						if (hasWaterSource(world, pos))
						{
							world.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
							world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:tap", 0.75F, 1.0F);
							world.setBlockToAir(pos.add(0, -2, 0));
							world.updateComparatorOutputLevel(pos, this);
						}
						else
						{
							player.addChatComponentMessage(new ChatComponentText("You need to have a water source under the block the basin is on to fill it. Alternatively you can use a water bucket to fill it."));
						}
					}
				}
			}
			else
			{
				if (!hasWater(state))
				{
					if (hasWaterSource(world, pos))
					{
						world.setBlockState(pos, state.withProperty(FILLED, Boolean.valueOf(true)), 2);
						world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:tap", 0.75F, 1.0F);
						world.setBlockToAir(pos.add(0, -2, 0));
						world.updateComparatorOutputLevel(pos, this);
					}
					else
					{
						player.addChatComponentMessage(new ChatComponentText("You need to have a water source under the block the basin is on to fill it. Alternatively you can use a water bucket to fill it."));
					}
				}
			}
		}
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemBasin;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemBasin);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getIndex() + (((Boolean) state.getValue(FILLED)).booleanValue() ? 0 : 4);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, FILLED });
	}

	public boolean hasWater(IBlockState state)
	{
		return ((Boolean) state.getValue(FILLED)).booleanValue();
	}

	public boolean hasWaterSource(World world, BlockPos pos)
	{
		return world.getBlockState(pos.add(0, -2, 0)) == Blocks.water.getDefaultState();
	}
	
	@Override
	public boolean hasComparatorInputOverride() 
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		return (Boolean) world.getBlockState(pos).getValue(FILLED) ? 1 : 0;
	}
}
