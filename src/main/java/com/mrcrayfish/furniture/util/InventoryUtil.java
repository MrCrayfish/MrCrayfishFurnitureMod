package com.mrcrayfish.furniture.util;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil
{
    /**
     * Drops the items that are in a tile entity's inventory. Does nothing if the tile entity is not an instance of an {@link ISimpleInventory} or {@link IInventory}.
     *
     * @param world The world to drop the items in
     * @param pos   The position to drop the items
     * @param te    The tile entity to get the items from
     */
    public static void dropTileEntityInventoryItems(World world, BlockPos pos, TileEntity te)
    {
        if(te instanceof ISimpleInventory)
        {
            dropInventoryItems(world, pos, (ISimpleInventory) te);
        }
        if(te instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(world, pos, (IInventory) te);
        }
    }

    /**
     * Drops the items in an {@link ISimpleInventory}.
     *
     * @param world The world to drop the items in
     * @param pos   The position to drop the items
     * @param inv   The inventory to drop the items out of
     */
    public static void dropInventoryItems(World world, BlockPos pos, ISimpleInventory inv)
    {
        for(int i = 0; i < inv.getSize(); i++)
        {
            ItemStack stack = inv.getItem(i);

            if(stack != null && !stack.isEmpty())
            {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    /**
     * Calculates an IItemHandler's redstone capacity based on the amount of items inside.
     *
     * @param inventory The inventory to drop the items out of
     * @return The redstone level from 0-15 based on how full the inventory is
     */
    public static int calculateTileEntityRedstone(IItemHandler inventory)
    {
        if(inventory == null)
        {
            return 0;
        }
        else
        {
            int i = 0;
            float f = 0.0F;

            for(int j = 0; j < inventory.getSlots(); ++j)
            {
                ItemStack itemstack = inventory.getStackInSlot(j);

                if(!itemstack.isEmpty())
                {
                    f += (float) itemstack.getCount() / (float) Math.min(inventory.getSlotLimit(j), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            f = f / (float) inventory.getSlots();
            return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }

    /**
     * Writes the contents of an inventory to nbt.
     *
     * @param inventory The inventory to get the items from
     * @param nbt       The list to put the items into.
     */
    public static NBTTagList writeInventoryToNBT(ISimpleInventory inventory, NBTTagList nbt)
    {
        if(nbt != null)
        {
            for(int i = 0; i < inventory.getSize(); ++i)
            {
                ItemStack stack = inventory.getItem(i);
                if(stack != null && !stack.isEmpty())
                {
                    NBTTagCompound itemTag = new NBTTagCompound();
                    itemTag.setByte("Slot", (byte) i);
                    stack.writeToNBT(itemTag);
                    nbt.appendTag(itemTag);
                }
            }
        }
        return nbt;
    }

    /**
     * Reads the items from nbt.
     *
     * @param inventory The inventory to get the items size from
     * @param nbt       The list to get the items from
     * @return The items put into an array
     */
    public static ItemStack[] readInventoryFromNBT(ISimpleInventory inventory, NBTTagList nbt)
    {
        ItemStack[] items = new ItemStack[inventory.getSize()];

        if(nbt != null)
        {
            for(int i = 0; i < nbt.tagCount(); ++i)
            {
                NBTTagCompound itemTag = nbt.getCompoundTagAt(i);
                byte slot = itemTag.getByte("Slot");

                if(slot >= 0 && slot < items.length)
                {
                    items[slot] = new ItemStack(itemTag);
                }
            }
        }
        return items;
    }
}