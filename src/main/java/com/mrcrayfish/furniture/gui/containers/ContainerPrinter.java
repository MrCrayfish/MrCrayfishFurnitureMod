package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotPrinter;
import com.mrcrayfish.furniture.gui.slots.SlotPrinterInput;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPrinter extends Container
{
    private IInventory printerInventory;

    public ContainerPrinter(IInventory playerInventory, IInventory printerInventory)
    {
        this.printerInventory = printerInventory;
        this.addSlotToContainer(new SlotPrinterInput(printerInventory, 0, 80, 5));
        this.addSlotToContainer(new Slot(printerInventory, 1, 55, 30));
        this.addSlotToContainer(new SlotPrinter(printerInventory, 2, 80, 61));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 103));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 161));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.printerInventory.isUsableByPlayer(par1EntityPlayer);
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

            if(slotNum == 2)
            {
                if(!this.mergeItemStack(item, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(item, itemCopy);
            }
            else if(slotNum != 1 && slotNum != 0)
            {
                RecipeData data = RecipeAPI.getPrinterRecipeFromInput(new ItemStack(itemCopy.getItem()));
                if(data != null)
                {
                    if(!this.mergeItemStack(item, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(TileEntityPrinter.isItemFuel(item))
                {
                    if(!this.mergeItemStack(item, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(slotNum >= 3 && slotNum < 30)
                {
                    if(!this.mergeItemStack(item, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(slotNum >= 30 && slotNum < 39 && !this.mergeItemStack(item, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(item, 3, 39, false))
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

            if(item.getCount() == itemCopy.getCount())
            {
                return null;
            }

            slot.onTake(player, item);
        }

        return itemCopy;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        printerInventory.closeInventory(player);
    }
}