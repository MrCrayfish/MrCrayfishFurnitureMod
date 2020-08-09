package com.mrcrayfish.furniture.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class GifDownloadThread implements Callable<GifDownloadThread.ImageDownloadResult>
{
    private static final Set<String> LOADING_URLS = new HashSet<>();
    private static final Map<Pattern, String> MAP_OF_GIF_HOSTS = Stream.of(
        new AbstractMap.SimpleEntry<>(
            Pattern.compile("https:\\/\\/giphy\\.com\\/gifs\\/(?:.*)-(.*)"),
            "https://i.giphy.com/media/%s/giphy.gif"),
        new AbstractMap.SimpleEntry<>(
            Pattern.compile("https:\\/\\/(?:i|media)\\.giphy\\.com\\/media\\/(.*)\\/giphy\\.gif"),
            "https://i.giphy.com/media/%s/giphy.gif")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    //Prevents GIFs larger than 2MB from loading
    private static final long MAX_FILE_SIZE = 2097152;

    private String url;
    private int tryCount;

    public GifDownloadThread(String url)
    {
        this.url = url;
    }

    @Override
    public ImageDownloadResult call()
    {
        if(GifCache.INSTANCE.loadCached(url))
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

                if(GifCache.INSTANCE.isCached(url))
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
            String asset_url = sanatizeUrl(url);
            URLConnection connection = new URL(asset_url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            if(!"image/gif".equals(connection.getContentType()))
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
            if(GifCache.INSTANCE.add(url, data))
            {
                setLoading(url, false);
                return ImageDownloadResult.SUCCESS;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        setLoading(url, false);
        return ImageDownloadResult.FAILED;
    }

    /**
     * Takes a URL and sees if it matches one of the known GIF hosts and if it
     * can turn it into a direct link to the GIF.
     */
    private String sanatizeUrl(String url)
    {
        for(Map.Entry<Pattern, String> host : MAP_OF_GIF_HOSTS.entrySet())
        {
        	try
    		{
	            Matcher matcher = host.getKey().matcher(url);
	            if (matcher.find())
	            {
	            	return String.format(host.getValue(), matcher.group(1));
	            }
        	}
        	catch(Exception e) {}
        }

        return url;
    }

    public enum ImageDownloadResult
    {
        SUCCESS("cfm.tv.success"),
        FAILED("cfm.tv.failed"),
        UNKNOWN_FILE("cfm.tv.unknown_file"),
        TOO_LARGE("cfm.tv.too_large");

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
