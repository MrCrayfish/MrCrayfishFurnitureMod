package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.gui.inventory.InventoryPackage;
import com.mrcrayfish.furniture.items.IMail;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPackage extends Slot
{
    InventoryPackage inventoryPac;

    public SlotPackage(IInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
        inventoryPac = (InventoryPackage) inventory;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return !InventoryPackage.isSigned() && !(par1ItemStack != null && par1ItemStack.getItem() instanceof IMail);
    }

}
