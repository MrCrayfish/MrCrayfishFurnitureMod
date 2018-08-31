package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.items.IMail;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;

public class SlotEnvelope extends Slot
{
    InventoryEnvelope inventoryEnv;

    public SlotEnvelope(IInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
        inventoryEnv = (InventoryEnvelope) inventory;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return !InventoryEnvelope.isSigned() && !(!par1ItemStack.isEmpty() && par1ItemStack.getItem() instanceof IMail || par1ItemStack.getItem() instanceof ItemShulkerBox);
    }

}
