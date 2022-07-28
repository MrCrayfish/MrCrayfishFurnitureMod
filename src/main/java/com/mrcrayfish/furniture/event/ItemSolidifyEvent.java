package com.mrcrayfish.furniture.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * Author: MrCrayfish
 */
public class ItemSolidifyEvent extends Event
{
    private final Player player;
    private final ItemStack stack;

    public ItemSolidifyEvent(Player player, ItemStack stack)
    {
        this.player = player;
        this.stack = stack;
    }

    public Player getPlayer()
    {
        return player;
    }

    public ItemStack getStack()
    {
        return this.stack;
    }
}
