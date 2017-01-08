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

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFillBath;
import com.mrcrayfish.furniture.tileentity.TileEntityBath;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class BlockBath extends BlockFurnitureTile
{
	public static final PropertyInteger WATER_LEVEL = PropertyInteger.create("level", 0, 16);

	/* Collision Bounds */
	private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
	private static final AxisAlignedBB SIDE_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.125, 0.0, 1.0, 0.9, 0.125);
	private static final AxisAlignedBB SIDE_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.0, 0.125, 0.0, 1.0, 0.9, 0.125);
	private static final AxisAlignedBB SIDE_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.0, 0.125, 0.0, 1.0, 0.9, 0.125);
	private static final AxisAlignedBB SIDE_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.0, 0.125, 0.0, 1.0, 0.9, 0.125);
	private static final AxisAlignedBB HEAD_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.125, 0.125, 0.125, 0.9, 0.875);
	private static final AxisAlignedBB HEAD_EAST = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.125, 0.125, 0.125, 0.9, 0.875);
	private static final AxisAlignedBB HEAD_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.125, 0.125, 0.125, 0.9, 0.875);
	private static final AxisAlignedBB HEAD_WEST = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.125, 0.125, 0.125, 0.9, 0.875);
	private static final AxisAlignedBB[] HEADS = { HEAD_SOUTH, HEAD_WEST, HEAD_NORTH, HEAD_WEST };
	private static final AxisAlignedBB[] TAILS = { SIDE_SOUTH, SIDE_WEST, SIDE_NORTH, SIDE_WEST };
	
	
	public BlockBath(Material material, boolean top)
	{
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.STONE);
		if(top) this.setCreativeTab(null);
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		return state.withProperty(WATER_LEVEL, Integer.valueOf(0));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos.offset(placer.getHorizontalFacing()), FurnitureBlocks.bath_2.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(WATER_LEVEL, state.getValue(WATER_LEVEL)));
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB bounds, List<AxisAlignedBB> list, Entity collidingEntity) 
	{
		EnumFacing facing = state.getValue(FACING);
		if(facing.getHorizontalIndex() % 2 == 0)
		{
			addCollisionBoxToList(pos, bounds, list, SIDE_NORTH);
			addCollisionBoxToList(pos, bounds, list, SIDE_SOUTH);
		}
		else
		{
			addCollisionBoxToList(pos, bounds, list, SIDE_EAST);
			addCollisionBoxToList(pos, bounds, list, SIDE_WEST);
		}
		if (this == FurnitureBlocks.bath_1)
		{
			addCollisionBoxToList(pos, bounds, list, HEADS[facing.getHorizontalIndex()]);
		}
		else
		{
			addCollisionBoxToList(pos, bounds, list, TAILS[facing.getHorizontalIndex()]);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			BlockPos otherBathPos = null;
			if (this == FurnitureBlocks.bath_1)
			{
				otherBathPos = pos.offset((EnumFacing) state.getValue(FACING));
			}
			else
			{
				otherBathPos = pos.offset(((EnumFacing) state.getValue(FACING)).getOpposite());
			}

			TileEntity tile_entity_1 = worldIn.getTileEntity(pos);
			TileEntity tile_entity_2 = worldIn.getTileEntity(otherBathPos);
			if (tile_entity_1 instanceof TileEntityBath && tile_entity_2 instanceof TileEntityBath)
			{
				TileEntityBath tileEntityBath = (TileEntityBath) tile_entity_1;
				TileEntityBath tileEntityBath2 = (TileEntityBath) tile_entity_2;

				if (heldItem != null)
				{
					if (heldItem.getItem() == Items.BUCKET)
					{
						if (tileEntityBath.hasWater())
						{
							if (!playerIn.capabilities.isCreativeMode)
							{
								if (heldItem.stackSize > 1)
								{
									if (playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
									{
										heldItem.stackSize--;
									}
								}
								else
								{
									playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
								}
							}
							tileEntityBath.removeWaterLevel();
							tileEntityBath2.removeWaterLevel();
							worldIn.updateComparatorOutputLevel(pos, this);
						}
					}
					else if (heldItem.getItem() == Items.WATER_BUCKET)
					{
						if (!tileEntityBath.isFull())
						{
							tileEntityBath.addWaterLevel();
							tileEntityBath2.addWaterLevel();
							if (!playerIn.capabilities.isCreativeMode)
							{
								playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
							}
							worldIn.updateComparatorOutputLevel(pos, this);
						}
					}
					else if (heldItem.getItem() == Items.GLASS_BOTTLE)
					{
						if (tileEntityBath.hasWater())
						{
							if (!playerIn.capabilities.isCreativeMode)
							{
								if (heldItem.stackSize > 1)
								{
									if (playerIn.inventory.addItemStackToInventory(new ItemStack(Items.POTIONITEM, 1, 0)))
									{
										heldItem.stackSize--;
									}
								}
								else
								{
									playerIn.setHeldItem(hand, new ItemStack(Items.POTIONITEM, 1, 0));
								}
							}
							tileEntityBath.removeWaterLevel();
							tileEntityBath2.removeWaterLevel();
							worldIn.updateComparatorOutputLevel(pos, this);
						}
					}
					else if (heldItem.getItem() == Items.POTIONITEM && heldItem.getItemDamage() == 0)
					{
						if (!tileEntityBath.isFull())
						{
							tileEntityBath.addWaterLevel();
							tileEntityBath2.addWaterLevel();
							if (!playerIn.capabilities.isCreativeMode)
							{
								playerIn.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
							}
							worldIn.updateComparatorOutputLevel(pos, this);
						}
					}
					else
					{
						if (this == FurnitureBlocks.bath_2)
						{
							if (hasWaterSource(worldIn, pos))
							{
								if (!tileEntityBath.isFull())
								{
									tileEntityBath.addWaterLevel();
									tileEntityBath2.addWaterLevel();
									worldIn.playSound(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 0.8F, true);
									worldIn.setBlockToAir(pos.add(0, -2, 0));
									worldIn.updateComparatorOutputLevel(pos, this);
								}
							}
							else
							{
								playerIn.addChatComponentMessage(new TextComponentString("You need to have a water source under the block the bath head is on to fill it. Alternatively you can use a water bucket to fill it."));
							}
						}
					}
				}
				else
				{
					if (this == FurnitureBlocks.bath_2)
					{
						if (hasWaterSource(worldIn, pos))
						{
							if (!tileEntityBath.isFull())
							{
								tileEntityBath.addWaterLevel();
								tileEntityBath2.addWaterLevel();
								worldIn.playSound(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 0.8F, true);
								worldIn.setBlockToAir(pos.add(0, -2, 0));
								worldIn.updateComparatorOutputLevel(pos, this);
							}
						}
						else
						{
							playerIn.addChatComponentMessage(new TextComponentString("You need to have a water source under the block the bath head is on to fill it. Alternatively you can use a water bucket to fill it."));
						}
					}

				}
				PacketHandler.INSTANCE.sendToAllAround(new MessageFillBath(tileEntityBath.getWaterLevel(), pos.getX(), pos.getY(), pos.getZ(), otherBathPos.getX(), otherBathPos.getY(), otherBathPos.getZ()), new TargetPoint(playerIn.dimension, pos.getX(), pos.getY(), pos.getZ(), 128D));
				worldIn.markBlockRangeForRenderUpdate(pos, pos);
				worldIn.markBlockRangeForRenderUpdate(otherBathPos, otherBathPos);
			}
		}
		return true;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(this == FurnitureBlocks.bath_1)
		{
			worldIn.destroyBlock(pos.offset((EnumFacing)state.getValue(FACING)), false);
		} else {
			worldIn.destroyBlock(pos.offset(((EnumFacing)state.getValue(FACING)).getOpposite()), false);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityBath();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(FurnitureBlocks.bath_1).getItem();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(FurnitureBlocks.bath_1);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntityBath bath = (TileEntityBath) world.getTileEntity(pos);
		return state.withProperty(WATER_LEVEL, Integer.valueOf(bath.getWaterLevel()));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, WATER_LEVEL });
	}

	public static boolean canPlaceBath(World world, BlockPos pos, EnumFacing facing)
	{
		return (world.isAirBlock(pos) && world.isAirBlock(pos.offset(facing, 1)));
	}
	
	public boolean hasWaterSource(World world, BlockPos pos)
	{
		return world.getBlockState(pos.add(0, -2, 0)) == Blocks.WATER.getDefaultState();
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		TileEntityBath bath = (TileEntityBath) world.getTileEntity(pos);
		return bath.getWaterLevel();
	}
}
