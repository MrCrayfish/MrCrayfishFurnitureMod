package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTV extends TileEntitySyncClient
{
    private int channel = 0;

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        if(tagCompound.hasKey("Channel", 3))
        {
            this.channel = tagCompound.getInteger("Channel");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("Channel", this.getChannel());
        return tagCompound;
    }

    public int getChannel()
    {
        return this.channel;
    }

    public void setChannel(int channel)
    {
        this.channel = channel;
    }

    public void reloadChannel()
    {
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public void nextChannel()
    {
        int nextChannel = 0;
        if(channel < Channels.getChannelCount() - 1)
        {
            nextChannel = channel + 1;
        }
        setChannel(nextChannel);
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }
}
