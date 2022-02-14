package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.gui.slots.SlotDisabled;
import com.mrcrayfish.furniture.gui.slots.SlotNonInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPackage extends ContainerItemInventory
{
    private ItemInventory itemInventory;

    public ContainerPackage(IInventory playerInventory, ItemInventory itemInventory)
    {
        this.itemInventory = itemInventory;

        this.addSlotToContainer(new SlotNonInventory(itemInventory, 0, 8 + 54, 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 1, 8 + 18 + 54, 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 2, 8 + 2 * 18 + 54, 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 3, 8 + 54, 18 + 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 4, 8 + 18 + 54, 18 + 18 - 3));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 5, 8 + 2 * 18 + 54, 18 + 18 - 3));

        this.addPlayerInventory(playerInventory);
    }

    @Override
    public ItemInventory getItemInventory()
    {
        return itemInventory;
    }
}
