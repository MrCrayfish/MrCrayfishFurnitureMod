package com.mrcrayfish.furniture.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
@SideOnly(Side.CLIENT)
public final class GifCache
{
    public static final GifCache INSTANCE = new GifCache();

    private final File cache;
    private Map<String, AnimatedTexture> cacheMap = new HashMap<>();

    private GifCache()
    {
        cache = new File(Minecraft.getMinecraft().mcDataDir, "tv-cache");
        cache.mkdir();
        this.init();
    }

    private void init()
    {
        try
        {
            FileUtils.writeStringToFile(new File(cache, "!read-me.txt"), "This is a cache for GIFs that are played on the TV (in MrCrayfish's Furniture Mod) in order to speed up load time.\nIt is safe to delete the entire folder in case you are running out of space, however it will mean that all GIFs will have to be downloaded again.", "UTF-8");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Nullable
    public AnimatedTexture get(String url)
    {
        if(url == null)
            return null;

        synchronized(this)
        {
            AnimatedTexture texture = cacheMap.get(url);
            if(texture != null)
            {
                return texture;
            }
        }
        return null;
    }

    public void add(String url, File file)
    {
        synchronized(this)
        {
            if(!cacheMap.containsKey(url))
            {
                AnimatedTexture texture = new AnimatedTexture(file);
                cacheMap.put(url, texture);
            }
        }
    }

    public boolean add(String url, byte[] data)
    {
        synchronized(this)
        {
            try
            {
                if(!cacheMap.containsKey(url))
                {
                    String id = DigestUtils.sha1Hex(url.getBytes()) + ".gif";
                    File gif = new File(getCache(), id);
                    FileUtils.writeByteArrayToFile(gif, data);
                    AnimatedTexture texture = new AnimatedTexture(gif);
                    cacheMap.put(url, texture);
                }
                return true;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return false;
        }
    }

    public void load(String url, GifDownloadThread.ResponseProcessor processor)
    {
        new GifDownloadThread(url, processor);
    }

    private void tick()
    {
        cacheMap.values().forEach(AnimatedTexture::update);
        cacheMap.keySet().removeIf(key -> cacheMap.get(key).isPendingDeletion());
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START)
        {
            this.tick();
        }
    }

    public boolean loadCached(String url)
    {
        synchronized(this)
        {
            if(cacheMap.containsKey(url))
            {
                return true;
            }
        }

        String id = DigestUtils.sha1Hex(url.getBytes()) + ".gif";
        File file = new File(getCache(), id);
        if(file.exists())
        {
            this.add(url, file);
            return true;
        }
        return false;
    }

    public boolean isCached(String url)
    {
        synchronized(this)
        {
            return cacheMap.containsKey(url);
        }
    }

    public File getCache()
    {
        cache.mkdir();
        return cache;
    }
}