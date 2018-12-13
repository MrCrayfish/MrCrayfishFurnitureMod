package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotArmour;
import com.mrcrayfish.furniture.gui.slots.SlotSoapyWater;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerWashingMachine extends Container
{
    private IInventory washingMachineInventory;

    public ContainerWashingMachine(IInventory playerInventory, IInventory washingMachineInventory)
    {
        this.washingMachineInventory = washingMachineInventory;

        this.addSlotToContainer(new SlotArmour(washingMachineInventory, 0, 80, 44, EntityEquipmentSlot.HEAD));
        this.addSlotToContainer(new SlotArmour(washingMachineInventory, 1, 64, 60, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotArmour(washingMachineInventory, 2, 96, 60, EntityEquipmentSlot.LEGS));
        this.addSlotToContainer(new SlotArmour(washingMachineInventory, 3, 80, 76, EntityEquipmentSlot.FEET));
        this.addSlotToContainer(new SlotSoapyWater(washingMachineInventory, 4, 125, 7));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 146));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 204));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.washingMachineInventory.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack itemCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotNum);

        if(slot != null && slot.getHasStack())
        {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            if(slotNum < 5)
            {
                if(!this.mergeItemStack(item, 5, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(slotNum > 4)
            {
                RecipeData data = RecipeAPI.getWashingMachineRecipeFromInput(item);
                if(data != null && (itemCopy.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN) || itemCopy.getItem() == Items.SKULL || itemCopy.getItem().getEquipmentSlot(itemCopy) != null || (itemCopy.getItem() instanceof ItemArmor && ((ItemArmor) itemCopy.getItem()).armorType != null) || EntityLiving.getSlotForItemStack(itemCopy) != EntityEquipmentSlot.HEAD))
                {
                    if(!this.mergeItemStack(item, 0, 4, true))
                    {
                        return ItemStack.EMPTY;
                    }
                    else if(slotNum > 4 && slotNum < this.inventorySlots.size() - 9)
                    {
                        if(!this.mergeItemStack(item, this.inventorySlots.size() - 9, this.inventorySlots.size(), false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if(slotNum >= this.inventorySlots.size() - 9 && slotNum < this.inventorySlots.size())
                    {
                        if(!this.mergeItemStack(item, 5, this.inventorySlots.size() - 9, false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                }
                else if(item.getItem() == FurnitureItems.SOAPY_WATER | item.getItem() == FurnitureItems.SUPER_SOAPY_WATER)
                {
                    if(!this.mergeItemStack(item, 4, 5, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(slotNum > 4 && slotNum < this.inventorySlots.size() - 9)
                {
                    if(!this.mergeItemStack(item, this.inventorySlots.size() - 9, this.inventorySlots.size(), false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(slotNum >= this.inventorySlots.size() - 9 && slotNum < this.inventorySlots.size())
                {
                    if(!this.mergeItemStack(item, 5, this.inventorySlots.size() - 9, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if(!this.mergeItemStack(item, 0, 9, false))
            {
                return ItemStack.EMPTY;
            }

            if(item.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemCopy;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        washingMachineInventory.closeInventory(player);
    }
}