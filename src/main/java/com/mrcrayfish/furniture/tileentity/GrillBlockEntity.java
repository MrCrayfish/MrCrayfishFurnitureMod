package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModRecipeTypes;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.S2CMessageFlipGrill;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import com.mrcrayfish.furniture.util.ItemStackHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GrillBlockEntity extends BlockEntity implements WorldlyContainer
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

    protected GrillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public GrillBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.GRILL.get(), pos, state);
    }

    public void setFlipping(int position)
    {
        this.flipping[position] = true;
        this.flippingCounter[position] = 0;
    }

    public boolean isFlipping(int position)
    {
        return this.flipping[position];
    }

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
            Level level = this.getLevel();
            if(level != null)
            {
                level.playSound(null, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.0, this.worldPosition.getZ() + 0.5, ModSounds.BLOCK_GRILL_PLACE.get(), SoundSource.BLOCKS, 0.75F, level.random.nextFloat() * 0.2F + 0.9F);
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
        CompoundTag compound = new CompoundTag();
        this.writeItems(compound);
        this.writeCookingTimes(compound);
        this.writeCookingTotalTimes(compound);
        this.writeFlipped(compound);
        this.writeRotations(compound);
        BlockEntityUtil.sendUpdatePacket(this, compound);
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
                CompoundTag compound = new CompoundTag();
                this.writeFuel(compound);
                BlockEntityUtil.sendUpdatePacket(this, compound);

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
                PacketHandler.getPlayChannel().send(PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.worldPosition)), new S2CMessageFlipGrill(this.worldPosition, position));

                /* Send updates to client */
                CompoundTag compound = new CompoundTag();
                this.writeCookingTimes(compound);
                this.writeFlipped(compound);
                BlockEntityUtil.sendUpdatePacket(this, compound);

                /* Play flip sound */
                Level level = this.getLevel();
                if(level != null)
                {
                    level.playSound(null, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.0, this.worldPosition.getZ() + 0.5, ModSounds.BLOCK_GRILL_FLIP.get(), SoundSource.BLOCKS, 0.75F, 1.0F);
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
            double posX = worldPosition.getX() + 0.3 + 0.4 * (position % 2);
            double posY = worldPosition.getY() + 1.0;
            double posZ = worldPosition.getZ() + 0.3 + 0.4 * (position / 2);

            /* Spawns the item */
            ItemEntity entity = new ItemEntity(this.level, posX, posY + 0.1, posZ, this.grill.get(position).copy());
            this.level.addFreshEntity(entity);

            /* Remove the item from the inventory */
            this.grill.set(position, ItemStack.EMPTY);

            /* Spawn experience orbs */
            if(this.flipped[position] && this.cookingTimes[position] == this.cookingTotalTimes[position])
            {
                int amount = (int) experience[position];
                while(amount > 0)
                {
                    int splitAmount = ExperienceOrb.getExperienceValue(amount);
                    amount -= splitAmount;
                    this.level.addFreshEntity(new ExperienceOrb(this.level, posX, posY, posZ, splitAmount));
                }
            }

            /* Send updates to client */
            CompoundTag compound = new CompoundTag();
            this.writeItems(compound);
            BlockEntityUtil.sendUpdatePacket(this, compound);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GrillBlockEntity blockEntity)
    {
        boolean canCook = blockEntity.canCook();
        if(blockEntity.remainingFuel == 0 && canCook)
        {
            for(int i = blockEntity.fuel.size() - 1; i >= 0; i--)
            {
                if(!blockEntity.fuel.get(i).isEmpty())
                {
                    blockEntity.remainingFuel = net.minecraftforge.common.ForgeHooks.getBurnTime(blockEntity.fuel.get(i), RecipeType.SMELTING);
                    blockEntity.fuel.set(i, ItemStack.EMPTY);

                    /* Send updates to client */
                    CompoundTag compound = new CompoundTag();
                    blockEntity.writeFuel(compound);
                    blockEntity.writeRemainingFuel(compound);
                    BlockEntityUtil.sendUpdatePacketSimple(blockEntity, compound);
                    break;
                }
            }
        }

        if(canCook && blockEntity.remainingFuel > 0)
        {
            blockEntity.cookItems();
            blockEntity.remainingFuel--;
            if(blockEntity.remainingFuel == 0)
            {
                /* Send updates to client */
                CompoundTag compound = new CompoundTag();
                blockEntity.writeRemainingFuel(compound);
                BlockEntityUtil.sendUpdatePacketSimple(blockEntity, compound);
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, GrillBlockEntity blockEntity)
    {
        blockEntity.spawnParticles();

        for(int i = 0; i < blockEntity.flipping.length; i++)
        {
            if(blockEntity.flipping[i] && blockEntity.flippingCounter[i] < MAX_FLIPPING_COUNTER)
            {
                blockEntity.flippingCounter[i]++;
                if(blockEntity.flippingCounter[i] == MAX_FLIPPING_COUNTER)
                {
                    blockEntity.flipping[i] = false;
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
                            Optional<GrillCookingRecipe> optional = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(this.grill.get(i)), this.level);
                            if(optional.isPresent())
                            {
                                this.grill.set(i, optional.get().getResultItem().copy());

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
            CompoundTag compound = new CompoundTag();
            this.writeItems(compound);
            this.writeCookingTimes(compound);
            BlockEntityUtil.sendUpdatePacket(this, compound);
        }
    }

    private void spawnParticles()
    {
        Level level = this.getLevel();
        if(level != null)
        {
            if(this.isCooking() && this.remainingFuel > 0)
            {
                double posX = worldPosition.getX() + 0.2 + 0.6 * level.random.nextDouble();
                double posY = worldPosition.getY() + 0.85;
                double posZ = worldPosition.getZ() + 0.2 + 0.6 * level.random.nextDouble();
                level.addParticle(ParticleTypes.FLAME, posX, posY, posZ, 0.0, 0.0, 0.0);
            }

            BlockPos pos = this.getBlockPos();
            for(int i = 0; i < this.grill.size(); i++)
            {
                if(!this.grill.get(i).isEmpty() && level.random.nextFloat() < 0.1F)
                {
                    double posX = pos.getX() + 0.3 + 0.4 * (i % 2);
                    double posY = pos.getY() + 1.0;
                    double posZ = pos.getZ() + 0.3 + 0.4 * (i / 2);
                    if(!this.flipped[i] && this.cookingTimes[i] == this.cookingTotalTimes[i])
                    {
                        for(int j = 0; j < 4; j++)
                        {
                            level.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0D, 5.0E-4D, 0.0D);
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
        return this.grill.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(input), this.level);
    }

    @Override
    public int getContainerSize()
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
    public ItemStack getItem(int index)
    {
        if(index - this.fuel.size() >= 0)
        {
            return this.grill.get(index - this.fuel.size());
        }
        return this.fuel.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count)
    {
        if(index - this.fuel.size() >= 0)
        {
            index -= this.fuel.size();
            ItemStack result = ContainerHelper.removeItem(this.grill, index, count);

            if(this.grill.get(index).isEmpty())
            {
                if(this.flipped[index] && this.cookingTimes[index] == this.cookingTotalTimes[index])
                {
                    double posX = worldPosition.getX() + 0.3 + 0.4 * (index % 2);
                    double posY = worldPosition.getY() + 1.0;
                    double posZ = worldPosition.getZ() + 0.3 + 0.4 * (index / 2);
                    int amount = (int) experience[index];
                    while(amount > 0)
                    {
                        int splitAmount = ExperienceOrb.getExperienceValue(amount);
                        amount -= splitAmount;
                        this.level.addFreshEntity(new ExperienceOrb(this.level, posX, posY, posZ, splitAmount));
                    }
                }
            }

            /* Send updates to client */
            CompoundTag compound = new CompoundTag();
            this.writeItems(compound);
            BlockEntityUtil.sendUpdatePacket(this, compound);

            return result;
        }

        ItemStack result = ContainerHelper.removeItem(this.fuel, index, count);

        /* Send updates to client */
        CompoundTag compound = new CompoundTag();
        this.writeFuel(compound);
        BlockEntityUtil.sendUpdatePacket(this, compound);

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index)
    {
        if(index - this.fuel.size() >= 0)
        {
            return ContainerHelper.takeItem(this.grill, index - this.fuel.size());
        }
        return ContainerHelper.takeItem(this.fuel, index);
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        NonNullList<ItemStack> inventory = this.fuel;
        if(index - this.fuel.size() >= 0)
        {
            index -= this.fuel.size();
            inventory = this.grill;
            int finalIndex = index;
            Optional<GrillCookingRecipe> optional = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(stack), this.level);
            if(optional.isPresent())
            {
                GrillCookingRecipe recipe = optional.get();
                this.resetPosition(finalIndex, recipe.getCookingTime(), recipe.getExperience(), (byte) 0);
            }
        }
        inventory.set(index, stack);
        if(stack.getCount() > this.getMaxStackSize())
        {
            stack.setCount(this.getMaxStackSize());
        }

        /* Send updates to client */
        CompoundTag compound = new CompoundTag();
        this.writeItems(compound);
        this.writeFuel(compound);
        BlockEntityUtil.sendUpdatePacket(this, compound);
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public void clearContent()
    {
        this.fuel.clear();
        this.grill.clear();
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        if(compound.contains("Grill", Tag.TAG_LIST))
        {
            this.grill.clear();
            ItemStackHelper.loadAllItems("Grill", compound, this.grill);
        }
        if(compound.contains("Fuel", Tag.TAG_LIST))
        {
            this.fuel.clear();
            ItemStackHelper.loadAllItems("Fuel", compound, this.fuel);
        }
        if(compound.contains("RemainingFuel", Tag.TAG_INT))
        {
            this.remainingFuel = compound.getInt("RemainingFuel");
        }
        if(compound.contains("CookingTimes", Tag.TAG_INT_ARRAY))
        {
            int[] cookingTimes = compound.getIntArray("CookingTimes");
            System.arraycopy(cookingTimes, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTimes.length));
        }
        if(compound.contains("CookingTotalTimes", Tag.TAG_INT_ARRAY))
        {
            int[] cookingTimes = compound.getIntArray("CookingTotalTimes");
            System.arraycopy(cookingTimes, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTimes.length));
        }
        if(compound.contains("Flipped", Tag.TAG_BYTE_ARRAY))
        {
            byte[] flipped = compound.getByteArray("Flipped");
            for(int i = 0; i < Math.min(this.flipped.length, flipped.length); i++)
            {
                this.flipped[i] = flipped[i] == 1;
            }
        }
        if(compound.contains("Experience", Tag.TAG_INT_ARRAY))
        {
            int[] experience = compound.getIntArray("Experience");
            for(int i = 0; i < Math.min(this.experience.length, experience.length); i++)
            {
                this.experience[i] = Float.intBitsToFloat(experience[i]);
            }
        }
        if(compound.contains("Rotations", Tag.TAG_BYTE_ARRAY))
        {
            byte[] rotations = compound.getByteArray("Rotations");
            System.arraycopy(rotations, 0, this.rotations, 0, Math.min(this.rotations.length, rotations.length));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeItems(tag);
        this.writeFuel(tag);
        this.writeCookingTimes(tag);
        this.writeCookingTotalTimes(tag);
        this.writeFlipped(tag);
        this.writeExperience(tag);
        this.writeRemainingFuel(tag);
        this.writeRotations(tag);
    }

    private CompoundTag writeItems(CompoundTag compound)
    {
        ItemStackHelper.saveAllItems("Grill", compound, this.grill, true);
        return compound;
    }

    private CompoundTag writeFuel(CompoundTag compound)
    {
        ItemStackHelper.saveAllItems("Fuel", compound, this.fuel, true);
        return compound;
    }

    private CompoundTag writeRemainingFuel(CompoundTag compound)
    {
        compound.putInt("RemainingFuel", this.remainingFuel);
        return compound;
    }

    private CompoundTag writeCookingTimes(CompoundTag compound)
    {
        compound.putIntArray("CookingTimes", this.cookingTimes);
        return compound;
    }

    private CompoundTag writeCookingTotalTimes(CompoundTag compound)
    {
        compound.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        return compound;
    }

    private CompoundTag writeFlipped(CompoundTag compound)
    {
        byte[] flipped = new byte[this.flipped.length];
        for(int i = 0; i < this.flipped.length; i++)
        {
            flipped[i] = (byte) (this.flipped[i] ? 1 : 0);
        }
        compound.putByteArray("Flipped", flipped);
        return compound;
    }

    private CompoundTag writeExperience(CompoundTag compound)
    {
        int[] experience = new int[this.experience.length];
        for(int i = 0; i < this.experience.length; i++)
        {
            experience[i] = Float.floatToIntBits(experience[i]);
        }
        compound.putIntArray("Experience", experience);
        return compound;
    }

    private CompoundTag writeRotations(CompoundTag compound)
    {
        compound.putByteArray("Rotations", this.rotations);
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithFullMetadata();
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        CompoundTag compound = pkt.getTag();
        this.load(compound);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64;
    }

    @Override
    public int[] getSlotsForFace(Direction side)
    {
        return side == Direction.DOWN ? GRILL_SLOTS : ALL_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        if(!this.getItem(index).isEmpty())
        {
            return false;
        }
        if(index - this.fuel.size() >= 0)
        {
            return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(stack), this.level).isPresent();
        }
        return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        if(direction == Direction.DOWN)
        {
            if(index - this.fuel.size() >= 0)
            {
                index -= this.fuel.size();
                if(this.flipped[index] && this.cookingTimes[index] == this.cookingTotalTimes[index])
                {
                    Optional<GrillCookingRecipe> optional = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(stack), this.level);
                    return !optional.isPresent();
                }
            }
        }
        return false;
    }
}
