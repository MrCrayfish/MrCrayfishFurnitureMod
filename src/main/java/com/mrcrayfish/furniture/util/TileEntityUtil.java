package com.mrcrayfish.furniture.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityUtil
{
    public static void markBlockForUpdate(World world, BlockPos pos)
    {
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }

    public static void syncToClient(TileEntity tileEntity)
    {
        World world = tileEntity.getWorld();
        if(world != null)
        {
            BlockPos pos = tileEntity.getPos();
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
}