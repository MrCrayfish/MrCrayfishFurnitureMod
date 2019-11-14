package com.mrcrayfish.furniture.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

/**
 * Author: MrCrayfish
 */
public class ItemStackHelper
{
    public static CompoundNBT saveAllItems(String key, CompoundNBT tag, NonNullList<ItemStack> list)
    {
        return saveAllItems(key, tag, list, true);
    }

    public static CompoundNBT saveAllItems(String key, CompoundNBT tag, NonNullList<ItemStack> list, boolean saveEmpty)
    {
        ListNBT listTag = new ListNBT();
        for(int i = 0; i < list.size(); ++i)
        {
            ItemStack stack = list.get(i);
            if(!stack.isEmpty())
            {
                CompoundNBT itemCompound = new CompoundNBT();
                itemCompound.putByte("Slot", (byte) i);
                stack.write(itemCompound);
                listTag.add(itemCompound);
            }
        }
        if(!listTag.isEmpty() || saveEmpty)
        {
            tag.put(key, listTag);
        }
        return tag;
    }

    public static void loadAllItems(String key, CompoundNBT tag, NonNullList<ItemStack> list)
    {
        ListNBT listTag = tag.getList(key, Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < listTag.size(); i++)
        {
            CompoundNBT slotCompound = listTag.getCompound(i);
            int j = slotCompound.getByte("Slot") & 255;
            if(j < list.size())
            {
                list.set(j, ItemStack.read(slotCompound));
            }
        }
    }
}
