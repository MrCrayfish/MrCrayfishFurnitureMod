package com.mrcrayfish.furniture.block;

import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

/**
 * Author: MrCrayfish
 */
public interface IDefaultBucketPickupHandler extends IBucketPickupHandler
{
    @Override
    default Fluid pickupFluid(IWorld world, BlockPos pos, IBlockState state)
    {
        if(state.get(BlockStateProperties.WATERLOGGED))
        {
            world.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, false), 3);
            return Fluids.WATER;
        }
        return Fluids.EMPTY;
    }
}
