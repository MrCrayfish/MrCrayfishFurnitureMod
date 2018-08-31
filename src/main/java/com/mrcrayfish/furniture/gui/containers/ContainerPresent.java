package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.items.IMail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPresent extends Container
{
    private IInventory presentInventory;

    public ContainerPresent(IInventory playerInventory, IInventory presentInventory)
    {
        this.presentInventory = presentInventory;
        presentInventory.openInventory(null);

        this.addSlotToContainer(new Slot(presentInventory, 0, 8 + 63, 16));
        this.addSlotToContainer(new Slot(presentInventory, 1, 8 + 81, 16));
        this.addSlotToContainer(new Slot(presentInventory, 2, 8 + 63, 34));
        this.addSlotToContainer(new Slot(presentInventory, 3, 8 + 81, 34));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 84));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.presentInventory.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack itemCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotNum);

        if(slot != null && slot.getHasStack())
        {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            if(item.getItem() instanceof IMail)
            {
                return ItemStack.EMPTY;
            }

            if(slotNum < 4)
            {
                if(!this.mergeItemStack(item, 4, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(item, 0, 4, false))
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

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        presentInventory.closeInventory(player);
    }
}
