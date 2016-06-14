package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.blocks.BlockGrill;
import com.mrcrayfish.furniture.blocks.BlockGrill.ClickedSide;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGrill extends TileEntity implements IUpdatePlayerListBox, ISimpleInventory
{
	public ItemStack[] inventory = new ItemStack[2];
	
	private static final int COOK_DURATION = 300;
	private static final int COAL_DURATION = COOK_DURATION * 10;
	public static final int FLIP_DURATION = 20; /* Ticks */
	
	private int coal = 0;
	private int coalTick = 0;
	private boolean fire = false;
	
	public boolean flippedLeft = false;
	public boolean flippedRight = false;
	
	public int leftCookTime = 0;
	public int rightCookTime = 0;
	
	public boolean leftCooked = false;
	public boolean rightCooked = false;
	
	public int leftFlipCount = 0;
	public float leftCurrentHeight = 0F;
	public int rightFlipCount = 0;
	public float rightCurrentHeight = 0F;
	
	public int leftSoundLoop = 0;
	public int rightSoundLoop = 0;
	
	public boolean addFood(BlockGrill.ClickedSide clickedSide, ItemStack food)
	{
		if(removeFood(clickedSide)) return false;
		
		if(RecipeAPI.getGrillRecipeFromInput(food) == null) return false;
		
		if(clickedSide != ClickedSide.UNKNOWN)
		{
			inventory[clickedSide.id]  = new ItemStack(food.getItem(), 1, food.getItemDamage());
			worldObj.markBlockForUpdate(pos);
			return true;
		}
		return false;
	}
	
	public boolean removeFood(BlockGrill.ClickedSide clickedSide)
	{
		if(!worldObj.isRemote)
		{
			if(clickedSide.id <= 1)
			{
				if(inventory[clickedSide.id] != null)
				{
					resetSide(clickedSide);
					RecipeData data = RecipeAPI.getGrillRecipeFromInput(inventory[clickedSide.id]);
					if(isCooked(clickedSide) && data != null)
					{
						spawnItem(data.getOutput());
					}
					else
					{
						spawnItem(inventory[clickedSide.id]);
					}
					inventory[clickedSide.id] = null;
					worldObj.markBlockForUpdate(getPos());
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isFlipped(BlockGrill.ClickedSide side)
	{
		if(side.id == 0) return flippedLeft;
		return flippedRight;
	}
	
	private boolean isCooked(BlockGrill.ClickedSide side)
	{
		if(!isFlipped(side)) return false;
		if(side.id == 0) return leftCooked;
		return rightCooked;
	}
	
	private void spawnItem(ItemStack stack)
	{
		EntityItem entityFood = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1F, pos.getZ() + 0.5, stack);
		worldObj.spawnEntityInWorld(entityFood);
	}
	
	public void flipFood(BlockGrill.ClickedSide clickedSide)
	{
		if(isCooked(clickedSide) && removeFood(clickedSide)) return;
		
		if(leftCooked && !flippedLeft && clickedSide == BlockGrill.ClickedSide.LEFT)
		{
			leftCooked = false;
			leftCookTime = 0;
			flippedLeft = true; 
			leftSoundLoop = 0;
		}
		else if(rightCooked && !flippedRight && clickedSide == BlockGrill.ClickedSide.RIGHT)
		{
			rightCooked = false;
			rightCookTime = 0;
			flippedRight = true;
			rightSoundLoop = 0;
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
		if(coal > 0)
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
			flippedLeft = false;
			leftSoundLoop = 0;
		}
		else if(side == ClickedSide.RIGHT)
		{
			rightCooked = false;
			rightCookTime = 0;
			flippedRight = false;
			rightSoundLoop = 0;
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
				if(inventory[0] != null)
				{
					RecipeData dataLeft = RecipeAPI.getGrillRecipeFromInput(inventory[0]);
					if(dataLeft != null)
					{
						if(leftCookTime >= COOK_DURATION)
						{
							if(!leftCooked)
							{
								if(flippedLeft)
								{
									inventory[0] = dataLeft.getOutput().copy();
								}
								leftCooked = true;
								worldObj.markBlockForUpdate(getPos());
								leftSoundLoop = 0;
							}
							if(leftSoundLoop % 20 == 0)
							{
								worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, "cfm:sizzle", 1.0F, 0.5F);
							}
						}
						else
						{
							if(leftSoundLoop % 20 == 0)
							{
								if(!leftCooked)
								{
									if(flippedLeft && leftCookTime >= 20)
									{
										worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, "cfm:sizzle", 1.0F, 1.0F);
									}
									else if(!flippedLeft)
									{
										worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, "cfm:sizzle", 1.0F, 1.0F);
									}
								}
							}
							leftCookTime++;
						}
						leftSoundLoop++;
					}
				}
				if(inventory[1] != null)
				{
					RecipeData dataRight = RecipeAPI.getGrillRecipeFromInput(inventory[1]);
					if(dataRight != null)
					{
						if(rightCookTime >= COOK_DURATION)
						{
							if(!rightCooked)
							{
								if(flippedRight)
								{
									inventory[1] = dataRight.getOutput().copy();
								}
								rightCooked = true;
								worldObj.markBlockForUpdate(getPos());
								rightSoundLoop = 0;
							}
							if(rightSoundLoop % 20 == 0)
							{
								worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, "cfm:sizzle", 1.0F, 0.5F);
							}
						}
						else
						{
							if(rightSoundLoop % 20 == 0)
							{
								if(!rightCooked)
								{
									if(flippedRight && rightCookTime >= 20)
									{
										worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, "cfm:sizzle", 1.0F, 1.0F);
									}
									else if(!flippedRight)
									{
										worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, "cfm:sizzle", 1.0F, 1.0F);
									}
								}
							}
							rightCookTime++;
						}
						rightSoundLoop++;
					}
				}
				
				coalTick++;
				
				if(coalTick >= COAL_DURATION)
				{
					coalTick = 0;
					coal--;
					if(coal <= 0)
					{
						fire = false;
					}
					worldObj.markBlockForUpdate(getPos());
				}
			}
		}
		else
		{
			if(flippedLeft && leftFlipCount < FLIP_DURATION)
			{
				leftFlipCount++;
			}
			if(flippedRight && rightFlipCount < FLIP_DURATION)
			{
				rightFlipCount++;
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
		this.coalTick = compound.getInteger("coalTick");
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
		compound.setInteger("coalTick", coalTick);
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
