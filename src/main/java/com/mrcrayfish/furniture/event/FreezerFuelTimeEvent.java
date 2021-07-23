package com.mrcrayfish.furniture.event;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * Author: MrCrayfish
 */
public class FreezerFuelTimeEvent extends Event
{
    private final ItemStack stack;
    private int fuelTime;

    public FreezerFuelTimeEvent(ItemStack stack)
    {
        this.stack = stack;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public void setFuelTime(int fuelTime)
    {
        this.fuelTime = fuelTime;
    }

    public int getFuelTime()
    {
        return fuelTime;
    }
}
