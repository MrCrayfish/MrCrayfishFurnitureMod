package com.mrcrayfish.furniture.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import com.mrcrayfish.furniture.blocks.BlockFurniture;

public class StateHelper
{
	public static Block getBlock(IBlockAccess world, BlockPos pos, EnumFacing facing, Direction dir)
	{
		BlockPos target = getBlockPosRelativeTo(world, pos, facing, dir);
		return world.getBlockState(target).getBlock();
	}

	public static Direction getRotation(IBlockAccess world, BlockPos pos, EnumFacing facing, Direction dir)
	{
		BlockPos target = getBlockPosRelativeTo(world, pos, facing, dir);
		EnumFacing other = (EnumFacing) world.getBlockState(target).getValue(BlockFurniture.FACING);
		Direction dir_2 = getDirectionRelativeTo(facing, other);
		return dir_2;
	}
	
	public static boolean isAirBlock(IBlockAccess world, BlockPos pos, EnumFacing facing, Direction dir)
	{
		BlockPos target = getBlockPosRelativeTo(world, pos, facing, dir);
		return world.getBlockState(target).getBlock() instanceof BlockAir;
	}

	private static BlockPos getBlockPosRelativeTo(IBlockAccess world, BlockPos pos, EnumFacing facing, Direction dir)
	{
		switch (dir)
		{
		case LEFT:
			return pos.offset(facing.rotateY());
		case RIGHT:
			return pos.offset(facing.rotateYCCW());
		case UP:
			return pos.offset(facing.getOpposite());
		case DOWN:
			return pos.offset(facing);
		default:
			return pos;
		}
	}

	private static Direction getDirectionRelativeTo(EnumFacing thisBlock, EnumFacing otherBlock)
	{
		int num = thisBlock.getHorizontalIndex() - otherBlock.getHorizontalIndex();
		switch (num)
		{
		case -3:
			return Direction.LEFT;
		case -2:
			return Direction.UP;
		case -1:
			return Direction.RIGHT;
		case 0:
			return Direction.DOWN;
		case 1:
			return Direction.RIGHT;
		case 2:
			return Direction.UP;
		case 3:
			return Direction.LEFT;
		}
		return Direction.NONE;
	}

	public static enum Direction
	{
		UP, DOWN, LEFT, RIGHT, NONE;
	}
}
