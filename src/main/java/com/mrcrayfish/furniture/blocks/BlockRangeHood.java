package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockRangeHood extends BlockFurniture implements IRayTrace
{
	public static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.25, 0.2, 1.0, 1.0, 0.8);
	public static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.25, 0.2, 1.0, 1.0, 0.8);
	public static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.25, 0.2, 1.0, 1.0, 0.8);
	public static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.25, 0.2, 1.0, 1.0, 0.8);
	public static final AxisAlignedBB[] COLLISION_BOX = { COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST };
	public static final AxisAlignedBB COLLISION_BOTTOM = new AxisAlignedBB(0.0, 0.0, 0, 1.0, 0.25, 1.0);

	public BlockRangeHood()
	{
		super(Material.IRON, "range_hood");
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setLightLevel(0.5F);
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
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		EnumFacing facing = state.getValue(FACING);

		boxes.add(COLLISION_BOTTOM);
		boxes.add(COLLISION_BOX[facing.getHorizontalIndex()]);
	}
}
