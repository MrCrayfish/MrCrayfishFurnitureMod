/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.gui.containers.ContainerPrinter;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPrinter extends TileEntityLockable implements ISidedInventory, IUpdatePlayerListBox
{
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };

	private ItemStack[] inventory = new ItemStack[3];

	public int printerPrintTime;
	public int currentItemPrintTime;
	public int printingTime;
	public int totalCookTime;

	@Override
    public int getSizeInventory()
    {
        return this.inventory.length;
    }

	@Override
    public ItemStack getStackInSlot(int index)
    {
        return this.inventory[index];
    }

	@Override
    public ItemStack decrStackSize(int index, int count)
    {
        if (this.inventory[index] != null)
        {
            ItemStack itemstack;

            if (this.inventory[index].stackSize <= count)
            {
                itemstack = this.inventory[index];
                this.inventory[index] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[index].splitStack(count);

                if (this.inventory[index].stackSize == 0)
                {
                    this.inventory[index] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

	@Override
    public ItemStack getStackInSlotOnClosing(int index)
    {
        if (this.inventory[index] != null)
        {
            ItemStack itemstack = this.inventory[index];
            this.inventory[index] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

	@Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        boolean flag = stack != null && stack.isItemEqual(this.inventory[index]) && ItemStack.areItemStackTagsEqual(stack, this.inventory[index]);
        this.inventory[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag)
        {
            this.totalCookTime = this.func_174904_a(stack);
            this.printingTime = 0;
            this.markDirty();
        }
    }

	@Override
    public String getName()
    {
        return "Printer";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }
    

	@Override
	public IChatComponent getDisplayName() 
	{
		return new ChatComponentText(getName());
	}

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length)
            {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.printerPrintTime = compound.getShort("BurnTime");
        this.printingTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");
        this.currentItemPrintTime = compound.getInteger("CurrentTimePrintTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("BurnTime", (short)this.printerPrintTime);
        compound.setShort("CookTime", (short)this.printingTime);
        compound.setShort("CookTimeTotal", (short)this.totalCookTime);
        compound.setInteger("CurrentTimePrintTime", currentItemPrintTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        compound.setTag("Items", nbttaglist);
    }
    
    @Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tagCom = pkt.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), tagCom);
	}

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isPrinting()
    {
        return this.printerPrintTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isPrinting(IInventory p_174903_0_)
    {
        return p_174903_0_.getField(0) > 0;
    }

    @Override
    public void update()
    {
    	boolean flag = this.printingTime > 0;
    	
        boolean flag1 = false;

        if (this.isPrinting())
        {
            --this.printerPrintTime;
        }


            if (!this.isPrinting() && (this.inventory[1] == null || this.inventory[0] == null))
            {
                if (!this.isPrinting() && this.printingTime > 0)
                {
                    this.printingTime = MathHelper.clamp_int(this.printingTime - 2, 0, this.totalCookTime);
                }
            }
            else
            {
                if (!this.isPrinting() && this.canPrint())
                {
                    this.currentItemPrintTime = this.printerPrintTime = getItemPrintTime(this.inventory[1]);

                    if (this.isPrinting())
                    {
                        flag1 = true;

                        if (this.inventory[1] != null)
                        {
                            --this.inventory[1].stackSize;

                            if (this.inventory[1].stackSize == 0)
                            {
                                this.inventory[1] = inventory[1].getItem().getContainerItem(inventory[1]);
                            }
                        }
                    }
                }

                if (this.isPrinting() && this.canPrint())
                {            	
                    ++this.printingTime;
                    
                	if(!flag)
        			{
                		System.out.println("Starting");
        				worldObj.updateComparatorOutputLevel(pos, blockType);
        			}

                    if (this.printingTime == this.totalCookTime)
                    {
                        this.printingTime = 0;
                        this.totalCookTime = this.func_174904_a(this.inventory[0]);
                        this.printItem();
                        flag1 = true;
                    }
                }
                else
                {
                	this.printingTime = 0;
                }
            }
       

        if (flag1)
        {
            this.markDirty();
        }
        
        if (flag && printingTime == 0)
		{
        	System.out.println("Stopping");
			worldObj.updateComparatorOutputLevel(pos, blockType);
		}
    }

    public int func_174904_a(ItemStack stack)
    {
    	if(stack != null && stack.getItem() == Items.enchanted_book)
    	{
    		return 10000;
    	}
    	return 1000;
    }

    private boolean canPrint()
    {
    	if (this.inventory[0] == null)
		{
			return false;
		}
		if (RecipeAPI.getPrinterRecipeFromInput(inventory[0]) != null)
		{
			if (this.inventory[2] == null)
				return true;
		}
		return false;
    }

    public void printItem()
    {
    	if (this.canPrint())
		{
			ItemStack itemstack = this.inventory[0];
			if (this.inventory[2] == null)
			{
				this.inventory[2] = itemstack.copy();
			}
		}
    }

    public static int getItemPrintTime(ItemStack stack)
    {
    	if (stack == null)
		{
			return 0;
		}
		Item i = stack.getItem();
		if (stack.getItemDamage() == 0)
		{
			if (i == Items.dye)
				return 1000;
			if (i == FurnitureItems.itemInkCartridge)
				return 5000;
		}
		return 0;
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemPrintTime(stack) > 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
    
    @Override
    public void openInventory(EntityPlayer player) {}
    
    @Override
    public void closeInventory(EntityPlayer player) {}
    
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.printerPrintTime;
            case 1:
                return this.currentItemPrintTime;
            case 2:
                return this.printingTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.printerPrintTime = value;
                break;
            case 1:
                this.currentItemPrintTime = value;
                break;
            case 2:
                this.printingTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount()
    {
        return 4;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.inventory.length; ++i)
        {
            this.inventory[i] = null;
        }
    }
	
	@Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == 2 ? false : (index != 1 ? true : isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack));
    }
    
    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? slots_bottom : (side == EnumFacing.UP ? slots_top : slots_sides);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.water_bucket && item != Items.bucket)
            {
                return false;
            }
        }

        return true;
    }
	
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerPrinter(playerInventory, this);
    }

    @Override
    public String getGuiID()
    {
        return "0";
    }
}
