package com.mrcrayfish.furniture.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockModernTV extends BlockAbstractTV implements IRayTrace
{

	public static final AxisAlignedBB BASE_NS = new AxisAlignedBB(2 * 0.0625, 0, 5 * 0.0625, 14 * 0.0625, 1 * 0.0625, 11 * 0.0625);
	public static final AxisAlignedBB BASE_EW = new AxisAlignedBB(5 * 0.0625, 0, 2 * 0.0625, 11 * 0.0625, 1 * 0.0625, 14 * 0.0625);

	public static final AxisAlignedBB STAND_NORTH = new AxisAlignedBB(6 * 0.0625, 1 * 0.0625, 6.75 * 0.0625, 10 * 0.0625, 4 * 0.0625, 8.25 * 0.0625);
	public static final AxisAlignedBB SCREEN_NORTH_F = new AxisAlignedBB(-4 * 0.0625, 3 * 0.0625, 7.5 * 0.0625, 20 * 0.0625, 17 * 0.0625, 8.5 * 0.0625);
	public static final AxisAlignedBB SCREEN_NORTH_B = new AxisAlignedBB(-3 * 0.0625, 4 * 0.0625, 6.5 * 0.0625, 19 * 0.0625, 16 * 0.0625, 7.5 * 0.0625);

	public static final AxisAlignedBB STAND_EAST = new AxisAlignedBB(7.75 * 0.0625, 1 * 0.0625, 6 * 0.0625, 9.25 * 0.0625, 4 * 0.0625, 10 * 0.0625);
	public static final AxisAlignedBB SCREEN_EAST_F = new AxisAlignedBB(7.5 * 0.0625, 3 * 0.0625, -4 * 0.0625, 8.5 * 0.0625, 17 * 0.0625, 20 * 0.0625);
	public static final AxisAlignedBB SCREEN_EAST_B = new AxisAlignedBB(8.5 * 0.0625, 4 * 0.0625, -3 * 0.0625, 9.5 * 0.0625, 16 * 0.0625, 19 * 0.0625);

	public static final AxisAlignedBB STAND_SOUTH = new AxisAlignedBB(6 * 0.0625, 1 * 0.0625, 9.125 * 0.0625, 10 * 0.0625, 4 * 0.0625, 7.75 * 0.0625);
	public static final AxisAlignedBB SCREEN_SOUTH_F = new AxisAlignedBB(20 * 0.0625, 3 * 0.0625, 7.5 * 0.0625, -4 * 0.0625, 17 * 0.0625, 8.5 * 0.0625);
	public static final AxisAlignedBB SCREEN_SOUTH_B = new AxisAlignedBB(19 * 0.0625, 4 * 0.0625, 8.5 * 0.0625, -3 * 0.0625, 16 * 0.0625, 9.5 * 0.0625);

	public static final AxisAlignedBB STAND_WEST = new AxisAlignedBB(6.825 * 0.0625, 1 * 0.0625, 6 * 0.0625, 8.25 * 0.0625, 4 * 0.0625, 10 * 0.0625);
	public static final AxisAlignedBB SCREEN_WEST_F = new AxisAlignedBB(8.5 * 0.0625, 3 * 0.0625, 20 * 0.0625, 7.5 * 0.0625, 17 * 0.0625, -4 * 0.0625);
	public static final AxisAlignedBB SCREEN_WEST_B = new AxisAlignedBB(6.5 * 0.0625, 4 * 0.0625, 19 * 0.0625, 7.5 * 0.0625, 16 * 0.0625, -3 * 0.0625);

	public BlockModernTV()
	{
		super(Material.ANVIL, "modern_tv", 22, 12, 4, -0.35);
	}

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		EnumFacing facing = state.getValue(FACING);

		switch (facing)
		{
		case NORTH:
			boxes.add(STAND_NORTH);
			boxes.add(SCREEN_NORTH_F);
			boxes.add(SCREEN_NORTH_B);
			break;
		case EAST:
			boxes.add(STAND_EAST);
			boxes.add(SCREEN_EAST_F);
			boxes.add(SCREEN_EAST_B);
			break;
		case SOUTH:
			boxes.add(STAND_SOUTH);
			boxes.add(SCREEN_SOUTH_F);
			boxes.add(SCREEN_SOUTH_B);
			break;
		case WEST:
			boxes.add(STAND_WEST);
			boxes.add(SCREEN_WEST_F);
			boxes.add(SCREEN_WEST_B);
			break;
		default:
			break;
		}

		boxes.add(facing.getHorizontalIndex() % 2 == 0 ? BASE_NS : BASE_EW);
	}
}
