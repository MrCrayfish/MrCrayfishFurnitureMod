package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;

public class TileEntityChoppingBoard extends TileEntitySyncClient implements ISimpleInventory
{
    private ItemStack food = null;

    public void setFood(ItemStack food)
    {
        this.food = food;
    }

    public ItemStack getFood()
    {
        return food;
    }

    public boolean chopFood()
    {
        if(food != null)
        {
            RecipeData data = RecipeAPI.getChoppingBoardRecipeFromInput(food);
            if(data != null)
            {
                if(!world.isRemote)
                {
                    EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, data.getOutput().copy());
                    world.spawnEntity(entityItem);
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.knife_chop, SoundCategory.BLOCKS, 0.75F, 1.0F);
                }
                setFood(null);
                TileEntityUtil.markBlockForUpdate(world, pos);
                return true;
            }
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        if(tagCompound.hasKey("Food", 10))
        {
            NBTTagCompound nbt = tagCompound.getCompoundTag("Food");
            food = new ItemStack(nbt);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        NBTTagCompound nbt = new NBTTagCompound();
        if(food != null)
        {
            food.writeToNBT(nbt);
            tagCompound.setTag("Food", nbt);
        }
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
        return food;
    }

    @Override
    public void clear()
    {
        food = null;
    }
}
