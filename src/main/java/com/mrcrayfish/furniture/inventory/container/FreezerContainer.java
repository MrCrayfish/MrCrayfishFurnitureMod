package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.inventory.container.slot.FreezerFuelSlot;
import com.mrcrayfish.furniture.inventory.container.slot.FreezerResultSlot;
import com.mrcrayfish.furniture.item.crafting.RecipeType;
import com.mrcrayfish.furniture.tileentity.FreezerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Author: MrCrayfish
 */
public class FreezerContainer extends Container
{
    private FreezerTileEntity freezerTileEntity;
    private IIntArray trackedData;
    private World world;

    public FreezerContainer(int windowId, PlayerInventory playerInventory, FreezerTileEntity freezerTileEntity)
    {
        super(ModContainers.FREEZER, windowId);

        assertInventorySize(freezerTileEntity, 3);
        assertIntArraySize(freezerTileEntity.getFreezerData(), 4);

        this.freezerTileEntity = freezerTileEntity;
        this.trackedData = freezerTileEntity.getFreezerData();
        this.world = playerInventory.player.world;

        this.addSlot(new Slot(freezerTileEntity, 0, 56, 17));
        this.addSlot(new FreezerFuelSlot(this, freezerTileEntity, 1, 56, 53));
        this.addSlot(new FreezerResultSlot(playerInventory.player, freezerTileEntity, 2, 116, 35));

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

        this.trackIntArray(this.trackedData);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.freezerTileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index)
    {
        ItemStack copyStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            copyStack = slotStack.copy();
            if(index == 2)
            {
                if(!this.mergeItemStack(slotStack, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(slotStack, copyStack);
            }
            else if(index != 1 && index != 0)
            {
                if(this.isIngredient(slotStack))
                {
                    if(!this.mergeItemStack(slotStack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(this.isFuel(slotStack))
                {
                    if(!this.mergeItemStack(slotStack, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(index < 30)
                {
                    if(!this.mergeItemStack(slotStack, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(index < 39 && !this.mergeItemStack(slotStack, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(slotStack, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
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
        return this.freezerTileEntity.getFreezeTime(stack) > 0;
    }

    private boolean isIngredient(ItemStack stack)
    {
        return this.world.getRecipeManager().getRecipe(RecipeType.FREEZER_SOLIDIFY, new Inventory(stack), this.world).isPresent();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSolidifyProgressionScaled()
    {
        int freezeTime = this.trackedData.get(2);
        int freezeTimeTotal = this.trackedData.get(3);
        return freezeTimeTotal != 0 && freezeTime != 0 ? freezeTime * 24 / freezeTimeTotal : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getFuelLeftScaled()
    {
        int fuelTimeTotal = this.trackedData.get(1);
        if(fuelTimeTotal == 0)
        {
            fuelTimeTotal = 200;
        }
        return this.trackedData.get(0) * 13 / fuelTimeTotal;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFueling()
    {
        return this.trackedData.get(0) > 0;
    }
}
