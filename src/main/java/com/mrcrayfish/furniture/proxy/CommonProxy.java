package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.client.MailBoxEntry;
import com.mrcrayfish.furniture.network.PacketHandler;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

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

    public void updateMailBoxes(CompoundNBT nbt) {}
}
