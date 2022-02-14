package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.gui.slots.SlotDisabled;
import com.mrcrayfish.furniture.gui.slots.SlotNonInventory;
import com.mrcrayfish.furniture.items.IItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPresent extends ContainerItemInventory
{
    private ItemInventory itemInventory;

    public ContainerPresent(IInventory playerInventory, ItemInventory itemInventory)
    {
        this.itemInventory = itemInventory;

        this.addSlotToContainer(new SlotNonInventory(itemInventory, 0, 8 + 63, 16));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 1, 8 + 81, 16));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 2, 8 + 63, 34));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 3, 8 + 81, 34));

        this.addPlayerInventory(playerInventory);
    }

    @Override
    public ItemInventory getItemInventory()
    {
        return itemInventory;
    }
}
