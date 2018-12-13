package com.mrcrayfish.furniture.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySeat extends Entity
{
    public int blockPosX;
    public int blockPosY;
    public int blockPosZ;

    public EntitySeat(World world)
    {
        super(world);
        this.noClip = true;
        this.height = 0.01F;
        this.width = 0.01F;
    }

    public EntitySeat(World world, double x, double y, double z, double y0ffset)
    {
        this(world);
        this.blockPosX = (int) x;
        this.blockPosY = (int) y;
        this.blockPosZ = (int) z;
        setPosition(x + 0.5D, y + y0ffset, z + 0.5D);
    }

    public EntitySeat(World world, double x, double y, double z, double y0ffset, int rotation, double rotationOffset)
    {
        this(world);
        this.blockPosX = (int) x;
        this.blockPosY = (int) y;
        this.blockPosZ = (int) z;
        setPostionConsideringRotation(x + 0.5D, y + y0ffset, z + 0.5D, rotation, rotationOffset);
    }

    public void setPostionConsideringRotation(double x, double y, double z, int rotation, double rotationOffset)
    {
        switch(rotation)
        {
            case 2:
                z += rotationOffset;
                break;
            case 0:
                z -= rotationOffset;
                break;
            case 3:
                x -= rotationOffset;
                break;
            case 1:
                x += rotationOffset;
                break;
        }
        setPosition(x, y, z);
    }

    @Override
    public double getMountedYOffset()
    {
        return this.height * 0.0D;
    }

    @Override
    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }

    @Override
    public void onEntityUpdate()
    {
        if(!this.world.isRemote)
        {
            if(!this.isBeingRidden() || this.world.isAirBlock(new BlockPos(blockPosX, blockPosY, blockPosZ)))
            {
                this.setDead();
                world.updateComparatorOutputLevel(getPosition(), world.getBlockState(getPosition()).getBlock());
            }
        }
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

}
