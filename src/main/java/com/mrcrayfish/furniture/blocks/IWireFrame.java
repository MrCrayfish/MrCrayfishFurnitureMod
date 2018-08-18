package com.mrcrayfish.furniture.blocks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Used to determine the individual boxes to render inside a block.
 * 
 * <br>
 * </br>
 * 
 * <b>Author: Ocelot5836</b>
 *
 */
public interface IWireFrame
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
	void addWireframeBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes);

}