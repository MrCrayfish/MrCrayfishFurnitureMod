package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;

/**
 * Author: MrCrayfish
 */
public class TileEntityKitchenCounterDrawer extends TileEntityLockableLoot
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    private int colour = 0;

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.inventory);
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }

        this.colour = compound.getInteger("colour");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.inventory);
        }

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        compound.setInteger("colour", colour);

        return compound;
    }

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return this.inventory;
    }

    @Override
    public int getSizeInventory()
    {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.inventory)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerChest(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID()
    {
        return "minecraft:container";
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.kitchen_counter_drawer";
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.cabinet_open, SoundCategory.BLOCKS, 0.75F, 0.9F, true);
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.cabinet_close, SoundCategory.BLOCKS, 0.75F, 0.8F, true);
    }

    public void setColour(int colour)
    {
        this.colour = 15 - colour;
    }

    public int getColour()
    {
        return colour;
    }
}
