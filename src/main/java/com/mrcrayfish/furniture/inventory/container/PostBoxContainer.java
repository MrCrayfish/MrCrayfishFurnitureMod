package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

/**
 * Author: MrCrayfish
 */
public class PostBoxContainer extends Container
{
    private final IInventory mailInput = new Inventory(1);
    private final IWorldPosCallable callable;

    public PostBoxContainer(int windowId, PlayerInventory inventory)
    {
        this(windowId, inventory, IWorldPosCallable.DUMMY);
    }

    public PostBoxContainer(int windowId, PlayerInventory inventory, final IWorldPosCallable callable)
    {
        super(ModContainers.POST_BOX, windowId);
        this.callable = callable;

        this.addSlot(new Slot(this.mailInput, 0, 149, 33));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 105 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 163));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.callable.applyOrElse((world, pos) -> playerIn.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0, true);
    }

    public ItemStack getMail()
    {
        return mailInput.getStackInSlot(0);
    }
}
