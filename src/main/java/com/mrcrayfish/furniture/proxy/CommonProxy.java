package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.network.PacketHandler;

/**
 * Author: MrCrayfish
 */
public class CommonProxy
{
    public void onSetupCommon()
    {
        PacketHandler.init();
    }

    public void onSetupClient() {}
}
