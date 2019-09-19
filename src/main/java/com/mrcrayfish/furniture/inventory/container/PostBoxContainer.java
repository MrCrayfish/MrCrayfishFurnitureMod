package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

/**
 * Author: MrCrayfish
 */
public class PostBoxContainer extends Container
{
    private final IInventory mailInput = new Inventory(1);
    private final IWorldPosCallable callable;

    public PostBoxContainer(int windowId, PlayerInventory inventory)
    {
        this(windowId, inventory, IWorldPosCallable.DUMMY);
    }

    public PostBoxContainer(int windowId, PlayerInventory inventory, final IWorldPosCallable callable)
    {
        super(ModContainers.POST_BOX, windowId);
        this.callable = callable;

        this.addSlot(new Slot(this.mailInput, 0, 149, 33));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 105 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 163));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.callable.applyOrElse((world, pos) -> playerIn.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0, true);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        this.callable.consume((world, pos) ->
        {
            this.clearContainer(playerIn, world, this.mailInput);
        });
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
            if(index < this.mailInput.getSizeInventory())
            {
                if(!this.mergeItemStack(slotStack, this.mailInput.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(slotStack, 0, this.mailInput.getSizeInventory(), false))
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

    public ItemStack getMail()
    {
        return this.mailInput.getStackInSlot(0);
    }

    public void removeMail()
    {
        this.mailInput.clear();
    }
}
