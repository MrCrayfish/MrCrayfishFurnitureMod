package com.mrcrayfish.furniture.blocks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Used in {@link ModBlock} to determine if a block should have a custom
 * ray trace or not.
 * 
 * <br>
 * </br>
 * 
 * <b><i>PLEASE NOTE:</b> bounding boxes are required for more than rending and
 * hitting so it is recommended to still have one</i>
 * 
 * <br>
 * </br>
 * 
 * <b>Author: Ocelot5836</b>
 *
 */
public interface IRayTrace
{
	/**
	 * Adds the specified ray trace boxes to a list to be used when ray tracing.
	 * 
	 * @param state
	 *            The actual state of the block
	 * @param world
	 *            The world this block is in
	 * @param pos
	 *            The position of this block
	 * @param boxes
	 *            The list to add the boxes to
	 */
	void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes);

	/**
	 * Used to determine if the boxes added in
	 * {@link #addBoxes(IBlockState, World, BlockPos, List)} should also be used for
	 * collisions.
	 * 
	 * @param state
	 *            The actual state of the block
	 * @param world
	 *            The world this block is in
	 * @param pos
	 *            The position of this block
	 * @return Whether or not the boxes added will be used for collisions as well
	 */
	boolean applyCollisions(IBlockState state, World world, BlockPos pos);
}