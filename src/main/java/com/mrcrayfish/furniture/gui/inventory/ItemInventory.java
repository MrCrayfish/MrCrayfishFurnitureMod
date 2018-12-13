package com.mrcrayfish.furniture.gui.inventory;

import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Author: MrCrayfish
 */
public class ItemInventory extends InventoryBasic
{
    private ItemStack stack;
    private boolean canInsertItems = true;

    public ItemInventory(ItemStack stack, String title, int slotCount)
    {
        super(title, false, slotCount);
        this.stack = stack;
        this.loadInventory();
    }

    public ItemInventory setCanInsertItems(boolean canInsertItems)
    {
        this.canInsertItems = canInsertItems;
        return this;
    }

    private void loadInventory()
    {
        NBTTagList itemList = NBTHelper.getCompoundTag(stack, this.getName()).getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < itemList.tagCount(); i++)
        {
            NBTTagCompound slotEntry = itemList.getCompoundTagAt(i);
            int j = slotEntry.getByte("Slot") & 0xff;
            if(j >= 0 && j < this.getSizeInventory())
            {
                this.setInventorySlotContents(j, new ItemStack(slotEntry));
            }
        }
    }

    public boolean canInsertItems()
    {
        return canInsertItems;
    }
}