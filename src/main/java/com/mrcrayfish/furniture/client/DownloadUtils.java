package com.mrcrayfish.furniture.client;

import com.mrcrayfish.furniture.handler.ConfigurationHandler;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: MrCrayfish
 */
public class DownloadUtils
{
    @Nullable
    public static URI createUri(String url)
    {
        if(url == null || url.trim().isEmpty())
        {
            return null;
        }
        try
        {
            return new URI(url);
        }
        catch(URISyntaxException e)
        {
            return null;
        }
    }

    public static boolean isValidScheme(URI uri)
    {
        String scheme = uri.getScheme();
        // Only allow http or https
        return scheme != null && (scheme.equals("http") || scheme.equals("https"));
    }

    public static boolean isValidType(URI uri, String ... types)
    {
        // Check if the path ends with the specified file type
        if(uri.getPath() == null)
        {
            return false;
        }

        boolean supported = false;
        for(String type : types)
        {
            if(uri.getPath().endsWith("." + type))
            {
                supported = true;
                break;
            }
        }
        return supported;
    }

    public static boolean isTrustedDomain(URI uri)
    {
        // Validate the domain name
        String domain = uri.getHost();
        if(domain == null)
        {
            return false;
        }
        if(domain.startsWith("www."))
        {
            domain = domain.substring(4);
        }
        boolean trusted = false;
        for(String trustedDomain : ConfigurationHandler.trustedUrlDomains)
        {
            if(domain.equals(trustedDomain))
            {
                trusted = true;
                break;
            }
        }
        return trusted;
    }
}
