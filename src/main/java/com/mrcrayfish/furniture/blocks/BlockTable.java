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

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTable extends Block
{
	public static final PropertyBool BACK = PropertyBool.create("back");
	public static final PropertyBool FORWARD = PropertyBool.create("forward");
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");

	public static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
	public static final AxisAlignedBB TOP = new AxisAlignedBB(0 * 0.0625, 14.5 * 0.0625, 0 * 0.0625, 16 * 0.0625, 16 * 0.0625, 16 * 0.0625);
	
	public static final AxisAlignedBB SINGLE_LEG = new AxisAlignedBB(6 * 0.0625, 0 * 0.0625, 6 * 0.0625, 10 * 0.0625, 14.5 * 0.0625, 10 * 0.0625);
	public static final AxisAlignedBB[] ONE_AXIS_LEG = new AxisAlignedBB[] { new AxisAlignedBB(6 * 0.0625, 0 * 0.0625, 9 * 0.0625, 10 * 0.0625, 14.5 * 0.0625, 13 * 0.0625), new AxisAlignedBB(9 * 0.0625, 0 * 0.0625, 6 * 0.0625, 13 * 0.0625, 14.5 * 0.0625, 10 * 0.0625), new AxisAlignedBB(6 * 0.0625, 0 * 0.0625, 3 * 0.0625, 10 * 0.0625, 14.5 * 0.0625, 7 * 0.0625), new AxisAlignedBB(3 * 0.0625, 0 * 0.0625, 6 * 0.0625, 7 * 0.0625, 14.5 * 0.0625, 10 * 0.0625) };
	public static final AxisAlignedBB TWO_AXIS_NORTH_EAST = new AxisAlignedBB(3 * 0.0625, 0 * 0.0625, 9 * 0.0625, 7 * 0.0625, 14.5 * 0.0625, 13 * 0.0625);
	public static final AxisAlignedBB TWO_AXIS_SOUTH_EAST = new AxisAlignedBB(3 * 0.0625, 0 * 0.0625, 3 * 0.0625, 7 * 0.0625, 14.5 * 0.0625, 7 * 0.0625);
	public static final AxisAlignedBB TWO_AXIS_NORTH_WEST = new AxisAlignedBB(9 * 0.0625, 0 * 0.0625, 9 * 0.0625, 13 * 0.0625, 14.5 * 0.0625, 13 * 0.0625);
	public static final AxisAlignedBB TWO_AXIS_SOUTH_WEST = new AxisAlignedBB(9 * 0.0625, 0 * 0.0625, 3 * 0.0625, 13 * 0.0625, 14.5 * 0.0625, 7 * 0.0625);

	public BlockTable(Material material, SoundType sound) {
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BACK, false).withProperty(FORWARD, false).withProperty(LEFT, false).withProperty(RIGHT, false));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (placer instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) placer;
			Triggers.trigger(Triggers.PLACE_TABLE, player);
			Triggers.trigger(Triggers.PLACE_CHAIR_OR_TABLE, player);
		}
		super.onBlockPlacedBy(world, pos, state, placer, stack);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		if (side == EnumFacing.UP) {
			return true;
		}
		return false;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		boolean back = world.getBlockState(pos.south()).getBlock() == this;
		boolean forward = world.getBlockState(pos.north()).getBlock() == this;
		boolean left = world.getBlockState(pos.west()).getBlock() == this;
		boolean right = world.getBlockState(pos.east()).getBlock() == this;
		return state.withProperty(BACK, back).withProperty(FORWARD, forward).withProperty(LEFT, left).withProperty(RIGHT, right);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { BACK, FORWARD, LEFT, RIGHT });
	}

	protected List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
	{
		List<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();

		IBlockState actualState = this.getActualState(state, world, pos);
		boolean north = actualState.getValue(FORWARD);
		boolean south = actualState.getValue(BACK);
		boolean east = actualState.getValue(RIGHT);
		boolean west = actualState.getValue(LEFT);

		int connectedSides = (north ? 1 : 0) + (south ? 1 : 0) + (east ? 1 : 0) + (west ? 1 : 0);

		if (connectedSides == 0) {
			boxes.add(SINGLE_LEG);
		} else {
			if (connectedSides >= 3 || (north && south) || (east && west)) {
				boxes.add(EMPTY_AABB);
			} else {
				if (north && east) {
					boxes.add(TWO_AXIS_NORTH_EAST);
				}
				if (south && east) {
					boxes.add(TWO_AXIS_SOUTH_EAST);
				}
				if (north && west) {
					boxes.add(TWO_AXIS_NORTH_WEST);
				}
				if (south && west) {
					boxes.add(TWO_AXIS_SOUTH_WEST);
				}
				if (connectedSides == 1) {
					int connectedIndex = north ? 0 : east ? 3 : south ? 2 : west ? 1 : -1;
					if (connectedIndex >= 0) {
						boxes.add(ONE_AXIS_LEG[connectedIndex]);
					}
				}
			}
		}

		return boxes;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
	{
		List<AxisAlignedBB> list = getCollisionBoxList(this.getActualState(state, world, pos), world, pos);
		for (AxisAlignedBB box : list) {
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
		}
	}

	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, world, pos), world, pos)) {
			list.add(this.rayTrace(pos, start, end, axisalignedbb));
		}

		RayTraceResult raytraceresult1 = null;
		double d1 = 0.0D;

		for (RayTraceResult raytraceresult : list) {
			if (raytraceresult != null) {
				double d0 = raytraceresult.hitVec.squareDistanceTo(end);

				if (d0 > d1) {
					raytraceresult1 = raytraceresult;
					d1 = d0;
				}
			}
		}

		return raytraceresult1;
	}
}
