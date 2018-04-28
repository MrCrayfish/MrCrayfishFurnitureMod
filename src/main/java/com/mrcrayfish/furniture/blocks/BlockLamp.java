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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.blocks.item.IMetaBlockName;
import com.mrcrayfish.furniture.init.FurnitureBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLamp extends Block implements IMetaBlockName
{
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 14 * 0.0625, 0.9375);

	public BlockLamp(Material material, boolean on) {
		super(material);
		this.setHardness(0.75F);
		this.setSoundType(SoundType.CLOTH);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
		if (!on) {
			this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		}
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() instanceof ItemDye) {
				worldIn.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
				if (!playerIn.isCreative())
					heldItem.shrink(1);
				return true;
			}
		}

		if (!(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLamp)) {
			worldIn.setBlockState(pos, FurnitureBlocks.lamp_on.getDefaultState().withProperty(BlockLampOn.COLOUR, (Integer) state.getValue(COLOUR)), 3);
		} else {
			int yOffset = 1;
			while (worldIn.getBlockState(pos.up(++yOffset)).getBlock() instanceof BlockLamp)
				;

			int colour = worldIn.getBlockState(pos.up(yOffset).down()).getValue(COLOUR);

			if (worldIn.getBlockState(pos.up(yOffset).down()).getBlock() instanceof BlockLampOn) {
				worldIn.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.lamp_off.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
			} else {
				worldIn.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.lamp_on.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
			}
		}
		return true;
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { COLOUR, UP, DOWN });
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, Math.min(meta, 15));
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		drops.add(new ItemStack(FurnitureBlocks.lamp_off, 1, Math.min(state.getValue(COLOUR), 15)));
	}

	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
	{
		for (int i = 0; i < EnumDyeColor.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(FurnitureBlocks.lamp_off, 1, state.getValue(COLOUR));
	}

	@Override
	public String getSpecialName(ItemStack stack)
	{
		int metadata = stack.getMetadata();
		if (metadata < 0 || metadata >= EnumDyeColor.values().length) {
			return EnumDyeColor.WHITE.getDyeColorName();
		}
		return EnumDyeColor.values()[metadata].getDyeColorName();
	}
}
