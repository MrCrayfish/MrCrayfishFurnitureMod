package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMicrowave extends Slot
{
    private TileEntityMicrowave microwave;

    public SlotMicrowave(TileEntityMicrowave microwave, int id, int x, int y)
    {
        super(microwave, id, x, y);
        this.microwave = microwave;
    }

    @Override
    public boolean isItemValid(ItemStack item)
    {
        return !microwave.isCooking();
    }

    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        return !microwave.isCooking();
    }
}