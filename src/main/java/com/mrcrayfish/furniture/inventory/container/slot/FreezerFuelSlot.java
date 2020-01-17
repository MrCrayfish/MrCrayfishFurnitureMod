package com.mrcrayfish.furniture.inventory.container.slot;

import com.mrcrayfish.furniture.inventory.container.FreezerContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class FreezerFuelSlot extends Slot
{
    private final FreezerContainer container;

    public FreezerFuelSlot(FreezerContainer container, IInventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
        this.container = container;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return this.container.isFuel(stack);
    }
}
