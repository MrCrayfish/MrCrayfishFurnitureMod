/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.slots.SlotMicrowave;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMicrowave extends Container
{
    private IInventory microwaveInventory;

    public ContainerMicrowave(IInventory playerInventory, TileEntityMicrowave microwaveInventory)
    {
        this.microwaveInventory = microwaveInventory;

        this.addSlotToContainer(new SlotMicrowave(microwaveInventory, 0, 65, 43));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 92));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 150));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.microwaveInventory.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack itemCopy = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(slotNum);

        if(slot != null && slot.getHasStack())
        {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(item);

            if(slotNum < 1)
            {
                if(!this.mergeItemStack(item, 1, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(data != null)
            {
                if(!this.mergeItemStack(item, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(slotNum > 0 && slotNum < 27)
            {
                if(!this.mergeItemStack(item, 27, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(item, 1, 27, false))
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
        this.microwaveInventory.closeInventory(player);
    }
}
