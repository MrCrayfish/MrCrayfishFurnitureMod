package com.mrcrayfish.furniture.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBasin extends TileEntitySyncClient
{
    private boolean hasWater = false;

    public boolean hasWater()
    {
        return hasWater;
    }

    public void setHasWater(boolean hasWater)
    {
        this.hasWater = hasWater;
    }

    public void fill()
    {
        hasWater = true;
    }

    public void empty()
    {
        hasWater = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.hasWater = tagCompound.getBoolean("hasWater");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("hasWater", hasWater);
        return tagCompound;
    }
}