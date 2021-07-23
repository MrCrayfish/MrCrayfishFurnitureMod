package com.mrcrayfish.furniture.inventory.container.slot;

import com.mrcrayfish.furniture.inventory.container.FreezerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class FreezerFuelSlot extends Slot
{
    private final FreezerMenu container;

    public FreezerFuelSlot(FreezerMenu menu, Container container, int index, int x, int y)
    {
        super(container, index, x, y);
        this.container = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return this.container.isFuel(stack);
    }
}
