package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityPlate extends TileEntitySyncClient implements ISimpleInventory
{
    private ItemStack food = null;
    private int rotation = 0;

    public void setFood(ItemStack food)
    {
        this.food = food;
    }

    public ItemStack getFood()
    {
        return food;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public int getRotation()
    {
        return rotation;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        if(tagCompound.hasKey("Items", 9))
        {
            NBTTagList tagList = (NBTTagList) tagCompound.getTag("Items");
            for(int i = 0; i < tagList.tagCount(); ++i)
            {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                ItemStack stack = new ItemStack(itemTag);
                this.setFood(stack);
            }
        }
        this.rotation = tagCompound.getInteger("Rotation");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        NBTTagList tagList = new NBTTagList();
        ItemStack itemStack = food;
        if(itemStack != null)
        {
            NBTTagCompound itemTag = new NBTTagCompound();
            itemStack.writeToNBT(itemTag);
            tagList.appendTag(itemTag);
        }
        tagCompound.setTag("Items", tagList);
        tagCompound.setInteger("Rotation", rotation);
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
        return getFood();
    }

    @Override
    public void clear()
    {
        food = null;
    }
}
