package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOven extends Container
{
    private IInventory ovenInventory;

    public ContainerOven(IInventory playerInventory, IInventory ovenInventory)
    {
        this.ovenInventory = ovenInventory;
        for(int i = 0; i < 4; i++)
        {
            this.addSlotToContainer(new Slot(ovenInventory, i, i * 18 + 53, 31));
        }

        for(int i = 0; i < 4; i++)
        {
            this.addSlotToContainer(new SlotOven(ovenInventory, i + 4, i * 18 + 53, 73));
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 146));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 204));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.ovenInventory.isUsableByPlayer(player);
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

            RecipeData data = RecipeAPI.getOvenRecipeFromInput(item);

            if(slotNum < 18)
            {
                if(!this.mergeItemStack(item, 18, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(data != null)
            {
                if(!this.mergeItemStack(item, 0, 9, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(slotNum >= 18 && slotNum < this.inventorySlots.size() - 9)
            {
                if(!this.mergeItemStack(item, this.inventorySlots.size() - 9, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(slotNum >= this.inventorySlots.size() - 9 && slotNum < this.inventorySlots.size())
            {
                if(!this.mergeItemStack(item, 18, this.inventorySlots.size() - 9, false))
                {
                    return ItemStack.EMPTY;
                }
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
        this.ovenInventory.closeInventory(player);
    }
}
