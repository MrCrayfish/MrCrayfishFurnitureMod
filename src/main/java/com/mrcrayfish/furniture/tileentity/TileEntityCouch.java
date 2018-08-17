package com.mrcrayfish.furniture.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCouch extends TileEntitySyncClient
{
    private int colour = 0;

    public void setColour(int colour)
    {
        this.colour = 15 - colour;
    }

    public int getColour()
    {
        return colour;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.colour = tagCompound.getInteger("colour");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("colour", colour);
        return tagCompound;
    }
}
