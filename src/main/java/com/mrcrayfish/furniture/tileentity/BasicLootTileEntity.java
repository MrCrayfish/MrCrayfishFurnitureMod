package com.mrcrayfish.furniture.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class BasicLootTileEntity extends LockableLootTileEntity implements ISidedInventory
{
    private final int[] slots;
    protected NonNullList<ItemStack> inventory;

    public BasicLootTileEntity(TileEntityType<?> tileEntityType)
    {
        super(tileEntityType);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        this.slots = IntStream.range(0, this.getSizeInventory()).toArray();
    }

    @Override
    public abstract int getSizeInventory();

    @Override
    protected abstract ITextComponent getDefaultName();

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks)
    {
        this.inventory = stacks;
    }

    protected boolean addItem(ItemStack stack)
    {
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            if(this.getStackInSlot(i).isEmpty())
            {
                this.setInventorySlotContents(i, stack);
                return true;
            }
        }
        return false;
    }

    @Override
    protected abstract Container createMenu(int windowId, PlayerInventory playerInventory);

    @Override
    public boolean isEmpty()
    {
        Iterator it = this.inventory.iterator();
        ItemStack stack;
        do
        {
            if(!it.hasNext())
            {
                return true;
            }
            stack = (ItemStack) it.next();
        }
        while(stack.isEmpty());
        return false;
    }

    public boolean isFull()
    {
        for(ItemStack stack : this.inventory)
        {
            if(stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        if(!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.inventory);
        }
        return compound;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound)
    {
        super.read(blockState, compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if(!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.inventory);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return slots;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, @Nullable Direction direction)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, Direction direction)
    {
        return true;
    }
}
