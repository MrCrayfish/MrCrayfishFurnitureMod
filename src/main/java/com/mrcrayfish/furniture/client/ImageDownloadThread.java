package com.mrcrayfish.furniture.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Author: MrCrayfish
 */
public class ImageDownloadThread implements Callable<ImageDownloadThread.ImageDownloadResult>
{
    private static final String[] SUPPORTED_FORMATS = { "image/png", "image/jpeg", "image/gif", "image/bmp" };
    private static final Set<String> LOADING_URLS = new HashSet<>();

    //Prevents GIFs larger than 2MB from loading
    private static final long MAX_FILE_SIZE = 2097152;

    private String url;
    private int tryCount;

    public ImageDownloadThread(String url)
    {
        this.url = url;
    }

    @Override
    public ImageDownloadResult call()
    {
        if(ImageCache.INSTANCE.loadCached(url))
        {
            return ImageDownloadResult.SUCCESS;
        }

        if(isLoading(url))
        {
            while(true)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }

                if(ImageCache.INSTANCE.isCached(url))
                {
                    return ImageDownloadResult.SUCCESS;
                }

                if(tryCount++ == 10)
                {
                    return ImageDownloadResult.FAILED;
                }
            }
        }

        try
        {
            URLConnection connection = new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            boolean failed = true;
            for(String format : SUPPORTED_FORMATS)
            {
                if(format.equals(connection.getContentType()))
                {
                    failed = false;
                    break;
                }
            }
            if(failed)
            {
                return ImageDownloadResult.UNKNOWN_FILE;
            }

            long length = Long.parseLong(connection.getHeaderField("Content-Length"));
            if(length > MAX_FILE_SIZE)
            {
                return ImageDownloadResult.TOO_LARGE;
            }

            setLoading(url, true);
            byte[] data = IOUtils.toByteArray(connection);
            if(ImageCache.INSTANCE.add(url, data))
            {
                setLoading(url, false);
                return ImageDownloadResult.SUCCESS;
            }
        }
        catch(IOException ignored) {}
        setLoading(url, false);
        return ImageDownloadResult.FAILED;
    }

    public enum ImageDownloadResult
    {
        SUCCESS("cfm.photo_frame.success"),
        FAILED("cfm.photo_frame.failed"),
        UNKNOWN_FILE("cfm.photo_frame.unknown_file"),
        TOO_LARGE("cfm.photo_frame.too_large");

        private String key;

        ImageDownloadResult(String key)
        {
            this.key = key;
        }

        public String getKey()
        {
            return key;
        }
    }

    public static void setLoading(String url, boolean loading)
    {
        synchronized(LOADING_URLS)
        {
            if(loading)
            {
                LOADING_URLS.add(url);
            }
            else
            {
                LOADING_URLS.remove(url);
            }
        }
    }

    public static boolean isLoading(String url)
    {
        synchronized(LOADING_URLS)
        {
           return LOADING_URLS.contains(url);
        }
    }
}
