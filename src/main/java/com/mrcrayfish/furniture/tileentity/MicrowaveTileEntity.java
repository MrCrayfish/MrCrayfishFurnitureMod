/*
UNDER CONSTRUCTION
package com.mrcrayfish.furniture.tileentity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

public class MicrowaveTileEntity extends BasicLootTileEntity implements ITickableTileEntity
{

    private Random rand = new Random();

    private static final int[] slot = new int[]{0};

    private String customName;
    private boolean cooking = false;
    public int progress = 0;
    private int timer = 0;

    public MicrowaveTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public ItemStack getItem()
    {
        return getStackInSlot(0);
    }

    public void startCooking()
    {
        if(!getStackInSlot(0).isEmpty())
        {
            RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(getStackInSlot(0));
            if(data != null)
            {
                cooking = true;
                world.updateComparatorOutputLevel(pos, blockType);
            }
        }
    }

    public void stopCooking()
    {
        this.cooking = false;
        this.progress = 0;
        world.updateComparatorOutputLevel(pos, blockType);
    }

    public boolean isCooking()
    {
        return cooking;
    }

    @Override
    public void update()
    {
        if(cooking)
        {
            if(this.world.isRemote)
            {
                double posX = pos.getX() + 0.35D + (rand.nextDouble() / 3);
                double posZ = pos.getZ() + 0.35D + (rand.nextDouble() / 3);
                ParticleSpawner.spawnParticle("smoke", posX, pos.getY() + 0.065D, posZ);
            }

            progress++;
            if(progress >= 40)
            {
                if(!getStackInSlot(0).isEmpty())
                {
                    RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(getStackInSlot(0));
                    if(data != null)
                    {
                        this.setInventorySlotContents(0, data.getOutput().copy());
                    }
                }
                if(world.isRemote)
                {
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.microwave_finish, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
                }
                timer = 0;
                progress = 0;
                cooking = false;
                world.updateComparatorOutputLevel(pos, blockType);
            }
            else
            {
                if(timer == 20)
                {
                    timer = 0;
                }
                if(timer == 0)
                {
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.microwave_running, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
                }
                timer++;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.cooking = tagCompound.getBoolean("Coooking");
        this.progress = tagCompound.getInteger("Progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("Coooking", cooking);
        tagCompound.setInteger("Progress", progress);
        return tagCompound;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) != null;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return slot;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
    {
        return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) != null;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) == null;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerMicrowave(playerInventory, this);
    }

}
 */