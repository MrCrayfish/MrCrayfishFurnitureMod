package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.gui.slots.SlotDisabled;
import com.mrcrayfish.furniture.gui.slots.SlotNonInventory;
import com.mrcrayfish.furniture.items.IItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPresent extends ContainerItemInventory
{
    private ItemInventory itemInventory;

    public ContainerPresent(IInventory playerInventory, ItemInventory itemInventory)
    {
        this.itemInventory = itemInventory;

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 84));
            }
        }

        int currentItemIndex = ((InventoryPlayer) playerInventory).currentItem;
        for(int i = 0; i < 9; i++)
        {
            if(i == currentItemIndex)
            {
                this.addSlotToContainer(new SlotDisabled(playerInventory, i, i * 18 + 8, 142));
            }
            else
            {
                this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 142));
            }
        }

        this.addSlotToContainer(new SlotNonInventory(itemInventory, 0, 8 + 63, 16));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 1, 8 + 81, 16));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 2, 8 + 63, 34));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 3, 8 + 81, 34));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.itemInventory.isUsableByPlayer(player);
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

            if(item.getItem() instanceof IItemInventory)
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
    public ItemInventory getItemInventory()
    {
        return itemInventory;
    }
}