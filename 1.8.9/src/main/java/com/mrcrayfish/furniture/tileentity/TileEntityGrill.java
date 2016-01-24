package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.blocks.BlockGrill;
import com.mrcrayfish.furniture.blocks.BlockGrill.ClickedSide;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGrill extends TileEntity implements ITickable, ISimpleInventory
{
	public ItemStack[] inventory = new ItemStack[2];
	
	public final int COOK_DURATION = 300;
	
	private int coal = 0;
	private boolean fire = false;
	
	public boolean flippedLeft = false;
	public boolean flippedRight = false;
	
	public int leftCookTime = 0;
	public int rightCookTime = 0;
	
	public boolean leftCooked = false;
	public boolean rightCooked = false;
	
	@SideOnly(Side.CLIENT)
	public float leftFlippingCount = 0.0F;
	@SideOnly(Side.CLIENT)
	public float leftCurrentHeight = 0F;
	@SideOnly(Side.CLIENT)
	public float rightFlippingCount = 0.0F;
	@SideOnly(Side.CLIENT)
	public float rightCurrentHeight = 0F;
	
	public boolean addFood(BlockGrill.ClickedSide clickedSide, ItemStack food)
	{
		if(removeFood(clickedSide)) return false;
		
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] == null)
			{
				inventory[i] = new ItemStack(food.getItem(), 1, food.getItemDamage());
				worldObj.markBlockForUpdate(getPos());
				return true;
			}
		}
		return false;
	}
	
	public boolean removeFood(BlockGrill.ClickedSide clickedSide)
	{
		if(!worldObj.isRemote)
		{
			if(flippedLeft && clickedSide == BlockGrill.ClickedSide.LEFT)
			{
				if(inventory[0] != null)
				{
					RecipeData data = RecipeAPI.getGrillRecipeFromInput(inventory[0]);
					if(leftCooked && flippedLeft && data != null)
					{
						resetSide(clickedSide);
						spawnItem(data.getOutput());
						inventory[0] = null;
						return true;
					}
					else
					{
						resetSide(clickedSide);
						spawnItem(inventory[0]);
						inventory[0] = null;
					}
				}
			}
			else if(flippedRight && clickedSide == BlockGrill.ClickedSide.RIGHT)
			{
				if(inventory[1] != null)
				{
					RecipeData data = RecipeAPI.getGrillRecipeFromInput(inventory[1]);
					if(rightCooked && flippedRight && data != null)
					{
						resetSide(clickedSide);
						spawnItem(data.getOutput());
						inventory[1] = null;
						return true;
					}
					else
					{
						resetSide(clickedSide);
						spawnItem(inventory[1]);
						inventory[0] = null;
					}
				}
			}
		}
		return false;
	}
	
	private void spawnItem(ItemStack stack)
	{
		EntityItem entityFood = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1F, pos.getZ() + 0.5, stack);
		worldObj.spawnEntityInWorld(entityFood);
		worldObj.markBlockForUpdate(getPos());
	}
	
	public void flipFood(BlockGrill.ClickedSide clickedSide)
	{
		if(removeFood(clickedSide)) return;
		
		if(!flippedLeft && clickedSide == BlockGrill.ClickedSide.LEFT)
		{
			leftCooked = false;
			leftCookTime = 0;
			flippedLeft = true;
		}
		else if(!flippedRight && clickedSide == BlockGrill.ClickedSide.RIGHT)
		{
			rightCooked = false;
			rightCookTime = 0;
			flippedRight = true;
		}
		worldObj.markBlockForUpdate(getPos());
	}
	
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
	
	private void resetSide(BlockGrill.ClickedSide side)
	{
		if(side == ClickedSide.LEFT)
		{
			leftCooked = false;
			leftCookTime = 0;
			flippedLeft = true;
		}
		else if(side == ClickedSide.RIGHT)
		{
			rightCooked = false;
			rightCookTime = 0;
			flippedRight = true;
		}
	}
	
	@Override
	public int getSize() 
	{
		return inventory.length;
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
		if(!worldObj.isRemote)
		{
			if(fire)
			{
				if(inventory[0] != null && RecipeAPI.getGrillRecipeFromInput(inventory[0]) != null)
				{
					if(leftCookTime >= COOK_DURATION)
					{
						if(!leftCooked)
						{
							leftCooked = true;
							worldObj.markBlockForUpdate(getPos());
						}
					}
					else
					{
						System.out.println("Running");
						leftCookTime++;
					}
				}
				
				if(inventory[1] != null && RecipeAPI.getGrillRecipeFromInput(inventory[1]) != null)
				{
					if(rightCookTime >= COOK_DURATION)
					{
						if(!rightCooked)
						{
							rightCooked = true;
							worldObj.markBlockForUpdate(getPos());
						}
					}
					else
					{
						rightCookTime++;
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		if (compound.hasKey("Items"))
		{
			NBTTagList tagList = (NBTTagList) compound.getTag("Items");
			this.inventory = new ItemStack[6];

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound nbt = (NBTTagCompound) tagList.getCompoundTagAt(i);
				byte slot = nbt.getByte("Slot");

				if (slot >= 0 && slot < this.inventory.length)
				{
					this.inventory[slot] = ItemStack.loadItemStackFromNBT(nbt);
				}
			}
		}
		this.fire = compound.getBoolean("fire");
		this.coal = compound.getInteger("coal");
		this.flippedLeft = compound.getBoolean("flipLeft");
		this.flippedRight = compound.getBoolean("flipRight");
		this.leftCookTime = compound.getInteger("leftCookTime");
		this.rightCookTime = compound.getInteger("rightCookTime");
		this.leftCooked = compound.getBoolean("leftCooked");
		this.rightCooked = compound.getBoolean("rightCooked");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		NBTTagList tagList = new NBTTagList();
		for (int slot = 0; slot < this.inventory.length; ++slot)
		{
			if (this.inventory[slot] != null)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) slot);
				this.inventory[slot].writeToNBT(nbt);
				tagList.appendTag(nbt);
			}
		}
		compound.setTag("Items", tagList);
		compound.setBoolean("fire", fire);
		compound.setInteger("coal", this.coal);
		compound.setBoolean("flipLeft", flippedLeft);
		compound.setBoolean("flipRight", flippedRight);
		compound.setInteger("leftCookTime", leftCookTime);
		compound.setInteger("rightCookTime", rightCookTime);
		compound.setBoolean("leftCooked", leftCooked);
		compound.setBoolean("rightCooked", rightCooked);
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
}
