package com.mrcrayfish.furniture.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ImageDownloadThread extends Thread
{
    private static final String[] SUPPORTED_FORMATS = { "image/png", "image/jpeg", "image/gif", "image/bmp" };
    private static final Set<String> LOADING_URLS = new HashSet<>();

    //Prevents GIFs larger than 2MB from loading
    private static final long MAX_FILE_SIZE = 2097152;

    private String url;
    private ResponseProcessor processor;
    private int tryCount;

    public ImageDownloadThread(String url, ResponseProcessor processor)
    {
        super("Image Download Thread");
        this.url = url;
        this.processor = processor;
    }

    @Override
    public void run()
    {
        if(ImageCache.INSTANCE.loadCached(url))
        {
            processor.process(ImageDownloadResult.SUCCESS, "Successfully processed image");
            return;
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
                    processor.process(ImageDownloadResult.SUCCESS, "Successfully processed image");
                    return;
                }

                if(tryCount++ == 10)
                {
                    processor.process(ImageDownloadResult.FAILED, "Unable to process image");
                    return;
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
                processor.process(ImageDownloadResult.UNKNOWN_FILE, "The file is not a image");
                return;
            }

            long length = Long.parseLong(connection.getHeaderField("Content-Length"));
            if(length > MAX_FILE_SIZE)
            {
                processor.process(ImageDownloadResult.TOO_LARGE, "The image is greater than " + MAX_FILE_SIZE / 1024.0 + "MB");
                return;
            }

            setLoading(url, true);
            byte[] data = IOUtils.toByteArray(connection);
            if(ImageCache.INSTANCE.add(url, data))
            {
                setLoading(url, false);
                processor.process(ImageDownloadResult.SUCCESS, "Successfully processed image");
                return;
            }
        }
        catch(IOException ignored) {}
        processor.process(ImageDownloadResult.FAILED, "Unable to process image");
        setLoading(url, false);
    }

    public interface ResponseProcessor
    {
        void process(ImageDownloadResult result, String message);
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
