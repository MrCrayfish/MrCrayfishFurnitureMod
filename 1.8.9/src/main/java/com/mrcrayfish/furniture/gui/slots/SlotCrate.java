package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.init.FurnitureBlocks;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotCrate extends Slot
{
	public SlotCrate(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() != Item.getItemFromBlock(FurnitureBlocks.crate);
	}
}
