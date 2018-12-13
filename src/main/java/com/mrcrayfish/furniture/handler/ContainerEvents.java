package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.gui.containers.ContainerItemInventory;
import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.items.IItemInventory;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ContainerEvents
{
    @SubscribeEvent
    public static void onContainerClosed(PlayerContainerEvent.Close event)
    {
        Container container = event.getContainer();
        if(container instanceof ContainerItemInventory)
        {
            ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
            if(heldItem.getItem() instanceof IItemInventory)
            {
                ContainerItemInventory itemInventory = (ContainerItemInventory) container;
                ContainerEvents.saveInventoryToItemStack(itemInventory.getItemInventory(), heldItem);
            }
        }
    }

    /**
     * Saves an IInventory to an ItemStack's NBT.
     *
     * @param inventory the inventory to save
     * @param stack the ItemStack to save the inventory to
     */
    private static void saveInventoryToItemStack(ItemInventory inventory, ItemStack stack)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        NBTTagList itemList = new NBTTagList();
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            if(!inventory.getStackInSlot(i).isEmpty())
            {
                NBTTagCompound slotEntry = new NBTTagCompound();
                slotEntry.setByte("Slot", (byte) i);
                inventory.getStackInSlot(i).writeToNBT(slotEntry);
                itemList.appendTag(slotEntry);
            }
        }
        tagCompound.setTag("Items", itemList);
        NBTHelper.setCompoundTag(stack, inventory.getName(), tagCompound);
    }
}