package com.mrcrayfish.furniture.gui.inventory;

import net.minecraft.item.ItemStack;

public interface ISimpleInventory
{
    int getSize();

    ItemStack getItem(int i);

    void clear();
}
