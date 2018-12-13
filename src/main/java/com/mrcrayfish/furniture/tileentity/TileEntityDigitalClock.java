package com.mrcrayfish.furniture.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

/**
 * Author: MrCrayfish
 */
public class TileEntityDigitalClock extends TileEntityColored
{
    private EnumDyeColor textColor = EnumDyeColor.WHITE;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("textColor", Constants.NBT.TAG_BYTE))
        {
            this.textColor = EnumDyeColor.byMetadata(compound.getByte("textColor"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("textColor", (byte) textColor.getMetadata());
        return compound;
    }

    public EnumDyeColor getTextColor()
    {
        return textColor;
    }

    public void setTextColorColor(EnumDyeColor color)
    {
        this.textColor = color;
        this.sync();
    }
}