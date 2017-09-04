package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.blocks.tv.Channels;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Author: MrCrayfish
 */
public class ClientEvents
{
    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event)
    {
        Channels.SOUND_POSITIONS.clear();
    }
}
