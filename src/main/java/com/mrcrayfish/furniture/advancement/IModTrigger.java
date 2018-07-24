package com.mrcrayfish.furniture.advancement;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Allows a custom trigger to be triggered from {@link Triggers#trigger(IModTrigger, net.minecraft.entity.player.EntityPlayer)}.
 *
 * @author Ocelot5836
 */
public interface IModTrigger
{
    /**
     * Triggers the advancement trigger.
     *
     * @param player The player triggering it
     */
    void trigger(EntityPlayerMP player);
}
