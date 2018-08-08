package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.items.IMail;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMail extends Slot
{
    public SlotMail(IInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return !(par1ItemStack != null && par1ItemStack.getItem() instanceof IMail);
    }
}