/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
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
import com.mrcrayfish.furniture.util.TileEntityUtil;

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
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPrinter extends TileEntityFurniture implements ISidedInventory, ITickable
{
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };

	public int printerPrintTime;
	public int currentItemPrintTime;
	public int printingTime;
	public int totalCookTime;
	
	public TileEntityPrinter() 
	{
		super("printer", 3);
	}

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.printerPrintTime = tagCompound.getShort("BurnTime");
        this.printingTime = tagCompound.getShort("CookTime");
        this.totalCookTime = tagCompound.getShort("CookTimeTotal");
        this.currentItemPrintTime = tagCompound.getInteger("CurrentTimePrintTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setShort("BurnTime", (short)this.printerPrintTime);
        tagCompound.setShort("CookTime", (short)this.printingTime);
        tagCompound.setShort("CookTimeTotal", (short)this.totalCookTime);
        tagCompound.setInteger("CurrentTimePrintTime", currentItemPrintTime);
        return tagCompound;
    }

    public boolean isPrinting()
    {
        return this.printerPrintTime > 0;
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

        if (!this.isPrinting() && (getStackInSlot(1).isEmpty() || getStackInSlot(0).isEmpty()))
        {
            if (!this.isPrinting() && this.printingTime > 0)
            {
                this.printingTime = MathHelper.clamp(this.printingTime - 2, 0, this.totalCookTime);
            }
        }
        else
        {
            if (!this.isPrinting() && this.canPrint())
            {
                this.currentItemPrintTime = this.printerPrintTime = getItemPrintTime(getStackInSlot(1));
                this.totalCookTime = this.getPrintTime(getStackInSlot(0));

                if (this.isPrinting())
                {
                    flag1 = true;

                    if (!getStackInSlot(1).isEmpty())
                    {
                    	getStackInSlot(1).shrink(1);

                        if (getStackInSlot(1).getCount() == 0)
                        {
                        	setInventorySlotContents(1, getStackInSlot(1).getItem().getContainerItem(getStackInSlot(1)));
                        }
                    }
                }
            }

            if (this.isPrinting() && this.canPrint())
            {            	
                ++this.printingTime;
                
            	if(!flag)
    			{
            		TileEntityUtil.markBlockForUpdate(world, pos);
    			}

                if (this.printingTime == this.totalCookTime)
                {
                    this.printingTime = 0;
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
			world.updateComparatorOutputLevel(pos, blockType);
		}
    }

    public int getPrintTime(ItemStack stack)
    {
    	if(stack != null && stack.getItem() == Items.ENCHANTED_BOOK)
    	{
    		return 10000;
    	}
    	return 1000;
    }

    private boolean canPrint()
    {
    	if (getStackInSlot(0).isEmpty())
		{
			return false;
		}
		if (RecipeAPI.getPrinterRecipeFromInput(getStackInSlot(0)) != null)
		{
			return getStackInSlot(2).isEmpty();
		}
		return false;
    }

    public void printItem()
    {
    	if (this.canPrint())
		{
			ItemStack stack = getStackInSlot(0);
			if (getStackInSlot(2).isEmpty())
			{
				setInventorySlotContents(2, stack.copy());
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
			if (i == Items.DYE)
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

            if (item != Items.WATER_BUCKET && item != Items.BUCKET)
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
}
