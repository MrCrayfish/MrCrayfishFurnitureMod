package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.advancement.Triggers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFreezer extends Slot
{
    public SlotFreezer(IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack)
    {
        Triggers.trigger(Triggers.FREEZER_FREEZE, player);
        return super.onTake(player, stack);
    }
}