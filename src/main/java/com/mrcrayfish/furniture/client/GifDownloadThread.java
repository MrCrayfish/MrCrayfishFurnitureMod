package com.mrcrayfish.furniture.client;

import com.google.common.primitives.Longs;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class GifDownloadThread extends Thread
{
    private static final Set<String> LOADING_URLS = new HashSet<>();

    //Prevents GIFs larger than 2MB from loading
    private static final long MAX_FILE_SIZE = 2097152;

    private URI uri;
    private ResponseProcessor processor;
    private int tryCount;

    public GifDownloadThread(URI uri, ResponseProcessor processor)
    {
        super("Image Download Thread");
        this.uri = uri;
        this.processor = processor;
    }

    @Override
    public void run()
    {
        String url = uri.toString();

        if(GifCache.INSTANCE.loadCached(url))
        {
            processor.process(ImageDownloadResult.SUCCESS, "Successfully processed GIF");
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

                if(GifCache.INSTANCE.isCached(url))
                {
                    processor.process(ImageDownloadResult.SUCCESS, "Successfully processed GIF");
                    return;
                }

                if(tryCount++ == 10)
                {
                    processor.process(ImageDownloadResult.FAILED, "Unable to process GIF");
                    return;
                }
            }
        }

        try
        {
            URLConnection connection = uri.toURL().openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            if(!"image/gif".equals(connection.getContentType()))
            {
                processor.process(ImageDownloadResult.UNKNOWN_FILE, "The file is not a GIF");
                return;
            }

            String lengthString = connection.getHeaderField("Content-Length");
            if(lengthString == null)
            {
                processor.process(ImageDownloadResult.UNKNOWN_SIZE, "Unable to determine size of GIF image");
                return;
            }

            Long length = Longs.tryParse(lengthString);
            if(length == null)
            {
                processor.process(ImageDownloadResult.UNKNOWN_SIZE, "Unable to determine size of GIF image");
                return;
            }

            if(length > MAX_FILE_SIZE)
            {
                processor.process(ImageDownloadResult.TOO_LARGE, "The GIF is greater than " + MAX_FILE_SIZE / 1024.0 + "MB");
                return;
            }

            setLoading(url, true);
            byte[] data = IOUtils.toByteArray(connection);
            if(GifCache.INSTANCE.add(url, data))
            {
                setLoading(url, false);
                processor.process(ImageDownloadResult.SUCCESS, "Successfully processed GIF");
                return;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        processor.process(ImageDownloadResult.FAILED, "Unable to process GIF");
        setLoading(url, false);
    }

    public interface ResponseProcessor
    {
        void process(ImageDownloadResult result, String message);
    }

    public enum ImageDownloadResult
    {
        SUCCESS("cfm.tv.success"),
        FAILED("cfm.tv.failed"),
        UNKNOWN_FILE("cfm.tv.unknown_file"),
        UNKNOWN_SIZE("cfm.tv.unknown_size"),
        TOO_LARGE("cfm.tv.too_large"),
        UNTRUSTED("cfm.tv.untrusted"),
        INVALID_URL("cfm.tv.invalid"),
        WRONG_SCHEME("cfm.tv.wrong_scheme"),
        UNSUPPORTED_FILE_TYPE("cfm.tv.unsupported_type");

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
