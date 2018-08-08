package com.mrcrayfish.furniture.client;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Author: MrCrayfish
 */
public class ImageDownloadThread extends Thread
{
    //Prevents GIFs larger than 2MB from loading
    private static final long MAX_FILE_SIZE = 2097152;

    private String url;
    private ResponseProcessor processor;

    public ImageDownloadThread(String url, ResponseProcessor processor)
    {
        super("Image Download Thread");
        this.url = url;
        this.processor = processor;
    }

    @Override
    public void run()
    {
        if(GifCache.INSTANCE.loadCached(url))
        {
            processor.process(ImageDownloadResult.SUCCESS, "Successfully processed GIF");
            return;
        }

        try
        {
            URLConnection connection = new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            if(!"image/gif".equals(connection.getContentType()))
            {
                processor.process(ImageDownloadResult.UNKNOWN_FILE, "The file is not a GIF");
                return;
            }

            long length = Long.parseLong(connection.getHeaderField("Content-Length"));
            if(length > MAX_FILE_SIZE)
            {
                processor.process(ImageDownloadResult.TOO_LARGE, "The GIF is greater than " + MAX_FILE_SIZE / 1024.0 + "MB");
                return;
            }

            byte[] data = IOUtils.toByteArray(connection);
            if(GifCache.INSTANCE.add(url, data))
            {
                processor.process(ImageDownloadResult.SUCCESS, "Successfully processed GIF");
                return;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        processor.process(ImageDownloadResult.FAILED, "Unable to process GIF");
    }

    public interface ResponseProcessor
    {
        void process(ImageDownloadResult result, String message);
    }

    public enum ImageDownloadResult
    {
        SUCCESS, FAILED, UNKNOWN_FILE, TOO_LARGE;
    }
}
