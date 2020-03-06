package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.item.crafting.RecipeType;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFlipGrill;
import com.mrcrayfish.furniture.util.ItemStackHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
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
public class GrillTileEntity extends TileEntity implements IClearable, ITickableTileEntity, ISidedInventory
{
    /* Used for animations on client only */
    public static final int MAX_FLIPPING_COUNTER = 15;
    public static final int[] ALL_SLOTS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    public static final int[] GRILL_SLOTS = new int[]{9, 10, 11, 12};

    private final NonNullList<ItemStack> fuel = NonNullList.withSize(9, ItemStack.EMPTY);
    private final NonNullList<ItemStack> grill = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private final boolean[] flipped = new boolean[4];
    private final float[] experience = new float[4];
    private final byte[] rotations = new byte[4];
    private int remainingFuel = 0;

    /* Used for animations on client only */
    private final boolean[] flipping = new boolean[4];
    private final int[] flippingCounter = new int[4];

    public GrillTileEntity()
    {
        super(ModTileEntities.GRILL);
    }

    /**
     * Alternative Constructor useful when reusing this class.
     * Needed because registering tile entities requires valid blocks.
     * @see TileEntityType
     * @param tileEntityType : The TileEntityType you use instead.
     */
    public GrillTileEntity(TileEntityType<?> tileEntityType) { super(tileEntityType); }

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
            ItemStack copy = stack.copy();
            copy.setCount(1);
            this.grill.set(position, copy);
            this.resetPosition(position, cookTime, experience, rotation);

            /* Play place sound */
            World world = this.getWorld();
            if(world != null)
            {
                world.playSound(null, this.pos.getX() + 0.5, this.pos.getY() + 1.0, this.pos.getZ() + 0.5, ModSounds.BLOCK_GRILL_PLACE, SoundCategory.BLOCKS, 0.75F, world.rand.nextFloat() * 0.2F + 0.9F);
            }

