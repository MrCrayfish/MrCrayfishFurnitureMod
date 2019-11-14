package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.item.crafting.RecipeType;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFlipGrill;
import com.mrcrayfish.furniture.util.ItemStackHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
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
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GrillTileEntity extends TileEntity implements IClearable, ITickableTileEntity
{
    @OnlyIn(Dist.CLIENT)
    public static final int MAX_FLIPPING_COUNTER = 15;

    private final NonNullList<ItemStack> fuel = NonNullList.withSize(9, ItemStack.EMPTY);
    private final NonNullList<ItemStack> grill = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private final boolean[] flipped = new boolean[4];
    private final float[] experience = new float[4];
    private final byte[] rotations = new byte[4];
    private int remainingFuel = 0;

    @OnlyIn(Dist.CLIENT)
    private final boolean[] flipping = new boolean[4];
    @OnlyIn(Dist.CLIENT)
    private final int[] flippingCounter = new int[4];

    public GrillTileEntity()
    {
        super(ModTileEntities.GRILL);
    }

    @OnlyIn(Dist.CLIENT)
    public void setFlipping(int position)
    {
        this.flipping[position] = true;
        this.flippingCounter[position] = 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFlipping(int position)
    {
        return this.flipping[position];
    }

    @OnlyIn(Dist.CLIENT)
    public int getFlippingCount(int position)
    {
        return this.flippingCounter[position];
    }

    public NonNullList<ItemStack> getGrill()
    {
        return this.grill;
    }

    public NonNullList<ItemStack> getFuel()
    {
        return this.fuel;
    }

    public byte[] getRotations()
    {
        return this.rotations;
    }

    public boolean isFlipped(int position)
    {
        return this.flipped[position];
    }

    public boolean addItem(ItemStack stack, int position, int cookTime, float experience, byte rotation)
    {
        if(this.grill.get(position).isEmpty())
        {
            this.grill.set(position, stack);
            this.cookingTimes[position] = 0;
            this.cookingTotalTimes[position] = cookTime / 2; //Half the time because it has to cook both sides
            this.flipped[position] = false;
            this.experience[position] = experience;
            this.rotations[position] = rotation;

            /* Send updates to client */
            CompoundNBT compound = new CompoundNBT();
            this.writeItems(compound);
            this.writeCookingTimes(compound);
            this.writeCookingTotalTimes(compound);
            this.writeFlipped(compound);
            this.writeRotations(compound);
            TileEntityUtil.sendUpdatePacket(this, super.write(compound));
            return true;
        }
        return false;
    }

    public boolean addFuel(ItemStack stack)
    {
        for(int i = 0; i < this.fuel.size(); i++)
        {
            if(this.fuel.get(i).isEmpty())
            {
                ItemStack fuel = stack.copy();
                fuel.setCount(1);
                this.fuel.set(i, fuel);

                /* Send updates to client */
                CompoundNBT compound = new CompoundNBT();
                this.writeFuel(compound);
                TileEntityUtil.sendUpdatePacket(this, super.write(compound));

                return true;
            }
        }
        return false;
    }

    public void flipItem(int position)
    {
        if(!this.grill.get(position).isEmpty())
        {
            if(!this.flipped[position] && this.cookingTimes[position] == this.cookingTotalTimes[position])
            {
                this.flipped[position] = true;
                this.cookingTimes[position] = 0;

                /* Sends a packet to players tracking the chunk the Grill is in indicating that animation should play */
                PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(this.pos)), new MessageFlipGrill(this.pos, position));

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
                InventoryHelper.spawnItemStack(this.world, posX, posY + 0.15, posZ, this.grill.get(position).copy());

                /* Remove the item from the inventory */
                this.grill.set(position, ItemStack.EMPTY);

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
            boolean canCook = this.canCook();
            if(this.remainingFuel == 0 && canCook)
            {
                for(int i = this.fuel.size() - 1; i >= 0; i--)
                {
                    if(!this.fuel.get(i).isEmpty())
                    {
                        this.remainingFuel = net.minecraftforge.common.ForgeHooks.getBurnTime(this.fuel.get(i));
                        this.fuel.set(i, ItemStack.EMPTY);

                        /* Send updates to client */
                        CompoundNBT compound = new CompoundNBT();
                        this.writeFuel(compound);
                        this.writeRemainingFuel(compound);
                        TileEntityUtil.sendUpdatePacket(this, super.write(compound));

                        break;
                    }
                }
            }

            if(canCook && this.remainingFuel > 0)
            {
                this.cookItems();
                this.remainingFuel--;
                if(this.remainingFuel == 0)
                {
                    /* Send updates to client */
                    CompoundNBT compound = new CompoundNBT();
                    this.writeRemainingFuel(compound);
                    TileEntityUtil.sendUpdatePacket(this, super.write(compound));
                }
            }
        }
        else
        {
            this.spawnParticles();

            for(int i = 0; i < this.flipping.length; i++)
            {
                if(this.flipping[i] && this.flippingCounter[i] < MAX_FLIPPING_COUNTER)
                {
                    this.flippingCounter[i]++;
                    if(this.flippingCounter[i] == MAX_FLIPPING_COUNTER)
                    {
                        this.flipping[i] = false;
                    }
                }
            }
        }
    }

    private boolean canCook()
    {
        for(int i = 0; i < this.grill.size(); i++)
        {
            if(!this.grill.get(i).isEmpty() && this.cookingTimes[i] != this.cookingTotalTimes[i])
            {
                return true;
            }
        }
        return false;
    }

    private void cookItems()
    {
        for(int i = 0; i < this.grill.size(); i++)
        {
            if(!this.grill.get(i).isEmpty())
            {
                if(this.cookingTimes[i] < this.cookingTotalTimes[i])
                {
                    this.cookingTimes[i]++;
                    if(this.cookingTimes[i] == this.cookingTotalTimes[i])
                    {
                        /* Set to result on cooked and flipped */
                        if(this.flipped[i])
                        {
                            Optional<GrillCookingRecipe> optional = this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(this.grill.get(i)), this.world);
                            if(optional.isPresent())
                            {
                                this.grill.set(i, optional.get().getRecipeOutput());
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
            if(this.isCooking() && this.remainingFuel > 0)
            {
                double posX = pos.getX() + 0.2 + 0.6 * world.rand.nextDouble();
                double posY = pos.getY() + 0.85;
                double posZ = pos.getZ() + 0.2 + 0.6 * world.rand.nextDouble();
                world.addParticle(ParticleTypes.FLAME, posX, posY, posZ, 0.0, 0.0, 0.0);
            }

            BlockPos pos = this.getPos();
            for(int i = 0; i < this.grill.size(); i++)
            {
                if(!this.grill.get(i).isEmpty() && world.rand.nextFloat() < 0.1F)
                {
                    double posX = pos.getX() + 0.3 + 0.4 * (i % 2);
                    double posY = pos.getY() + 1.0;
                    double posZ = pos.getZ() + 0.3 + 0.4 * (i / 2);
                    if(!this.flipped[i] && this.cookingTimes[i] == this.cookingTotalTimes[i])
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

    private boolean isCooking()
    {
        for(int i = 0; i < this.grill.size(); i++)
        {
            if(!this.grill.get(i).isEmpty() && (this.cookingTimes[i] != this.cookingTotalTimes[i] || !this.flipped[i]))
            {
                return true;
            }
        }
        return false;
    }

    public Optional<GrillCookingRecipe> findMatchingRecipe(ItemStack input)
    {
        return this.grill.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(input), this.world);
    }

    @Override
    public void clear()
    {
        this.grill.clear();
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if(compound.contains("Grill", Constants.NBT.TAG_LIST))
        {
            this.grill.clear();
            ItemStackHelper.loadAllItems("Grill", compound, this.grill);
        }
        if(compound.contains("Fuel", Constants.NBT.TAG_LIST))
        {
            this.fuel.clear();
            ItemStackHelper.loadAllItems("Fuel", compound, this.fuel);
        }
        if(compound.contains("RemainingFuel", Constants.NBT.TAG_INT))
        {
            this.remainingFuel = compound.getInt("RemainingFuel");
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
        if(compound.contains("Rotations", Constants.NBT.TAG_BYTE_ARRAY))
        {
            byte[] rotations = compound.getByteArray("Rotations");
            System.arraycopy(rotations, 0, this.rotations, 0, Math.min(this.rotations.length, rotations.length));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        this.writeItems(compound);
        this.writeFuel(compound);
        this.writeCookingTimes(compound);
        this.writeCookingTotalTimes(compound);
        this.writeFlipped(compound);
        this.writeExperience(compound);
        this.writeRemainingFuel(compound);
        this.writeRotations(compound);
        return super.write(compound);
    }

    private CompoundNBT writeItems(CompoundNBT compound)
    {
        ItemStackHelper.saveAllItems("Grill", compound, this.grill, true);
        return compound;
    }

    private CompoundNBT writeFuel(CompoundNBT compound)
    {
        ItemStackHelper.saveAllItems("Fuel", compound, this.fuel, true);
        return compound;
    }

    private CompoundNBT writeRemainingFuel(CompoundNBT compound)
    {
        compound.putInt("RemainingFuel", this.remainingFuel);
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

    private CompoundNBT writeRotations(CompoundNBT compound)
    {
        compound.putByteArray("Rotations", this.rotations);
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
