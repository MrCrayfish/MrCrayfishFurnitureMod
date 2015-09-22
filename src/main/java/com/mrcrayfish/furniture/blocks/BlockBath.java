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
import net.minecraft.block.properties.PropertyInteger;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFillBath;
import com.mrcrayfish.furniture.tileentity.TileEntityBath;

public class BlockBath extends BlockFurnitureTile
{
	public static final PropertyInteger WATER_LEVEL = PropertyInteger.create("level", 0, 16);

	public BlockBath(Material material)
	{
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return null;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
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

			TileEntity tile_entity_1 = world.getTileEntity(pos);
			TileEntity tile_entity_2 = world.getTileEntity(otherBathPos);
			if (tile_entity_1 instanceof TileEntityBath && tile_entity_2 instanceof TileEntityBath)
			{
				TileEntityBath tileEntityBath = (TileEntityBath) tile_entity_1;
				TileEntityBath tileEntityBath2 = (TileEntityBath) tile_entity_2;

				ItemStack currentItem = player.getHeldItem();

				if (currentItem != null)
				{
					if (currentItem.getItem() == Items.bucket)
					{
						if (tileEntityBath.hasWater())
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
							tileEntityBath.removeWaterLevel();
							tileEntityBath2.removeWaterLevel();
							world.updateComparatorOutputLevel(pos, this);
						}
					}
					else if (currentItem.getItem() == Items.water_bucket)
					{
						if (!tileEntityBath.isFull())
						{
							tileEntityBath.addWaterLevel();
							tileEntityBath2.addWaterLevel();
							if (!player.capabilities.isCreativeMode)
							{
								player.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
							}
							world.updateComparatorOutputLevel(pos, this);
						}
					}
					else if (currentItem.getItem() == Items.glass_bottle)
					{
						if (tileEntityBath.hasWater())
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
							tileEntityBath.removeWaterLevel();
							tileEntityBath2.removeWaterLevel();
							world.updateComparatorOutputLevel(pos, this);
						}
					}
					else if (currentItem.getItem() == Items.potionitem && currentItem.getItemDamage() == 0)
					{
						if (!tileEntityBath.isFull())
						{
							tileEntityBath.addWaterLevel();
							tileEntityBath2.addWaterLevel();
							if (!player.capabilities.isCreativeMode)
							{
								player.setCurrentItemOrArmor(0, new ItemStack(Items.glass_bottle));
							}
							world.updateComparatorOutputLevel(pos, this);
						}
					}
					else
					{
						if (this == FurnitureBlocks.bath_2)
						{
							if (hasWaterSource(world, pos))
							{
								if (!tileEntityBath.isFull())
								{
									tileEntityBath.addWaterLevel();
									tileEntityBath2.addWaterLevel();
									world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "cfm:tap", 0.75F, 0.8F);
									world.setBlockToAir(pos.add(0, -2, 0));
									world.updateComparatorOutputLevel(pos, this);
								}
							}
							else
							{
								player.addChatComponentMessage(new ChatComponentText("You need to have a water source under the block the bath head is on to fill it. Alternatively you can use a water bucket to fill it."));
							}
						}
					}
				}
				else
				{
					if (this == FurnitureBlocks.bath_2)
					{
						if (hasWaterSource(world, pos))
						{
							if (!tileEntityBath.isFull())
							{
								tileEntityBath.addWaterLevel();
								tileEntityBath2.addWaterLevel();
								world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "cfm:tap", 0.75F, 0.8F);
								world.setBlockToAir(pos.add(0, -2, 0));
								world.updateComparatorOutputLevel(pos, this);
							}
						}
						else
						{
							player.addChatComponentMessage(new ChatComponentText("You need to have a water source under the block the bath head is on to fill it. Alternatively you can use a water bucket to fill it."));
						}
					}

				}
				PacketHandler.INSTANCE.sendToAllAround(new MessageFillBath(tileEntityBath.getWaterLevel(), pos.getX(), pos.getY(), pos.getZ(), otherBathPos.getX(), otherBathPos.getY(), otherBathPos.getZ()), new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 128D));
				world.markBlockRangeForRenderUpdate(pos, pos);
				world.markBlockRangeForRenderUpdate(otherBathPos, otherBathPos);
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
		return FurnitureItems.itemBath;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemBath);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntityBath bath = (TileEntityBath) world.getTileEntity(pos);
		return state.withProperty(WATER_LEVEL, Integer.valueOf(bath.getWaterLevel()));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING, WATER_LEVEL });
	}

	public static boolean canPlaceBath(World world, BlockPos pos, EnumFacing facing)
	{
		return (world.isAirBlock(pos) && world.isAirBlock(pos.offset(facing, 1)));
	}
	
	public boolean hasWaterSource(World world, BlockPos pos)
	{
		return world.getBlockState(pos.add(0, -2, 0)) == Blocks.water.getDefaultState();
	}

	@Override
	public int getComparatorInputOverride(World world, BlockPos pos) 
	{
		TileEntityBath bath = (TileEntityBath) world.getTileEntity(pos);
		return bath.getWaterLevel();
	}
}
