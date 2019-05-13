package com.mrcrayfish.furniture.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

/**
 * Author: MrCrayfish
 */
public class BlockFurnitureWaterlogged extends BlockFurniture implements IDefaultBucketPickupHandler, IDefaultLiquidContainer
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockFurnitureWaterlogged(Properties properties)
    {
        super(properties);
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context)
    {
        IFluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public IFluidState getFluidState(IBlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder)
    {
        builder.add(WATERLOGGED);
    }
}
