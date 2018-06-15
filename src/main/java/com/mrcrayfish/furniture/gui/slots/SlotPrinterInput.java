package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.api.RecipeAPI;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPrinterInput extends Slot
{
    public SlotPrinterInput(IInventory inventory, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return RecipeAPI.getPrinterRecipeFromInput(new ItemStack(stack.getItem())) != null;
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}