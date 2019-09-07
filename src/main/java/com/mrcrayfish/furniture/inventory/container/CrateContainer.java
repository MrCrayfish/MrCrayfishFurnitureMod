package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IntReferenceHolder;

/**
 * Author: MrCrayfish
 */
public class CrateContainer extends Container
{
    protected IInventory crateInventory;
    protected final IntReferenceHolder locked = IntReferenceHolder.single();

    public CrateContainer(int windowId, PlayerInventory playerInventory)
    {
        this(windowId, playerInventory, new Inventory(27), false);
    }

    public CrateContainer(int windowId, PlayerInventory playerInventory, IInventory crateInventory, boolean locked)
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

        this.trackInt(this.locked).set(locked ? 1 : 0);
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

    /**
     * Sets the tracker value for the lock state. This does not control the access to the crate.
     *
     * @param locked the current lock state of the crate
     */
    public void setLocked(boolean locked)
    {
        this.locked.set(locked ? 1 : 0);
        this.detectAndSendChanges();
    }

    public boolean isLocked()
    {
        return this.locked.get() == 1;
    }
}
