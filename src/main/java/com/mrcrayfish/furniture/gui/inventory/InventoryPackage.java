package com.mrcrayfish.furniture.gui.inventory;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.items.IMail;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Objects;
import java.util.UUID;

public class InventoryPackage extends InventoryBasic
{
    protected EntityPlayer playerEntity;
    protected static ItemStack packag3;
    protected boolean reading = false;
    protected String uniqueID = "";

    public InventoryPackage(EntityPlayer player, ItemStack packag3)
    {
        super("Package", false, getInventorySize());

        this.playerEntity = player;
        InventoryPackage.packag3 = packag3;

        if(!hasInventory())
        {
            uniqueID = UUID.randomUUID().toString();
            createInventory();
        }
        loadInventory();
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        if(!reading)
        {
            saveInventory();
        }
    }

    public static boolean isSigned()
    {
        boolean isValid = false;
        if(packag3.getItem() == FurnitureItems.PACKAGE_SIGNED)
        {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        loadInventory();
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        saveInventory();
    }

    protected static int getInventorySize()
    {
        return 6;
    }

    protected boolean hasInventory()
    {
        return NBTHelper.hasTag(packag3, "Package");
    }

    protected void createInventory()
    {
        writeToNBT();
    }

    protected void setNBT()
    {
        for(ItemStack itemStack : playerEntity.inventory.mainInventory)
        {
            if(itemStack != null && itemStack.getItem() instanceof IMail)
            {
                NBTTagCompound nbt = itemStack.getTagCompound();
                if(nbt != null)
                {
                    if(Objects.equals(nbt.getCompoundTag("Package").getString("UniqueID"), uniqueID))
                    {
                        itemStack.setTagCompound(packag3.getTagCompound());
                        break;
                    }
                }
            }
        }
    }

    public void loadInventory()
    {
        readFromNBT();
    }

    public void saveInventory()
    {
        writeToNBT();
        setNBT();
    }

    public String getSender()
    {
        return NBTHelper.getString(packag3, "Author");
    }

    protected void readFromNBT()
    {
        reading = true;
        NBTTagCompound nbt = NBTHelper.getCompoundTag(packag3, "Package");
        if("".equals(uniqueID))
        {
            this.uniqueID = nbt.getString("UniqueID");
            if("".equals(uniqueID))
            {
                this.uniqueID = UUID.randomUUID().toString();
            }
        }
        NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(packag3, "Package").getTag("Items");
        for(int i = 0; i < itemList.tagCount(); i++)
        {
            NBTTagCompound slotEntry = itemList.getCompoundTagAt(i);
            int j = slotEntry.getByte("Slot") & 0xff;

            if(j >= 0 && j < getSizeInventory())
            {
                setInventorySlotContents(j, new ItemStack(slotEntry));
            }
        }
        reading = false;
    }

    protected void writeToNBT()
    {
        NBTTagList itemList = new NBTTagList();
        for(int i = 0; i < getSizeInventory(); i++)
        {
            if(getStackInSlot(i) != ItemStack.EMPTY)
            {
                NBTTagCompound slotEntry = new NBTTagCompound();
                slotEntry.setByte("Slot", (byte) i);
                getStackInSlot(i).writeToNBT(slotEntry);
                itemList.appendTag(slotEntry);
            }
        }
        NBTTagCompound inventory = new NBTTagCompound();
        inventory.setTag("Items", itemList);
        inventory.setString("UniqueID", uniqueID);
        NBTHelper.setCompoundTag(packag3, "Package", inventory);
    }
}