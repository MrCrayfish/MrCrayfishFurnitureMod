package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerFridge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityFridge extends TileEntityFurniture implements ISidedInventory
{
    private static final int[] slots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    public TileEntityFridge()
    {
        super("fridge", 15);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerFridge(playerInventory, this);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return slots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
    {
        return stack.getItem() instanceof ItemFood && !isLocked();
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return !isLocked();
    }
}
