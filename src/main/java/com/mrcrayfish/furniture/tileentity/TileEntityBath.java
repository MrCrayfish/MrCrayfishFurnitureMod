package com.mrcrayfish.furniture.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBath extends TileEntitySyncClient
{
    private int waterLevel = 0;

    public boolean hasWater()
    {
        return waterLevel > 0;
    }

    public boolean isFull()
    {
        return waterLevel == 16;
    }

    public int getWaterLevel()
    {
        return waterLevel;
    }

    public void addWaterLevel()
    {
        if(waterLevel < 16) waterLevel++;
    }

    public void removeWaterLevel()
    {
        if(waterLevel > 0) waterLevel--;
    }

    public void setWaterLevel(int waterLevel)
    {
        this.waterLevel = waterLevel;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.waterLevel = tagCompound.getInteger("waterLevel");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("waterLevel", waterLevel);
        return tagCompound;
    }
}