package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler
{
    @SubscribeEvent
    public void onCrafted(ItemCraftedEvent event)
    {
        Item item = event.crafting.getItem();
        if(item == FurnitureItems.LOG)
        {
            for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
            {
                ItemStack stack = event.craftMatrix.getStackInSlot(i);
                if(stack != ItemStack.EMPTY)
                {
                    if(stack.getItem() instanceof ItemAxe)
                    {
                        ItemStack axe = stack.copy();
                        axe.damageItem(16, event.player);
                        if(!event.player.inventory.addItemStackToInventory(axe))
                        {
                            event.player.dropItem(axe, false, false);
                        }
                        break;
                    }
                }
            }
        }
    }
}