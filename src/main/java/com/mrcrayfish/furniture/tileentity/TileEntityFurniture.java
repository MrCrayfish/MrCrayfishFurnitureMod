package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class TileEntityFurniture extends TileEntityLockableLoot implements IInventory
{
	private final String ID;
	protected final NonNullList<ItemStack> INVENTORY;
	protected String customName;
	
	public TileEntityFurniture(String id, int inventorySize) 
	{
		this.ID = id;
		this.INVENTORY = NonNullList.<ItemStack>withSize(inventorySize, ItemStack.EMPTY);
	}
	
	@Override
	public String getName() 
	{
		return hasCustomName() ? this.customName : "container." + ID;
	}

	@Override
	public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

	@Override
	public String getGuiID() 
	{
		return Reference.MOD_ID + ":" + ID;
	}

	@Override
	public int getSizeInventory() 
	{
		return INVENTORY.size();
	}

	@Override
	public boolean isEmpty() 
	{
		for(ItemStack itemstack : this.INVENTORY)
        {
            if(!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.INVENTORY.clear();

        ItemStackHelper.loadAllItems(compound, this.INVENTORY);

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        ItemStackHelper.saveAllItems(compound, this.INVENTORY);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        return compound;
    }
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	protected NonNullList<ItemStack> getItems()
	{
		return this.INVENTORY;
	}
}
