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

public class InventoryEnvelope extends InventoryBasic
{
    protected EntityPlayer playerEntity;
    protected static ItemStack envelope;
    protected boolean reading = false;
    protected String uniqueID = "";

    public InventoryEnvelope(EntityPlayer player, ItemStack envelope)
    {
        super("Envelope", false, getInventorySize());

        this.playerEntity = player;
        InventoryEnvelope.envelope = envelope;

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
        if(envelope.getItem() == FurnitureItems.ENVELOPE_SIGNED)
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
        return NBTHelper.hasTag(envelope, "Envelope");
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
                    if(Objects.equals(nbt.getCompoundTag("Envelope").getString("UniqueID"), uniqueID))
                    {
                        itemStack.setTagCompound(envelope.getTagCompound());
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
        return NBTHelper.getString(envelope, "Author");
    }

    protected void readFromNBT()
    {
        reading = true;
        NBTTagCompound nbt = NBTHelper.getCompoundTag(envelope, "Envelope");
        if("".equals(uniqueID))
        {
            this.uniqueID = nbt.getString("UniqueID");
            if("".equals(uniqueID))
            {
                this.uniqueID = UUID.randomUUID().toString();
            }
        }
        NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(envelope, "Envelope").getTag("Items");
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
        NBTHelper.setCompoundTag(envelope, "Envelope", inventory);
    }
}