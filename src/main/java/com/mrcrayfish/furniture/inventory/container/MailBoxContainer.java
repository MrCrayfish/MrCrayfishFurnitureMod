package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class MailBoxContainer extends Container
{
    private MailBoxTileEntity mailBoxTileEntity;

    public MailBoxContainer(int windowId, PlayerInventory playerInventory, MailBoxTileEntity mailBoxTileEntity)
    {
        super(ModContainers.MAIL_BOX.get(), windowId);
        this.mailBoxTileEntity = mailBoxTileEntity;
        mailBoxTileEntity.openInventory(playerInventory.player);

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(mailBoxTileEntity, x, 8 + x * 18, 18));
        }

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 50 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 108));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.mailBoxTileEntity.isUsableByPlayer(playerIn);
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
            if(index < this.mailBoxTileEntity.getSizeInventory())
            {
                if(!this.mergeItemStack(slotStack, this.mailBoxTileEntity.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(slotStack, 0, this.mailBoxTileEntity.getSizeInventory(), false))
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
        this.mailBoxTileEntity.closeInventory(playerEntity);
    }

    public MailBoxTileEntity getMailBoxTileEntity()
    {
        return mailBoxTileEntity;
    }
}
