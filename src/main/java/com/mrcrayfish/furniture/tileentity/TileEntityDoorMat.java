package com.mrcrayfish.furniture.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDoorMat extends TileEntitySyncClient
{
    private String message = null;
    private int colour = 0;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

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
        this.message = tagCompound.getString("message");
        this.colour = tagCompound.getInteger("colour");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        if(this.message != null)
        {
            tagCompound.setString("message", this.message);
        }
        tagCompound.setInteger("colour", colour);
        return tagCompound;
    }
}
