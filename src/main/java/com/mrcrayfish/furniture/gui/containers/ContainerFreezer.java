package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotFreezer;
import com.mrcrayfish.furniture.tileentity.TileEntityFreezer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFreezer extends Container
{
    private IInventory freezerInventory;

    public ContainerFreezer(IInventory playerInventory, IInventory freezerInventory)
    {
        this.freezerInventory = freezerInventory;
        freezerInventory.openInventory(null);

        this.addSlotToContainer(new Slot(freezerInventory, 0, 130, 6));
        this.addSlotToContainer(new Slot(freezerInventory, 1, 42, 32));
        this.addSlotToContainer(new SlotFreezer(freezerInventory, 2, 89, 32));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 77));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 135));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.freezerInventory.isUsableByPlayer(par1EntityPlayer);
    }

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
                RecipeData data = RecipeAPI.getFreezerRecipeFromInput(item);

                if(data != null)
                {
                    if(!this.mergeItemStack(item, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(TileEntityFreezer.isFuel(item))
                {
                    if(!this.mergeItemStack(item, 0, 1, false))
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
                return ItemStack.EMPTY;
            }

            slot.onTake(player, item);
        }

        return itemCopy;
    }

    public IInventory getFreezerInventory()
    {
        return freezerInventory;
    }
}
