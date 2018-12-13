package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotSoapyWater extends Slot
{

    public SlotSoapyWater(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        if(par1ItemStack == null)
        {
            return false;
        }

        Item item = par1ItemStack.getItem();
        return item == FurnitureItems.SOAPY_WATER || item == FurnitureItems.SUPER_SOAPY_WATER;
    }

    @Override
    public String getSlotTexture()
    {
        return Reference.MOD_ID + ":items/outline_bucket";
    }
}