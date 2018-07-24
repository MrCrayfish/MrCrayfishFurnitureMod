package com.mrcrayfish.furniture.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public interface IPowered
{
    void setPowered(World world, BlockPos pos, boolean powered);
}
