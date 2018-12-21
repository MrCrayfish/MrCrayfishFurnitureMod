package com.mrcrayfish.furniture.util;

import com.mrcrayfish.furniture.entity.EntitySeat;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class SeatUtil
{
    public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, double yOffset)
    {
        return sitOnBlockWithRotationOffset(world, x, y, z, player, yOffset, 0, 0);
    }

    public static boolean sitOnBlockWithRotationOffset(World world, double x, double y, double z, EntityPlayer player, double yOffset, int metadata, double rotationOffset)
    {
        if(!world.isRemote && !player.isSneaking() && !checkForExistingSeat(world, x, y, z, player))
        {
            EntitySeat seat = new EntitySeat(world, x, y, z, yOffset, metadata, rotationOffset);
            world.spawnEntity(seat);
            player.startRiding(seat);
        }
        return true;
    }

    @Nullable
    public static EntitySeat getSeat(World world, double x, double y, double z)
    {
        if(!world.isRemote)
        {
            List<EntitySeat> seats = world.getEntitiesWithinAABB(EntitySeat.class, Block.FULL_BLOCK_AABB.offset(x, y, z).grow(1));
            for(EntitySeat seat : seats)
            {
                if(seat.blockPosX == x && seat.blockPosY == y && seat.blockPosZ == z)
                {
                    return seat;
                }
            }
        }
        return null;
    }

    private static boolean checkForExistingSeat(World world, double x, double y, double z, EntityPlayer player)
    {
    	EntitySeat seat = getSeat(world, x, y, z);
        if(seat == null)
        	return false;
 
        if(!seat.isBeingRidden())
            player.startRiding(seat);

        return true;
        
    }

    public static boolean isSomeoneSitting(World world, double x, double y, double z)
    {
    	EntitySeat seat = getSeat(world, x, y, z);
    	return seat == null ? false : seat.isBeingRidden();
    }
}
