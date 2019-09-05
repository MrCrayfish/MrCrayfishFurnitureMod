package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CrateContainer extends Container
{
    protected IInventory crateInventory;

    public CrateContainer(int windowId, PlayerInventory playerInventory)
    {
        this(windowId, playerInventory, new Inventory(27));
    }

    public CrateContainer(int windowId, PlayerInventory playerInventory, IInventory crateInventory)
    {
        super(ModContainers.CRATE, windowId);
        this.crateInventory = crateInventory;
        crateInventory.openInventory(playerInventory.player);

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new PortableSlot(crateInventory, x + y * 9, 8 + x * 18, 18 + y * 18));
            }
        }

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.crateInventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int index)
    {
        ItemStack clickedStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            clickedStack = slotStack.copy();
            if(index < this.crateInventory.getSizeInventory())
            {
                if(!this.mergeItemStack(slotStack, this.crateInventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(slotStack, 0, this.crateInventory.getSizeInventory(), false))
            {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return clickedStack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity)
    {
        super.onContainerClosed(playerEntity);
        this.crateInventory.closeInventory(playerEntity);
    }

    public IInventory getCrateInventory()
    {
        return crateInventory;
    }
}
