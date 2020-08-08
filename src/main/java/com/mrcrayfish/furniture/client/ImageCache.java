package com.mrcrayfish.furniture.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: MrCrayfish
 */
@SideOnly(Side.CLIENT)
public final class ImageCache
{
    public static final ImageCache INSTANCE = new ImageCache();

    private final File cache;
    private Map<String, Texture> cacheMap = new ConcurrentHashMap<>();

    private ImageCache()
    {
        cache = new File(Minecraft.getMinecraft().mcDataDir, "photo-frame-cache");
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
    public Texture get(String url)
    {
        if(url == null)
            return null;

        Texture texture = cacheMap.get(url);
        if(texture != null)
        {
            return texture;
        }
        return null;
    }

    public void add(String url, File file)
    {
        if(!cacheMap.containsKey(url))
        {
            Texture texture = new Texture(file);
            cacheMap.put(url, texture);
        }
    }

    public boolean add(String url, byte[] data)
    {
        try
        {
            if(!cacheMap.containsKey(url))
            {
                String id = DigestUtils.sha1Hex(url.getBytes());
                File image = new File(getCache(), id);
                FileUtils.writeByteArrayToFile(image, data);

                if (convert(image))
                {
                    image.delete();
                    image = new File(getCache(), id + ".dds");
                }

                Texture texture = new Texture(image);
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

    private void tick()
    {
        cacheMap.values().forEach(Texture::update);
        cacheMap.keySet().removeIf(key -> cacheMap.get(key).isPendingDeletion());
    }

    public boolean delete(String url)
    {
        Texture texture = get(url);
        if (texture == null)
            return false;
        texture.delete();
        return true;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START)
        {
            this.tick();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void unloadWorld(WorldEvent.Unload event)
    {
        cacheMap.values().forEach(Texture::delete);
    }

    public boolean loadCached(String url, boolean canConvert)
    {
        if(cacheMap.containsKey(url))
        {
            return true;
        }

        String id = DigestUtils.sha1Hex(url.getBytes());
        File rawFile = new File(getCache(), id);
        File cacheFile = new File(getCache(), id + ".dds");

        // If the raw file is present in the cache try to convert it
        if (canConvert && rawFile.exists())
        {
            // If conversion fails, simply add the raw file to the cache instead
            if (convert(rawFile))
                rawFile.delete();
            else
                cacheFile = rawFile;
        }

        if(cacheFile.exists())
        {
            this.add(url, cacheFile);
            return true;
        }
        return false;
    }

    public boolean isCached(String url)
    {
        return cacheMap.containsKey(url);
    }

    public File getCache()
    {
        cache.mkdir();
        return cache;
    }

    private boolean convert(File file)
    {
        File converter = new File(Minecraft.getMinecraft().mcDataDir, "texconv.exe");
        if (!converter.exists())
            return false;

        ProcessBuilder pb = new ProcessBuilder().inheritIO().command(converter.getAbsolutePath(),
                "-m", "5", // Minecraft supports a maximum of 4 mipmaps, so we need 5 levels
                "-f", "BC7_UNORM", "-hflip", "-vflip", "-l",
                "-o", getCache().getAbsolutePath(),
                file.getAbsolutePath());

        try
        {
            Process compress = pb.start();
            return compress.waitFor() >= 0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
