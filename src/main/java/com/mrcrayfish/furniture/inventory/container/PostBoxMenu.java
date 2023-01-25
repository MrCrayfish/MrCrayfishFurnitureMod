package com.mrcrayfish.furniture.inventory.container;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.client.MailBoxEntry;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModContainers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class PostBoxMenu extends AbstractContainerMenu
{
    private final SimpleContainer mailInput = new SimpleContainer(1);
    private final ContainerLevelAccess access;
    private final List<MailBoxEntry> mailBoxes;

    public PostBoxMenu(int windowId, Inventory inventory, List<MailBoxEntry> mailBoxes)
    {
        this(windowId, inventory, ContainerLevelAccess.NULL, mailBoxes);
    }

    public PostBoxMenu(int windowId, Inventory playerInventory, ContainerLevelAccess access, List<MailBoxEntry> mailBoxes)
    {
        super(ModContainers.POST_BOX.get(), windowId);
        this.access = access;
        this.mailBoxes = ImmutableList.copyOf(mailBoxes);

        this.addSlot(new Slot(this.mailInput, 0, 149, 33));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 105 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 163));
        }
    }

    public List<MailBoxEntry> getMailBoxes()
    {
        return this.mailBoxes;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(this.access, player, ModBlocks.POST_BOX.get());
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.access.execute((world, pos) ->
        {
            this.clearContainer(player, this.mailInput);
        });
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
            if(index < this.mailInput.getContainerSize())
            {
                if(!this.moveItemStackTo(slotStack, this.mailInput.getContainerSize(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, 0, this.mailInput.getContainerSize(), false))
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

    public ItemStack getMail()
    {
        return this.mailInput.getItem(0);
    }

    public void removeMail()
    {
        this.mailInput.clearContent();
    }
}
