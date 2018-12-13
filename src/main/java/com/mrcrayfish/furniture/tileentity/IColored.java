package com.mrcrayfish.furniture.tileentity;

import net.minecraft.item.EnumDyeColor;

/**
 * Author: MrCrayfish
 */
public interface IColored
{
    EnumDyeColor getColor();

    void setColor(EnumDyeColor color);
}