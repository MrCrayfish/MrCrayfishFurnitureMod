package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.item.crafting.RecipeType;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GrillTileEntity extends TileEntity implements IClearable, ITickableTileEntity
{
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private final boolean[] flipped = new boolean[4];
    private final float[] experience = new float[4];

    @OnlyIn(Dist.CLIENT)
    private final int[] flippingCounter = new int[4];

    public GrillTileEntity()
    {
        super(ModTileEntities.GRILL);
    }

    public NonNullList<ItemStack> getInventory()
    {
        return inventory;
    }

    public boolean addItem(ItemStack stack, int position, int cookTime, float experience)
    {
        if(this.inventory.get(position).isEmpty())
        {
            this.inventory.set(position, stack);
            this.cookingTimes[position] = 0;
            this.cookingTotalTimes[position] = cookTime / 2; //Half the time because it has to cook both sides
            this.flipped[position] = false;
            this.experience[position] = experience;

            /* Send updates to client */
            CompoundNBT compound = new CompoundNBT();
            this.writeItems(compound);
            this.writeCookingTimes(compound);
            this.writeCookingTotalTimes(compound);
            this.writeFlipped(compound);
            TileEntityUtil.sendUpdatePacket(this, super.write(compound));
            return true;
        }
        return false;
    }

    public void flipItem(int position)
    {
        if(!this.inventory.get(position).isEmpty())
        {
            if(!this.flipped[position] && this.cookingTimes[position] == this.cookingTotalTimes[position])
            {
                this.flipped[position] = true;
                this.cookingTimes[position] = 0;

                /* Send updates to client */
                CompoundNBT compound = new CompoundNBT();
                this.writeCookingTimes(compound);
                this.writeFlipped(compound);
                TileEntityUtil.sendUpdatePacket(this, super.write(compound));
            }
            else if(this.flipped[position] && this.cookingTimes[position] == this.cookingTotalTimes[position])
            {
                double posX = pos.getX() + 0.3 + 0.4 * (position % 2);
                double posY = pos.getY() + 1.0;
                double posZ = pos.getZ() + 0.3 + 0.4 * (position / 2);

                /* Spawn experience orbs */
                int amount = (int) experience[position];
                while(amount > 0)
                {
                    int splitAmount = ExperienceOrbEntity.getXPSplit(amount);
                    amount -= splitAmount;
                    this.world.addEntity(new ExperienceOrbEntity(this.world, posX, posY, posZ, splitAmount));
                }

                /* Spams the result item */
                InventoryHelper.spawnItemStack(this.world, posX, posY, posZ, this.inventory.get(position).copy());

                /* Remove the item from the inventory */
                this.inventory.set(position, ItemStack.EMPTY);

                /* Send updates to client */
                CompoundNBT compound = new CompoundNBT();
                this.writeItems(compound);
                TileEntityUtil.sendUpdatePacket(this, super.write(compound));
            }
        }
    }

    @Override
    public void tick()
    {
        if(!this.world.isRemote())
        {
            this.cookItems();
        }
        else
        {
            this.spawnParticles();
        }
    }

    private void cookItems()
    {
        for(int i = 0; i < this.inventory.size(); i++)
        {
            if(!this.inventory.get(i).isEmpty())
            {
                if(this.cookingTimes[i] < this.cookingTotalTimes[i])
                {
                    this.cookingTimes[i]++;
                    if(this.cookingTimes[i] == this.cookingTotalTimes[i])
                    {
                        /* Set to result on cooked and flipped */
                        if(this.flipped[i])
                        {
                            Optional<GrillCookingRecipe> optional = this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(this.inventory.get(i)), this.world);
                            if(optional.isPresent())
                            {
                                this.inventory.set(i, optional.get().getRecipeOutput());
                            }
                        }

                        /* Send updates to client */
                        CompoundNBT compound = new CompoundNBT();
                        this.writeItems(compound);
                        this.writeCookingTimes(compound);
                        TileEntityUtil.sendUpdatePacket(this, super.write(compound));
                    }
                }
            }
        }
    }

    private void spawnParticles()
    {
        World world = this.getWorld();
        if(world != null)
        {
            BlockPos pos = this.getPos();
            for(int i = 0; i < this.inventory.size(); i++)
            {
                boolean cookedSide = this.cookingTimes[i] == this.cookingTotalTimes[i];
                boolean finishedCooking = cookedSide && this.flipped[i];
                if(!this.inventory.get(i).isEmpty() && world.rand.nextFloat() < 0.1F)
                {
                    double posX = pos.getX() + 0.3 + 0.4 * (i % 2);
                    double posY = pos.getY() + 1.0;
                    double posZ = pos.getZ() + 0.3 + 0.4 * (i / 2);
                    if(!finishedCooking)
                    {
                        world.addParticle(ParticleTypes.FLAME, posX, posY, posZ, 0.0D, 5.0E-4D, 0.0D);
                        if(cookedSide)
                        {
                            for(int j = 0; j < 4; j++)
                            {
                                world.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0D, 5.0E-4D, 0.0D);
                            }
                        }
                    }
                }
            }
        }
    }

    public Optional<GrillCookingRecipe> findMatchingRecipe(ItemStack input)
    {
        return this.inventory.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(input), this.world);
    }

    @Override
    public void clear()
    {
        this.inventory.clear();
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if(compound.contains("Items", Constants.NBT.TAG_LIST))
        {
            this.inventory.clear();
            ItemStackHelper.loadAllItems(compound, this.inventory);
        }
        if(compound.contains("CookingTimes", Constants.NBT.TAG_INT_ARRAY))
        {
            int[] cookingTimes = compound.getIntArray("CookingTimes");
            System.arraycopy(cookingTimes, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTimes.length));
        }
        if(compound.contains("CookingTotalTimes", Constants.NBT.TAG_INT_ARRAY))
        {
            int[] cookingTimes = compound.getIntArray("CookingTotalTimes");
            System.arraycopy(cookingTimes, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTimes.length));
        }
        if(compound.contains("Flipped", Constants.NBT.TAG_BYTE_ARRAY))
        {
            byte[] flipped = compound.getByteArray("Flipped");
            for(int i = 0; i < Math.min(this.flipped.length, flipped.length); i++)
            {
                this.flipped[i] = flipped[i] == 1;
            }
        }
        if(compound.contains("Experience", Constants.NBT.TAG_INT_ARRAY))
        {
            int[] experience = compound.getIntArray("Experience");
            for(int i = 0; i < Math.min(this.experience.length, experience.length); i++)
            {
                this.experience[i] = Float.intBitsToFloat(experience[i]);
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        this.writeItems(compound);
        this.writeCookingTimes(compound);
        this.writeCookingTotalTimes(compound);
        this.writeFlipped(compound);
        this.writeExperience(compound);
        return super.write(compound);
    }

    private CompoundNBT writeItems(CompoundNBT compound)
    {
        ItemStackHelper.saveAllItems(compound, this.inventory, true);
        return compound;
    }

    private CompoundNBT writeCookingTimes(CompoundNBT compound)
    {
        compound.putIntArray("CookingTimes", this.cookingTimes);
        return compound;
    }

    private CompoundNBT writeCookingTotalTimes(CompoundNBT compound)
    {
        compound.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        return compound;
    }

    private CompoundNBT writeFlipped(CompoundNBT compound)
    {
        byte[] flipped = new byte[this.flipped.length];
        for(int i = 0; i < this.flipped.length; i++)
        {
            flipped[i] = (byte) (this.flipped[i] ? 1 : 0);
        }
        compound.putByteArray("Flipped", flipped);
        return compound;
    }

    private CompoundNBT writeExperience(CompoundNBT compound)
    {
        int[] experience = new int[this.experience.length];
        for(int i = 0; i < this.experience.length; i++)
        {
            experience[i] = Float.floatToIntBits(experience[i]);
        }
        compound.putIntArray("Experience", experience);
        return compound;
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        CompoundNBT compound = pkt.getNbtCompound();
        this.read(compound);
    }
}
