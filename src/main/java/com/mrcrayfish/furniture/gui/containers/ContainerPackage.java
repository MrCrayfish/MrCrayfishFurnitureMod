package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.gui.slots.SlotDisabled;
import com.mrcrayfish.furniture.gui.slots.SlotNonInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPackage extends ContainerItemInventory
{
    private ItemInventory itemInventory;

    public ContainerPackage(IInventory playerInventory, ItemInventory itemInventory)
    {
        this.itemInventory = itemInventory;

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

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 84));
            }
        }

        this.addSlotToContainer(new SlotNonInventory(itemInventory, 0, 8 + 54, 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 1, 8 + 54, 18 + 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 2, 8 + 18 + 54, 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 3, 8 + 18 + 54, 18 + 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 4, 8 + 2 * 18 + 54, 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 5, 8 + 2 * 18 + 54, 18 + 18 - 3));
    }

    @Override
    public ItemInventory getItemInventory()
    {
        return itemInventory;
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

        if(slot != null && slot.getHasStack() && slot instanceof SlotDisabled)
        {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            if(slotNum < 6)
            {
                if(!this.mergeItemStack(item, 6, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(item, 0, 6, false))
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
