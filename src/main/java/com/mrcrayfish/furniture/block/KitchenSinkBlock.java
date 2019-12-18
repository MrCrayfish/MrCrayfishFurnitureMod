package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.tileentity.KitchenSinkTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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
    public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(!world.isRemote)
        {
            return FluidUtil.interactWithFluidHandler(playerEntity, hand, world, pos, result.getFace()) ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
        return ActionResultType.SUCCESS;
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

