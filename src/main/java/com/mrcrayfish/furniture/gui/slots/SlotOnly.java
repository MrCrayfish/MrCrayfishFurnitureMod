package com.mrcrayfish.furniture.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotOnly extends Slot
{
    private Item item;

    public SlotOnly(IInventory inventory, int id, int x, int y, Item item)
    {
        super(inventory, id, x, y);
        this.item = item;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() == item;
    }

}
