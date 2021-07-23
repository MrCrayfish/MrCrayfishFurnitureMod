package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class MailBoxMenu extends AbstractContainerMenu
{
    private MailBoxBlockEntity mailBoxBlockEntity;

    public MailBoxMenu(int windowId, Inventory playerInventory, MailBoxBlockEntity mailBoxBlockEntity)
    {
        super(ModContainers.MAIL_BOX.get(), windowId);
        this.mailBoxBlockEntity = mailBoxBlockEntity;
        mailBoxBlockEntity.startOpen(playerInventory.player);

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(mailBoxBlockEntity, x, 8 + x * 18, 18));
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
    public boolean stillValid(Player playerIn)
    {
        return this.mailBoxBlockEntity.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerEntity, int index)
    {
        ItemStack clickedStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem())
        {
            ItemStack slotStack = slot.getItem();
            clickedStack = slotStack.copy();
            if(index < this.mailBoxBlockEntity.getContainerSize())
            {
                if(!this.moveItemStackTo(slotStack, this.mailBoxBlockEntity.getContainerSize(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, 0, this.mailBoxBlockEntity.getContainerSize(), false))
            {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
        }
        return clickedStack;
    }

    @Override
    public void removed(Player playerEntity)
    {
        super.removed(playerEntity);
        this.mailBoxBlockEntity.stopOpen(playerEntity);
    }

    public MailBoxBlockEntity getMailBoxBlockEntity()
    {
        return mailBoxBlockEntity;
    }
}
