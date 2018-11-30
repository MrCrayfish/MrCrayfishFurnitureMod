package com.mrcrayfish.furniture.util;

import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class SittableUtil
{

    public static boolean sitOnBlock(World par1World, double x, double y, double z, EntityPlayer par5EntityPlayer, double par6)
    {
        if(!checkForExistingEntity(par1World, x, y, z, par5EntityPlayer))
        {
            EntitySittableBlock nemb = new EntitySittableBlock(par1World, x, y, z, par6);
            par1World.spawnEntity(nemb);
            par5EntityPlayer.startRiding(nemb);
            return true;
        }
        return false;
    }

    public static boolean sitOnBlockWithRotationOffset(World par1World, double x, double y, double z, EntityPlayer par5EntityPlayer, double par6, int metadata, double offset)
    {
        if(!checkForExistingEntity(par1World, x, y, z, par5EntityPlayer) && !par1World.isRemote)
        {
            EntitySittableBlock nemb = new EntitySittableBlock(par1World, x, y, z, par6, metadata, offset);
            par1World.spawnEntity(nemb);
            par5EntityPlayer.startRiding(nemb);
        }
        return true;
    }

    public static boolean checkForExistingEntity(World par1World, double x, double y, double z, EntityPlayer par5EntityPlayer)
    {
        List<EntitySittableBlock> listEMB = par1World.getEntitiesWithinAABB(EntitySittableBlock.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).grow(1D));
        for(EntitySittableBlock mount : listEMB)
        {
            if(mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
            {
                if(!mount.isBeingRidden())
                {
                    par5EntityPlayer.startRiding(mount);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isSomeoneSitting(World world, double x, double y, double z)
    {
        List<EntitySittableBlock> listEMB = world.getEntitiesWithinAABB(EntitySittableBlock.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).grow(1D));
        for(EntitySittableBlock mount : listEMB)
        {
            if(mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
            {
                return mount.isBeingRidden();
            }
        }
        return false;
    }
}
