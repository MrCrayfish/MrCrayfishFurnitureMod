package com.mrcrayfish.furniture.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerComputer extends Container
{
    private IInventory computerInventory;

    public ContainerComputer(IInventory playerInventory, IInventory computerInventory)
    {
        this.computerInventory = computerInventory;
        computerInventory.openInventory(null);

        this.addSlotToContainer(new Slot(computerInventory, 0, 119, 40));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 95));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 153));
        }
    }

    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.computerInventory.isUsableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotNum)
    {
        ItemStack itemCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotNum);

        if(slot != null && slot.getHasStack())
        {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            if(slotNum == 0)
            {
                if(!this.mergeItemStack(item, 1, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(item, 0, 1, false))
            {
                return ItemStack.EMPTY;
            }

            if(item.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemCopy;
    }

}
