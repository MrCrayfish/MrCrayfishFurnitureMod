package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.inventory.container.slot.FreezerFuelSlot;
import com.mrcrayfish.furniture.inventory.container.slot.FreezerResultSlot;
import com.mrcrayfish.furniture.item.crafting.ModRecipeTypes;
import com.mrcrayfish.furniture.tileentity.FreezerBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class FreezerMenu extends AbstractContainerMenu
{
    private final FreezerBlockEntity blockEntity;
    private final ContainerData data;
    private final Level level;

    public FreezerMenu(int windowId, Inventory playerInventory, FreezerBlockEntity blockEntity)
    {
        super(ModContainers.FREEZER.get(), windowId);

        checkContainerSize(blockEntity, 3);
        checkContainerDataCount(blockEntity.getFreezerData(), 4);

        blockEntity.startOpen(playerInventory.player);

        this.blockEntity = blockEntity;
        this.data = blockEntity.getFreezerData();
        this.level = playerInventory.player.level;

        this.addSlot(new Slot(blockEntity, 0, 56, 17));
        this.addSlot(new FreezerFuelSlot(this, blockEntity, 1, 56, 53));
        this.addSlot(new FreezerResultSlot(playerInventory.player, blockEntity, 2, 116, 35));

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(this.data);
    }

    public FreezerBlockEntity getBlockEntity()
    {
        return this.blockEntity;
    }

    @Override
    public boolean stillValid(Player playerIn)
    {
        return this.blockEntity.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack copyStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem())
        {
            ItemStack slotStack = slot.getItem();
            copyStack = slotStack.copy();
            if(index == 2)
            {
                if(!this.moveItemStackTo(slotStack, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, copyStack);
            }
            else if(index != 1 && index != 0)
            {
                if(this.isIngredient(slotStack))
                {
                    if(!this.moveItemStackTo(slotStack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(this.isFuel(slotStack))
                {
                    if(!this.moveItemStackTo(slotStack, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(index < 30)
                {
                    if(!this.moveItemStackTo(slotStack, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(index < 39 && !this.moveItemStackTo(slotStack, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if(slotStack.getCount() == copyStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return copyStack;
    }

    public boolean isFuel(ItemStack stack)
    {
        return this.blockEntity.getFreezeTime(stack) > 0;
    }

    private boolean isIngredient(ItemStack stack)
    {
        return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.FREEZER_SOLIDIFY, new SimpleContainer(stack), this.level).isPresent();
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.blockEntity.stopOpen(player);
    }

    public int getSolidifyProgressionScaled()
    {
        int freezeTime = this.data.get(2);
        int freezeTimeTotal = this.data.get(3);
        return freezeTimeTotal != 0 && freezeTime != 0 ? freezeTime * 24 / freezeTimeTotal : 0;
    }

    public int getFuelLeftScaled()
    {
        int fuelTimeTotal = this.data.get(1);
        if(fuelTimeTotal == 0)
        {
            fuelTimeTotal = 200;
        }
        return this.data.get(0) * 13 / fuelTimeTotal;
    }

    public boolean isFueling()
    {
        return this.data.get(0) > 0;
    }
}
