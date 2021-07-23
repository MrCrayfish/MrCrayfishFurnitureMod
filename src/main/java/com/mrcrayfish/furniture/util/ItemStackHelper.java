package com.mrcrayfish.furniture.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Constants;

//import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class ItemStackHelper
{
    public static CompoundTag saveAllItems(String key, CompoundTag tag, NonNullList<ItemStack> list)
    {
        return saveAllItems(key, tag, list, true);
    }

    public static CompoundTag saveAllItems(String key, CompoundTag tag, NonNullList<ItemStack> list, boolean saveEmpty)
    {
        ListTag listTag = new ListTag();
        for(int i = 0; i < list.size(); ++i)
        {
            ItemStack stack = list.get(i);
            if(!stack.isEmpty())
            {
                CompoundTag itemCompound = new CompoundTag();
                itemCompound.putByte("Slot", (byte) i);
                stack.save(itemCompound);
                listTag.add(itemCompound);
            }
        }
        if(!listTag.isEmpty() || saveEmpty)
        {
            tag.put(key, listTag);
        }
        return tag;
    }

    public static void loadAllItems(String key, CompoundTag tag, NonNullList<ItemStack> list)
    {
        ListTag listTag = tag.getList(key, Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < listTag.size(); i++)
        {
            CompoundTag slotCompound = listTag.getCompound(i);
            int j = slotCompound.getByte("Slot") & 255;
            if(j < list.size())
            {
                list.set(j, ItemStack.of(slotCompound));
            }
        }
    }
}