            return true;
        }
        return false;
    }

    private void resetPosition(int position, int cookTime, float experience, byte rotation)
    {
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

                /* Play flip sound */
                World world = this.getWorld();
                if(world != null)
                {
                    world.playSound(null, this.pos.getX() + 0.5, this.pos.getY() + 1.0, this.pos.getZ() + 0.5, ModSounds.BLOCK_GRILL_FLIP, SoundCategory.BLOCKS, 0.75F, 1.0F);
                }
            }
            else if(this.flipped[position] && this.cookingTimes[position] == this.cookingTotalTimes[position])
            {
                this.removeItem(position);
            }
        }
    }

    public void flipItems()
    {
        for(int i = 0; i < 4; i++)
        {
            if(!this.grill.get(i).isEmpty())
            {
                if(!this.flipped[i] && this.cookingTimes[i] == this.cookingTotalTimes[i])
                {
                    this.flipItem(i);
                    return;
                }
            }
        }
    }

    public void removeItem(int position)
    {
        if(!this.grill.get(position).isEmpty())
        {
            double posX = pos.getX() + 0.3 + 0.4 * (position % 2);
            double posY = pos.getY() + 1.0;
            double posZ = pos.getZ() + 0.3 + 0.4 * (position / 2);

            /* Spawns the item */
            ItemEntity entity = new ItemEntity(this.world, posX, posY + 0.1, posZ, this.grill.get(position).copy());
            this.world.addEntity(entity);

            /* Remove the item from the inventory */
            this.grill.set(position, ItemStack.EMPTY);

            /* Spawn experience orbs */
            if(this.flipped[position] && this.cookingTimes[position] == this.cookingTotalTimes[position])
            {
                int amount = (int) experience[position];
                while(amount > 0)
                {
                    int splitAmount = ExperienceOrbEntity.getXPSplit(amount);
                    amount -= splitAmount;
                    this.world.addEntity(new ExperienceOrbEntity(this.world, posX, posY, posZ, splitAmount));
                }
            }

            /* Send updates to client */
            CompoundNBT compound = new CompoundNBT();
            this.writeItems(compound);
            TileEntityUtil.sendUpdatePacket(this, super.write(compound));
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
        boolean itemsChanged = false;
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
                                this.grill.set(i, optional.get().getRecipeOutput().copy());

                            }
                        }
                        itemsChanged = true;
                    }
                }
            }
        }
        if(itemsChanged)
        {
            /* Send updates to client */
            CompoundNBT compound = new CompoundNBT();
            this.writeItems(compound);
            this.writeCookingTimes(compound);
            TileEntityUtil.sendUpdatePacket(this, super.write(compound));
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
    public int getSizeInventory()
    {
        return this.fuel.size() + this.grill.size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : this.fuel)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        for(ItemStack stack : this.grill)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        if(index - this.fuel.size() >= 0)
        {
            return this.grill.get(index - this.fuel.size());
        }
        return this.fuel.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if(index - this.fuel.size() >= 0)
        {
            index -= this.fuel.size();
            ItemStack result = net.minecraft.inventory.ItemStackHelper.getAndSplit(this.grill, index, count);

            if(this.grill.get(index).isEmpty())
            {
                if(this.flipped[index] && this.cookingTimes[index] == this.cookingTotalTimes[index])
                {
                    double posX = pos.getX() + 0.3 + 0.4 * (index % 2);
                    double posY = pos.getY() + 1.0;
                    double posZ = pos.getZ() + 0.3 + 0.4 * (index / 2);
                    int amount = (int) experience[index];
                    while(amount > 0)
                    {
                        int splitAmount = ExperienceOrbEntity.getXPSplit(amount);
                        amount -= splitAmount;
                        this.world.addEntity(new ExperienceOrbEntity(this.world, posX, posY, posZ, splitAmount));
                    }
                }
            }

            /* Send updates to client */
            CompoundNBT compound = new CompoundNBT();
            this.writeItems(compound);
            TileEntityUtil.sendUpdatePacket(this, super.write(compound));

            return result;
        }

        ItemStack result = net.minecraft.inventory.ItemStackHelper.getAndSplit(this.fuel, index, count);

        /* Send updates to client */
        CompoundNBT compound = new CompoundNBT();
        this.writeFuel(compound);
        TileEntityUtil.sendUpdatePacket(this, super.write(compound));

        return result;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        if(index - this.fuel.size() >= 0)
        {
            return net.minecraft.inventory.ItemStackHelper.getAndRemove(this.grill, index - this.fuel.size());
        }
        return net.minecraft.inventory.ItemStackHelper.getAndRemove(this.fuel, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        NonNullList<ItemStack> inventory = this.fuel;
        if(index - this.fuel.size() >= 0)
        {
            index -= this.fuel.size();
            inventory = this.grill;
            int finalIndex = index;
            Optional<GrillCookingRecipe> optional = this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(stack), this.world);
            if(optional.isPresent())
            {
                GrillCookingRecipe recipe = optional.get();
                this.resetPosition(finalIndex, recipe.getCookTime(), recipe.getExperience(), (byte) 0);
            }
        }
        inventory.set(index, stack);
        if(stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        /* Send updates to client */
        CompoundNBT compound = new CompoundNBT();
        this.writeItems(compound);
        this.writeFuel(compound);
        TileEntityUtil.sendUpdatePacket(this, super.write(compound));
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void clear()
    {
        this.fuel.clear();
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

    @Override
    public boolean isUsableByPlayer(PlayerEntity player)
    {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
    }

    @Override
    public int[] getSlotsForFace(Direction side)
    {
        return side == Direction.DOWN ? GRILL_SLOTS : ALL_SLOTS;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction)
    {
        if(!this.getStackInSlot(index).isEmpty())
        {
            return false;
        }
        if(index - this.fuel.size() >= 0)
        {
            return this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(stack), this.world).isPresent();
        }
        return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction)
    {
        if(direction == Direction.DOWN)
        {
            if(index - this.fuel.size() >= 0)
            {
                index -= this.fuel.size();
                if(this.flipped[index] && this.cookingTimes[index] == this.cookingTotalTimes[index])
                {
                    Optional<GrillCookingRecipe> optional = this.world.getRecipeManager().getRecipe(RecipeType.GRILL_COOKING, new Inventory(stack), this.world);
                    return !optional.isPresent();
                }
            }
        }
        return false;
    }
}
