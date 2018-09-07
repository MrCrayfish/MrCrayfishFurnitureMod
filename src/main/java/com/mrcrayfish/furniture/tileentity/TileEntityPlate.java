package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class TileEntityPlate extends TileEntitySyncClient implements ISimpleInventory
{
    private ItemStack food = ItemStack.EMPTY;
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
        this.setFood(ItemStack.EMPTY);
        if(tagCompound.hasKey("Items", Constants.NBT.TAG_LIST))
        {
            NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < tagList.tagCount(); ++i)
            {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                ItemStack stack = new ItemStack(itemTag);
                this.setFood(stack);
            }
        }
        else if(tagCompound.hasKey("Item", Constants.NBT.TAG_COMPOUND))
        {
            this.setFood(new ItemStack(tagCompound.getCompoundTag("Item")));
        }
        this.rotation = tagCompound.getInteger("Rotation");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        if(this.food != null)
        {
            tagCompound.setTag("Item", this.food.writeToNBT(new NBTTagCompound()));
        }
        tagCompound.setInteger("Rotation", this.rotation);
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
