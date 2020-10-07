package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.gui.slots.SlotDisabled;
import com.mrcrayfish.furniture.gui.slots.SlotNonInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnvelope extends ContainerItemInventory
{
    private ItemInventory itemInventory;

    public ContainerEnvelope(IInventory playerInventory, ItemInventory itemInventory)
    {
        this.itemInventory = itemInventory;

        this.addSlotToContainer(new SlotNonInventory(itemInventory, 0, 8 + 63, 18));
        this.addSlotToContainer(new SlotNonInventory(itemInventory, 1, 8 + 81, 18));

        this.addPlayerInventory(playerInventory);
    }

    @Override
    public ItemInventory getItemInventory()
    {
        return itemInventory;
    }
}
