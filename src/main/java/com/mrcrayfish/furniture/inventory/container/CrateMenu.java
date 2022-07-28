package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.inventory.container.slot.PortableSlot;
import com.mrcrayfish.furniture.tileentity.CrateBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CrateMenu extends AbstractContainerMenu
{
    protected final CrateBlockEntity blockEntity;

    public CrateMenu(int windowId, Inventory playerInventory, CrateBlockEntity blockEntity, boolean locked)
    {
        super(ModContainers.CRATE.get(), windowId);
        this.blockEntity = blockEntity;
        blockEntity.startOpen(playerInventory.player);

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new PortableSlot(blockEntity, x + y * 9, 8 + x * 18, 18 + y * 18));
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
    public boolean stillValid(Player playerIn)
    {
        return this.blockEntity.stillValid(playerIn);
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
            if(index < this.blockEntity.getContainerSize())
            {
                if(!this.moveItemStackTo(slotStack, this.blockEntity.getContainerSize(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, 0, this.blockEntity.getContainerSize(), false))
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
        this.blockEntity.stopOpen(playerEntity);
    }

    public CrateBlockEntity getBlockEntity()
    {
        return blockEntity;
    }
}
