package com.mrcrayfish.furniture.inventory.container.slot;

import com.mrcrayfish.furniture.block.IPortableInventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/**
 * Author: MrCrayfish
 */
public class PortableSlot extends Slot
{
    public PortableSlot(Container container, int index, int x, int y)
    {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        Block block = Block.byItem(stack.getItem());
        return !(block instanceof IPortableInventory || block instanceof ShulkerBoxBlock);
    }
}
