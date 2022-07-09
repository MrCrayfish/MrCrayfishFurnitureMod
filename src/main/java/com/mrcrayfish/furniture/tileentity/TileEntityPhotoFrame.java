package com.mrcrayfish.furniture.tileentity;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.client.DownloadUtils;
import com.mrcrayfish.furniture.client.ImageCache;
import com.mrcrayfish.furniture.client.ImageDownloadThread;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class TileEntityPhotoFrame extends TileEntitySyncClient implements IValueContainer
{
    private int colour = 0;
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

    // Client only
    public void loadUrl(String url)
    {
        if(loading)
            return;

        URI uri = DownloadUtils.createUri(url);
        if(uri == null)
        {
            this.result = ImageDownloadThread.ImageDownloadResult.INVALID_URL;
            this.loaded = true;
            return;
        }

        if(!DownloadUtils.isValidScheme(uri))
        {
            this.result = ImageDownloadThread.ImageDownloadResult.WRONG_SCHEME;
            this.loaded = true;
            return;
        }

        if(!DownloadUtils.isValidType(uri, "png", "jpg", "jpeg"))
        {
            this.result = ImageDownloadThread.ImageDownloadResult.UNSUPPORTED_FILE_TYPE;
            this.loaded = true;
            return;
        }

        if(!DownloadUtils.isTrustedDomain(uri))
        {
            this.result = ImageDownloadThread.ImageDownloadResult.UNTRUSTED;
            this.loaded = true;
            return;
        }

        this.loaded = false;
        this.result = null;
        if(!ImageCache.INSTANCE.loadCached(url))
        {
            this.loading = true;
            new ImageDownloadThread(uri, (result, message) ->
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

    // Client only
    public boolean isLoading()
    {
        return loading;
    }

    // Client only
    public boolean isLoaded()
    {
        return loaded && !loading;
    }

    @Nullable
    public ImageDownloadThread.ImageDownloadResult getResult()
    {
        return result;
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
    public String updateEntries(Map<String, String> entries, EntityPlayer player)
    {
        this.url = entries.get("photo");
        this.stretch = Boolean.valueOf(entries.get("stretch"));
        this.markDirty();
        return null;
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
