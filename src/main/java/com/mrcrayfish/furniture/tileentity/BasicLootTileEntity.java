package com.mrcrayfish.furniture.tileentity;

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
    private NonNullList<ItemStack> stacks;

    public BasicLootTileEntity(TileEntityType<?> tileEntityType)
    {
        super(tileEntityType);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        this.slots = IntStream.range(0, this.getSizeInventory()).toArray();
    }

    @Override
    public abstract int getSizeInventory();

    @Override
    protected abstract ITextComponent getDefaultName();

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks)
    {
        this.stacks = stacks;
    }

    @Override
    protected abstract Container createMenu(int windowId, PlayerInventory playerInventory);

    @Override
    public boolean isEmpty()
    {
        Iterator it = this.stacks.iterator();
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

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        if(!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.stacks);
        }
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if(!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.stacks);
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
