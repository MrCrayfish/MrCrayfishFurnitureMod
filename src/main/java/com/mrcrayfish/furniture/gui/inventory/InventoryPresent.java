package com.mrcrayfish.furniture.gui.inventory;

import com.mrcrayfish.furniture.items.IMail;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.UUID;

public class InventoryPresent extends InventoryBasic
{
    protected EntityPlayer playerEntity;
    protected static ItemStack present;
    protected boolean reading = false;
    protected String uniqueID = "";

    public InventoryPresent(EntityPlayer player, ItemStack present)
    {
        super("Present", false, getInventorySize());

        this.playerEntity = player;
        InventoryPresent.present = present;

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
        return 4;
    }

    protected boolean hasInventory()
    {
        return NBTHelper.hasTag(present, "Present");
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
                    if(nbt.getCompoundTag("Present").getString("UniqueID").equals(uniqueID))
                    {
                        itemStack.setTagCompound(present.getTagCompound());
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
        return NBTHelper.getString(present, "Author");
    }

    protected void readFromNBT()
    {
        reading = true;
        NBTTagCompound nbt = NBTHelper.getCompoundTag(present, "Present");
        if("".equals(uniqueID))
        {
            this.uniqueID = nbt.getString("UniqueID");
            if("".equals(uniqueID))
            {
                this.uniqueID = UUID.randomUUID().toString();
            }
        }
        NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(present, "Present").getTag("Items");
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
            if(getStackInSlot(i) != null)
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
        NBTHelper.setCompoundTag(present, "Present", inventory);
    }
}