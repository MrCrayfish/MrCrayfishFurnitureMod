package com.mrcrayfish.furniture.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityStereo extends TileEntitySyncClient
{
    public int count;

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.count = tagCompound.getInteger("count");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("count", count);
        return tagCompound;
    }
}
