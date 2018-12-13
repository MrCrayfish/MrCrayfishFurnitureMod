package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.slots.SlotEski;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class ContainerEski extends Container
{
    private IInventory inventory;

    public ContainerEski(IInventory playerInventory, IInventory inventory)
    {
        this.inventory = inventory;

        for(int y = 0; y < 2; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                this.addSlotToContainer(new SlotEski(inventory, x + y * 4, x * 18 + 53, y * 18 + 12));
            }
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 85));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 143));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.inventory.isUsableByPlayer(par1EntityPlayer);
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

            if(!(item.getItem() instanceof ItemPotion)) return ItemStack.EMPTY;

            if(slotNum < inventory.getSizeInventory())
            {
                if(!this.mergeItemStack(item, inventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(item, 0, inventory.getSizeInventory(), false))
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
        inventory.closeInventory(player);
    }
}