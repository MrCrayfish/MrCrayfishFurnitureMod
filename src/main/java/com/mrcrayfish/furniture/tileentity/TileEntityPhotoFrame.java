package com.mrcrayfish.furniture.tileentity;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.client.ImageCache;
import com.mrcrayfish.furniture.client.ImageDownloadThread;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: MrCrayfish
 */
public class TileEntityPhotoFrame extends TileEntitySyncClient implements IValueContainer
{
    protected static final ExecutorService THREAD_SERVICE = Executors.newFixedThreadPool(4, r -> {
        Thread thread = new Thread(r);
        thread.setName("Image Download Thread");
        return thread;
    });

    private int colour = 0;
    private String url;
    private boolean stretch;
    private boolean disabled;

    @SideOnly(Side.CLIENT)
    private boolean cached;
    @SideOnly(Side.CLIENT)
    private Future<ImageDownloadThread.ImageDownloadResult> result;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if(this.url != null)
        {
            compound.setString("Photo", this.url);
        }
        compound.setBoolean("Stretch", this.stretch);
        compound.setBoolean("DisableInteraction", this.disabled);
        compound.setInteger("Color", colour);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("Photo", Constants.NBT.TAG_STRING))
        {
            this.url = compound.getString("Photo");
        }
        if(compound.hasKey("Stretch", Constants.NBT.TAG_BYTE))
        {
            this.stretch = compound.getBoolean("Stretch");
        }
        if(compound.hasKey("DisableInteraction", Constants.NBT.TAG_BYTE))
        {
            this.disabled = compound.getBoolean("DisableInteraction");
        }
        if(compound.hasKey("Color", Constants.NBT.TAG_INT))
        {
            this.colour = compound.getInteger("Color");
        }
        if(world != null && world.isRemote && url != null)
        {
            this.loadUrl(url);
        }
    }

    @Nullable
    public String getPhoto()
    {
        return url;
    }

    @SideOnly(Side.CLIENT)
    public void loadUrl(String url)
    {
        if(isLoading())
            return;

        this.cached = false;
        this.result = null;
        if(!ImageCache.INSTANCE.loadCached(url, false))
        {
            this.result = THREAD_SERVICE.submit(new ImageDownloadThread(url));
        }
        else
        {
            this.cached = true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onChunkUnload()
    {
        ImageCache.INSTANCE.delete(url);
    }

    @SideOnly(Side.CLIENT)
    public boolean isLoading()
    {
        return result != null && !result.isDone();
    }

    @SideOnly(Side.CLIENT)
    public boolean isLoaded()
    {
        return cached || (result != null && result.isDone());
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public ImageDownloadThread.ImageDownloadResult getResult()
    {
        try
        {
            if (result != null && result.isDone())
                return result.get();
        }
        catch(Exception ex)
        {
            return ImageDownloadThread.ImageDownloadResult.FAILED;
        }

        return null;
    }

    @Override
    public List<IValueContainer.Entry> getEntries()
    {
        List<IValueContainer.Entry> entries = Lists.newArrayList();
        entries.add(new IValueContainer.Entry("photo", "Photo URL", Entry.Type.TEXT_FIELD, this.url));
        entries.add(new IValueContainer.Entry("stretch", "Stretch to Border", Entry.Type.TOGGLE, this.stretch));
        return entries;
    }

    @Override
    public void updateEntries(Map<String, String> entries)
    {
        String photo = entries.get("photo");
        if (url != null && !url.equals(photo))
            ImageCache.INSTANCE.delete(getPhoto());
        this.url = photo;
        this.stretch = Boolean.valueOf(entries.get("stretch"));
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

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setColour(int colour)
    {
        this.colour = 15 - colour;
    }

    public int getColour()
    {
        return colour;
    }
}
