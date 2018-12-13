package com.mrcrayfish.furniture.tileentity;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.blocks.BlockAbstractTV;
import com.mrcrayfish.furniture.client.GifCache;
import com.mrcrayfish.furniture.client.GifDownloadThread;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class TileEntityTV extends TileEntitySyncClient implements IValueContainer
{
    private int width;
    private int height;
    private double screenYOffset;
    private double screenZOffset;
    private boolean stretch;
    private boolean powered;
    private boolean disabled;

    private List<String> channels = new ArrayList<>();
    private int currentChannel;

    @SideOnly(Side.CLIENT)
    private boolean loading;
    @SideOnly(Side.CLIENT)
    private boolean loaded;
    @SideOnly(Side.CLIENT)
    private GifDownloadThread.ImageDownloadResult result;

    public TileEntityTV() {}

    public TileEntityTV(int width, int height, double screenYOffset, double screenZOffset)
    {
        this.width = width;
        this.height = height;
        this.screenYOffset = screenYOffset;
        this.screenZOffset = screenZOffset;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList channelList = new NBTTagList();
        channels.forEach(url -> channelList.appendTag(new NBTTagString(url)));
        compound.setTag("Channels", channelList);
        compound.setInteger("CurrentChannel", this.currentChannel);
        compound.setBoolean("Stretch", this.stretch);
        compound.setBoolean("Powered", this.powered);
        compound.setBoolean("DisableInteraction", this.disabled);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.channels.clear();
        if(compound.hasKey("Channels", Constants.NBT.TAG_LIST))
        {
            NBTTagList channelList = compound.getTagList("Channels", Constants.NBT.TAG_STRING);
            channelList.forEach(nbtBase ->
            {
                if(nbtBase instanceof NBTTagString)
                {
                    NBTTagString url = (NBTTagString) nbtBase;
                    channels.add(url.getString());
                }
            });
        }
        else if(compound.hasKey("URL", Constants.NBT.TAG_STRING))
        {
            this.channels.add(compound.getString("URL"));
        }
        if(compound.hasKey("CurrentChannel", Constants.NBT.TAG_INT))
        {
            this.currentChannel = compound.getInteger("CurrentChannel");
        }
        if(compound.hasKey("Stretch", Constants.NBT.TAG_BYTE))
        {
            this.stretch = compound.getBoolean("Stretch");
        }
        if(compound.hasKey("Powered", Constants.NBT.TAG_BYTE))
        {
            this.powered = compound.getBoolean("Powered");
        }
        if(compound.hasKey("DisableInteraction", Constants.NBT.TAG_BYTE))
        {
            this.disabled = compound.getBoolean("DisableInteraction");
        }
        if(world != null && world.isRemote && powered && channels.size() > 0 && currentChannel >= 0 && currentChannel < channels.size())
        {
            this.loadUrl(channels.get(currentChannel));
        }
    }

    @Nullable
    public String getCurrentChannel()
    {
        if(channels.size() > 0 && currentChannel >= 0 && currentChannel < channels.size())
        {
            return channels.get(currentChannel);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void loadUrl(String url)
    {
        if(loading)
            return;

        this.loaded = false;
        this.result = null;
        if(!GifCache.INSTANCE.loadCached(url))
        {
            this.loading = true;
            new GifDownloadThread(url, (result, message) ->
            {
                this.loading = false;
                this.result = result;
                if(result == GifDownloadThread.ImageDownloadResult.SUCCESS)
                {
                    this.loaded = true;
                }
            }).start();
        }
        else
        {
            this.loaded = true;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isLoading()
    {
        return loading;
    }

    @SideOnly(Side.CLIENT)
    public boolean isLoaded()
    {
        return loaded && !loading;
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public GifDownloadThread.ImageDownloadResult getResult()
    {
        return result;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public double getScreenYOffset()
    {
        return screenYOffset;
    }

    public double getScreenZOffset()
    {
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof BlockAbstractTV)
        {
            return ((BlockAbstractTV) state.getBlock()).getScreenZOffset(state);
        }
        return screenZOffset;
    }

    @Override
    public List<Entry> getEntries()
    {
        List<Entry> entries = Lists.newArrayList();
        for(int i = 0; i < 3; i++)
        {
            String url = "";
            if(channels.size() > 0 && i >= 0 && i < channels.size())
            {
                url = channels.get(i);
            }
            entries.add(new Entry("channel_" + i, "Channel #" + (i + 1), Entry.Type.TEXT_FIELD, url));
        }
        entries.add(new Entry("stretch", "Stretch to Screen", Entry.Type.TOGGLE, this.stretch));
        entries.add(new Entry("powered", "Powered", Entry.Type.TOGGLE, this.powered));
        return entries;
    }

    @Override
    public void updateEntries(Map<String, String> entries)
    {
        channels.clear();
        for(int i = 0; i < 3; i++)
        {
            String url = entries.get("channel_" + i);
            if(!Strings.isNullOrEmpty(url))
            {
                channels.add(url);
            }
        }
        this.stretch = Boolean.valueOf(entries.get("stretch"));
        this.powered = Boolean.valueOf(entries.get("powered"));
        this.markDirty();
    }

    @Override
    public boolean requiresTool()
    {
        return false;
    }

    public boolean isStretched()
    {
        return stretch;
    }

    @Override
    public BlockPos getContainerPos()
    {
        return this.pos;
    }

    public void setPowered(boolean powered)
    {
        if(!disabled)
        {
            this.powered = powered;
            TileEntityUtil.syncToClient(this);
        }
    }

    public boolean isPowered()
    {
        return powered;
    }

    public boolean nextChannel()
    {
        if(!disabled && powered && channels.size() > 1)
        {
            this.currentChannel++;
            if(this.currentChannel >= channels.size())
            {
                this.currentChannel = 0;
            }
            TileEntityUtil.syncToClient(this);
            return true;
        }
        return false;
    }

    public boolean isDisabled()
    {
        return disabled;
    }
}