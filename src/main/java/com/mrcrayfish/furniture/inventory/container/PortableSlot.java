package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.block.IPortableInventory;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class PortableSlot extends Slot
{
    public PortableSlot(IInventory inventory, int index, int xPosition, int yPosition)
    {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        Block block = Block.getBlockFromItem(stack.getItem());
        return !(block instanceof IPortableInventory || block instanceof ShulkerBoxBlock);
    }
}
