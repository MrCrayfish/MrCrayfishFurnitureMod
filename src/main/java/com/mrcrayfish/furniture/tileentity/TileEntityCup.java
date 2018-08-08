package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityCup extends TileEntitySyncClient implements ISimpleInventory
{
    private ItemStack item = null;
    public int red, green, blue;

    public void setItem(ItemStack item)
    {
        this.item = item.copy();
    }

    public void setColour(int[] rgb)
    {
        this.red = rgb[0];
        this.green = rgb[1];
        this.blue = rgb[2];
    }

    public ItemStack getDrink()
    {
        return item;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        if(tagCompound.hasKey("Item", 9))
        {
            NBTTagList tagList = (NBTTagList) tagCompound.getTag("Item");
            if(tagList.tagCount() > 0)
            {
                this.item = new ItemStack(tagList.getCompoundTagAt(0));
            }
        }
        this.red = tagCompound.getInteger("Red");
        this.green = tagCompound.getInteger("Green");
        this.blue = tagCompound.getInteger("Blue");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        NBTTagList tagList = new NBTTagList();
        NBTTagCompound nbt = new NBTTagCompound();
        if(item != null)
        {
            item.writeToNBT(nbt);
            tagList.appendTag(nbt);
        }
        tagCompound.setTag("Item", tagList);
        tagCompound.setInteger("Red", red);
        tagCompound.setInteger("Green", green);
        tagCompound.setInteger("Blue", blue);
        return tagCompound;
    }

    @Override
    public int getSize()
    {
        return 1;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return getDrink();
    }

    @Override
    public void clear()
    {
        red = 0;
        green = 0;
        blue = 0;
        item = null;
    }
}
