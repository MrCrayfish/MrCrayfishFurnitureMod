package com.mrcrayfish.furniture.gui.inventory;

import net.minecraft.item.ItemStack;

public class InventoryPackage extends ItemInventory
{
    public InventoryPackage(ItemStack stack)
    {
        super(stack, "Package", 6);
    }
}