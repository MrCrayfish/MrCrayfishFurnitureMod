package com.mrcrayfish.furniture.tileentity;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.client.ImageCache;
import com.mrcrayfish.furniture.client.ImageDownloadThread;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class TileEntityPhotoFrame extends TileEntitySyncClient implements IValueContainer
{
    private String url;
    private boolean stretch;
    private boolean disabled;

    @SideOnly(Side.CLIENT)
    private boolean loading;
    @SideOnly(Side.CLIENT)
    private boolean loaded;
    @SideOnly(Side.CLIENT)
    private ImageDownloadThread.ImageDownloadResult result;

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
        if(loading)
            return;

        this.loaded = false;
        this.result = null;
        if(!ImageCache.INSTANCE.loadCached(url))
        {
            this.loading = true;
            new ImageDownloadThread(url, (result, message) ->
            {
                this.loading = false;
                this.result = result;
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
        return loading;
    }

    @SideOnly(Side.CLIENT)
    public boolean isLoaded()
    {
        return loaded && !loading;
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public ImageDownloadThread.ImageDownloadResult getResult()
    {
        return result;
    }

    @Override
    public List<IValueContainer.Entry> getEntries()
    {
        List<IValueContainer.Entry> entries = Lists.newArrayList();
        entries.add(new IValueContainer.Entry("photo", "Photo URL", Entry.Type.TEXT_FIELD, this.url));
        entries.add(new IValueContainer.Entry("stretch", "Stretch to Screen", Entry.Type.TOGGLE, this.stretch));
        return entries;
    }

    @Override
    public void updateEntries(Map<String, String> entries)
    {
        this.url = entries.get("photo");
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

    @Override
    public BlockPos getContainerPos()
    {
        return this.pos;
    }

    public boolean isDisabled()
    {
        return disabled;
    }
}
