package com.mrcrayfish.furniture.tileentity;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.client.GifCache;
import com.mrcrayfish.furniture.client.ImageDownloadThread;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public abstract class TileEntityAbstractTV extends TileEntitySyncClient implements IValueContainer
{
    private int width;
    private int height;
    private boolean stretch;

    private String url;
    private boolean loading;
    private boolean loaded;

    public TileEntityAbstractTV() {}

    public TileEntityAbstractTV(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if(!Strings.isNullOrEmpty(this.url))
        {
            compound.setString("URL", this.url);
        }
        compound.setBoolean("Stretch", this.stretch);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("URL", Constants.NBT.TAG_STRING))
        {
            this.url = compound.getString("URL");
            if(world != null && world.isRemote)
            {
                this.loadUrl(url);
            }
        }
        if(compound.hasKey("Stretch", Constants.NBT.TAG_BYTE))
        {
            this.stretch = compound.getBoolean("Stretch");
        }
    }

    public String getUrl()
    {
        return url;
    }

    @SideOnly(Side.CLIENT)
    public void loadUrl(String url)
    {
        this.loaded = false;
        if(!GifCache.INSTANCE.loadCached(url))
        {
            this.loading = true;
            new ImageDownloadThread(url, (result, message) ->
            {
                this.loading = false;
                if(result == ImageDownloadThread.ImageDownloadResult.SUCCESS)
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
        return url != null && loading;
    }

    @SideOnly(Side.CLIENT)
    public boolean isLoaded()
    {
        return url != null && loaded && !loading;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public List<Entry> getEntries()
    {
        List<Entry> entries = Lists.newArrayList();
        entries.add(new Entry("url", "URL", Entry.Type.TEXT_FIELD, this.url));
        entries.add(new Entry("stretch", "Stretch to Screen", Entry.Type.TOGGLE, this.stretch));
        return entries;
    }

    @Override
    public void updateEntries(Map<String, String> entries)
    {
        this.url = entries.get("url");
        this.stretch = Boolean.valueOf(entries.get("stretch"));
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
}
