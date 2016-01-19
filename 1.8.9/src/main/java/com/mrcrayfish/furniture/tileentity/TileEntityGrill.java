package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityGrill extends TileEntity implements ITickable, ISimpleInventory
{
	public ItemStack[] inventory = new ItemStack[1];
	
	private int coal = 0;
	private boolean fire = false;
	
	public boolean addCoal()
	{
		if(coal < 3)
		{
			coal++;
			worldObj.markBlockForUpdate(getPos());
			return true;
		}
		return false;
	}
	
	public int getCoal()
	{
		return coal;
	}
	
	public void startFire()
	{
		if(coal >= 3)
		{
			fire = true;
		} 
		worldObj.markBlockForUpdate(getPos());
	}
	
	public boolean isFireStarted()
	{
		return fire;
	}
	
	@Override
	public int getSize() 
	{
		return 1;
	}

	@Override
	public ItemStack getItem(int i) 
	{
		if(i < getSize())
		{
			return inventory[i];
		}
		return null;
	}

	@Override
	public void clear() 
	{
		inventory[0] = null;
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.coal = compound.getInteger("coal");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger("coal", this.coal);
	}

}
