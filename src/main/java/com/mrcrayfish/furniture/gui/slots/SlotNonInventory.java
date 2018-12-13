package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.items.IItemInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;

public class SlotNonInventory extends Slot
{
    private ItemInventory inventory;

    public SlotNonInventory(ItemInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
        this.inventory = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return inventory.canInsertItems() && !(stack.getItem() instanceof IItemInventory) && !(stack.getItem() instanceof ItemShulkerBox);
    }
}