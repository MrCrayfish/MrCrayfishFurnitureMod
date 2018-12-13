package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.api.RecipeAPI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotArmour extends Slot
{
    private EntityEquipmentSlot armourType;

    public SlotArmour(IInventory machine, int id, int x, int y, EntityEquipmentSlot armourType)
    {
        super(machine, id, x, y);
        this.armourType = armourType;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if(stack == null)
        {
            return false;
        }

        if(stack.isEmpty())
        {
            return false;
        }

        return RecipeAPI.getWashingMachineRecipeFromInput(stack) != null && EntityLiving.getSlotForItemStack(stack) == armourType;
    }

    @Override
    public String getSlotTexture()
    {
        return ItemArmor.EMPTY_SLOT_NAMES[armourType.getIndex()];
    }
}