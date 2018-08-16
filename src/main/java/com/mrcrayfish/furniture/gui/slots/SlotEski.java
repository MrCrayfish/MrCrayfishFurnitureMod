package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class SlotEski extends Slot
{
    public SlotEski(IInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemPotion || stack.getItem() == FurnitureItems.DRINK || stack.getItem() == Items.MILK_BUCKET || stack.getItem() == Items.WATER_BUCKET;
    }

}
