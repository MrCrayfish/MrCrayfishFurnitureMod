package com.mrcrayfish.furniture.proxy;

public interface ProxyInterface
{
    default void preInit()
    {
    }

    default void init()
    {
    }

    boolean isSinglePlayer();

    boolean isDedicatedServer();

}