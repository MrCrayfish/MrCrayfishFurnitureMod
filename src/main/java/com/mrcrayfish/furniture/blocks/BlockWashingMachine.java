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

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWashingMachine extends BlockFurnitureTile
{
	public static final AxisAlignedBB CENTER_BOX_AND_PANEL = new AxisAlignedBB(1 * 0.0625, 0, 1 * 0.0625, 15 * 0.0625, 1, 15 * 0.0625);
	public static final AxisAlignedBB[] CENTER_BOXES = new AxisAlignedBB[] { new AxisAlignedBB(1 * 0.0625, 0, 2 * 0.0625, 15 * 0.0625, 1, 15 * 0.0625), new AxisAlignedBB(1 * 0.0625, 0, 1 * 0.0625, 14 * 0.0625, 1, 15 * 0.0625), new AxisAlignedBB(1 * 0.0625, 0, 1 * 0.0625, 15 * 0.0625, 1, 14 * 0.0625), new AxisAlignedBB(2 * 0.0625, 0, 1 * 0.0625, 15 * 0.0625, 1, 15 * 0.0625) };

	public BlockWashingMachine(Material material) {
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.ANVIL);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote) {
			TileEntity tile_entity = worldIn.getTileEntity(pos);

			if (tile_entity instanceof TileEntityWashingMachine) {
				playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityWashingMachine();
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		TileEntityWashingMachine washing_machine = (TileEntityWashingMachine) world.getTileEntity(pos);
		return washing_machine.isWashing() ? 1 : 0;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState)
	{
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, CENTER_BOX_AND_PANEL);
	}

	private List<AxisAlignedBB> getCollisionBoxList(IBlockState state)
	{
		List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
		EnumFacing facing = state.getValue(FACING);
		list.add(CENTER_BOXES[facing.getHorizontalIndex()]);
		return list;
	}

	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, world, pos))) {
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
