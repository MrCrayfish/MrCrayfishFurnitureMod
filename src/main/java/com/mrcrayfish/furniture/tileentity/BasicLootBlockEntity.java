package com.mrcrayfish.furniture.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class BasicLootBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer
{
    private final int[] slots;
    protected NonNullList<ItemStack> items;
    protected final ContainerOpenersCounter openersCounter = new BasicContainerOpenersCounter(this);

    public BasicLootBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        this.slots = IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public abstract int getContainerSize();

    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof ChestMenu chestMenu && chestMenu.getContainer() == this;
    }

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items)
    {
        this.items = items;
    }

    @Override
    protected abstract Component getDefaultName();

    protected boolean addItem(ItemStack stack)
    {
        for(int i = 0; i < this.getContainerSize(); i++)
        {
            if(this.getItem(i).isEmpty())
            {
                this.setItem(i, stack);
                return true;
            }
        }
        return false;
    }

    @Override
    protected abstract AbstractContainerMenu createMenu(int windowId, Inventory playerInventory);

    @Override
    public boolean isEmpty()
    {
        Iterator it = this.items.iterator();
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
        for(ItemStack stack : this.items)
        {
            if(stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        if(!this.trySaveLootTable(tag))
        {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if(!this.tryLoadLootTable(compound))
        {
            ContainerHelper.loadAllItems(compound, this.items);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction)
    {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction)
    {
        return true;
    }

    @Override
    public void startOpen(Player player)
    {
        if(!this.remove && !player.isSpectator())
        {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player)
    {
        if(!this.remove && !player.isSpectator())
        {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void updateOpenerCount()
    {
        if(!this.remove)
        {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void onOpen(Level level, BlockPos pos, BlockState state) {}

    public void onClose(Level level, BlockPos pos, BlockState state) {}
}
