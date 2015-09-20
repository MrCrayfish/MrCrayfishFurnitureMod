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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class BlockCeilingLight extends Block
{
	public static final PropertyEnum MODE = PropertyEnum.create("mode", Mode.class);

	public BlockCeilingLight(Material material, boolean on)
	{
		super(material);
		setHardness(0.5F);
		setStepSound(Block.soundTypeGlass);
		if (on)
		{
			this.setLightLevel(0.8F);
		}

		this.setDefaultState(this.blockState.getBaseState().withProperty(MODE, Mode.RIGHT_CLICK));
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
		setBlockBounds(5F * 0.0625F, 0.4F, 5F * 0.0625F, 11F * 0.0625F, 1.0F, 11F * 0.0625F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (player.isSneaking())
			{
				Mode mode = (Mode) state.getValue(MODE);
				if (mode == Mode.RIGHT_CLICK)
				{
					world.setBlockState(pos, state.withProperty(MODE, Mode.REDSTONE));
					player.addChatComponentMessage(new ChatComponentText("Ceiling Light now in Redstone mode"));
				}
				else
				{
					world.setBlockState(pos, state.withProperty(MODE, Mode.RIGHT_CLICK));
					player.addChatComponentMessage(new ChatComponentText("Ceiling Light now in Right Click mode"));
				}
			}
			else if (((Mode) state.getValue(MODE)) == Mode.RIGHT_CLICK)
			{
				if (this == FurnitureBlocks.ceiling_light_on)
				{
					world.setBlockState(pos, FurnitureBlocks.ceiling_light_off.getDefaultState(), 2);
				}
				else
				{
					world.setBlockState(pos, FurnitureBlocks.ceiling_light_on.getDefaultState(), 2);
				}
			}
		}
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!canBlockStay(world, pos))
		{
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
			return;
		}

		if (((Mode) state.getValue(MODE)) == Mode.REDSTONE)
		{
			if (world.isBlockPowered(pos))
			{
				world.setBlockState(pos, FurnitureBlocks.ceiling_light_on.getDefaultState().withProperty(MODE, state.getValue(MODE)), 2);
			}
			else
			{
				world.setBlockState(pos, FurnitureBlocks.ceiling_light_off.getDefaultState().withProperty(MODE, state.getValue(MODE)), 2);
			}
		}
	}

	public boolean canBlockStay(World par1World, BlockPos pos)
	{
		return !par1World.isAirBlock(pos.up());
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.DOWN;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemCeilingLight;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemCeilingLight);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Mode) state.getValue(MODE) == Mode.REDSTONE ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MODE, meta == 1 ? Mode.REDSTONE : Mode.RIGHT_CLICK);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { MODE });
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	public static enum Mode implements IStringSerializable
	{
		RIGHT_CLICK, REDSTONE;

		@Override
		public String getName()
		{
			return toString().toLowerCase();
		}
	}
}
