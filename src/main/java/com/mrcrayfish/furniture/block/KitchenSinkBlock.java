package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.tileentity.KitchenSinkTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkBlock extends FurnitureHorizontalBlock
{
    public KitchenSinkBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(!worldIn.isRemote)
        {
            if(FluidUtil.interactWithFluidHandler(player, handIn, worldIn, pos, hit.getFace()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new KitchenSinkTileEntity();
    }
}

