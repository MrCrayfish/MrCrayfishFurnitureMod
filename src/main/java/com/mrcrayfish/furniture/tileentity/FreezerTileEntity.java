package com.mrcrayfish.furniture.tileentity;

import com.google.common.collect.Maps;
import com.mrcrayfish.furniture.block.FreezerBlock;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.event.FreezerFuelTimeEvent;
import com.mrcrayfish.furniture.inventory.container.FreezerContainer;
import com.mrcrayfish.furniture.item.crafting.RecipeType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class FreezerTileEntity extends BasicLootTileEntity implements ITickableTileEntity
{
    private static final int[] SLOTS_SOURCE = new int[]{0};
    private static final int[] SLOTS_FUEL = new int[]{1};
    private static final int[] SLOTS_RESULT = new int[]{2};

    private int fuelTime;
    private int fuelTimeTotal;
    private int freezeTime;
    private int freezeTimeTotal;
    private int playerCount;

    protected final IIntArray freezerData = new IIntArray()
    {
        @Override
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return fuelTime;
                case 1:
                    return fuelTimeTotal;
                case 2:
                    return freezeTime;
                case 3:
                    return freezeTimeTotal;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    fuelTime = value;
                    break;
                case 1:
                    fuelTimeTotal = value;
                    break;
                case 2:
                    freezeTime = value;
                    break;
                case 3:
                    freezeTimeTotal = value;
                    break;
            }

        }

        @Override
        public int size()
        {
            return 4;
        }
    };

    private final Map<ResourceLocation, Integer> usedRecipeCount = Maps.newHashMap();

    protected FreezerTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public FreezerTileEntity()
    {
        super(ModTileEntities.FREEZER.get());
    }

    @Override
    public void tick()
    {
        boolean freezing = this.isFreezing();
        boolean shouldMarkDirty = false;

        if(this.isFreezing())
        {
            --this.fuelTime;
        }

        if(!this.world.isRemote)
        {
            ItemStack fuelStack = this.inventory.get(1);
            if(this.isFreezing() || !fuelStack.isEmpty() && !this.inventory.get(0).isEmpty())
            {
                IRecipe<?> recipe = this.world.getRecipeManager().getRecipe(RecipeType.FREEZER_SOLIDIFY, this, this.world).orElse(null);
                if(!this.isFreezing() && this.canFreeze(recipe))
                {
                    this.fuelTime = this.getFreezeTime(fuelStack);
                    this.fuelTimeTotal = this.fuelTime;
                    if(this.isFreezing())
                    {
                        shouldMarkDirty = true;
                        if(fuelStack.hasContainerItem())
                        {
                            this.inventory.set(1, fuelStack.getContainerItem());
                        }
                        else if(!fuelStack.isEmpty())
                        {
                            fuelStack.shrink(1);
                            if(fuelStack.isEmpty())
                            {
                                this.inventory.set(1, fuelStack.getContainerItem());
                            }
                        }
                    }
                }

                if(this.isFreezing() && this.canFreeze(recipe))
                {
                    ++this.freezeTime;
                    if(this.freezeTime == this.freezeTimeTotal)
                    {
                        this.freezeTime = 0;
                        this.freezeTimeTotal = this.getFreezeTime();
                        this.freeze(recipe);
                        shouldMarkDirty = true;
                    }
                }
                else
                {
                    this.freezeTime = 0;
                }
            }
            else if(this.freezeTime > 0)
            {
                this.freezeTime = MathHelper.clamp(this.freezeTime - 2, 0, this.freezeTimeTotal);
            }
        }

        if(shouldMarkDirty)
        {
            this.markDirty();
        }
    }

    private boolean isFreezing()
    {
        return this.fuelTime > 0;
    }

    public int getFreezeTime(ItemStack stack)
    {
        if(stack.isEmpty())
        {
            return 0;
        }
        else if(stack.getItem() == Items.ICE)
        {
            return 2000;
        }
        else if(stack.getItem() == Items.BLUE_ICE)
        {
            return 162000;
        }
        else if(stack.getItem() == Items.PACKED_ICE)
        {
            return 18000;
        }
        FreezerFuelTimeEvent event = new FreezerFuelTimeEvent(stack);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getFuelTime();
    }

    private boolean canFreeze(@Nullable IRecipe<?> recipe)
    {
        if(!this.inventory.get(0).isEmpty() && recipe != null)
        {
            ItemStack outputStack = recipe.getRecipeOutput();
            if(outputStack.isEmpty())
            {
                return false;
            }

            ItemStack resultStack = this.inventory.get(2);
            if(resultStack.isEmpty())
            {
                return true;
            }
            else if(!resultStack.isItemEqual(outputStack))
            {
                return false;
            }
            else if(resultStack.getCount() + outputStack.getCount() <= this.getInventoryStackLimit() && resultStack.getCount() + outputStack.getCount() <= resultStack.getMaxStackSize())
            {
                return true;
            }
            else
            {
                return resultStack.getCount() + outputStack.getCount() <= outputStack.getMaxStackSize();
            }
        }
        return false;
    }

    private void freeze(@Nullable IRecipe<?> recipe)
    {
        if(recipe != null && this.canFreeze(recipe))
        {
            ItemStack sourceStack = this.inventory.get(0);
            ItemStack outputStack = recipe.getRecipeOutput();
            ItemStack resultStack = this.inventory.get(2);
            if(resultStack.isEmpty())
            {
                this.inventory.set(2, outputStack.copy());
            }
            else if(resultStack.getItem() == outputStack.getItem())
            {
                resultStack.grow(outputStack.getCount());
            }

            if(!this.world.isRemote)
            {
                this.addRecipeUsed(recipe);
            }

            sourceStack.shrink(1);
        }
    }

    @Override
    public int getSizeInventory()
    {
        return 3;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        super.setInventorySlotContents(index, stack);
        ItemStack slotStack = this.inventory.get(index);
        boolean sameItem = !stack.isEmpty() && stack.isItemEqual(slotStack) && ItemStack.areItemStackTagsEqual(stack, slotStack);
        if(index == 0 && !sameItem)
        {
            this.freezeTimeTotal = this.getFreezeTime();
            this.freezeTime = 0;
            this.markDirty();
        }
    }

    protected int getFreezeTime()
    {
        return this.world.getRecipeManager().getRecipe(RecipeType.FREEZER_SOLIDIFY, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(300);
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.freezer");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        return new FreezerContainer(windowId, playerInventory, this);
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        switch(direction)
        {
            case UP:
                return SLOTS_SOURCE;
            case DOWN:
                return SLOTS_RESULT;
            default:
                return SLOTS_FUEL;
        }
    }

    private void addRecipeUsed(@Nullable IRecipe<?> recipe)
    {
        if(recipe != null)
        {
            this.usedRecipeCount.compute(recipe.getId(), (id, count) -> 1 + (count == null ? 0 : count));
        }
    }

    public void spawnExperience(PlayerEntity player)
    {
        for(Map.Entry<ResourceLocation, Integer> entry : this.usedRecipeCount.entrySet())
        {
            player.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) ->
            {
                spawnExperienceOrbs(player, entry.getValue(), ((AbstractCookingRecipe) recipe).getExperience());
            });
        }
        this.usedRecipeCount.clear();
    }

    private static void spawnExperienceOrbs(PlayerEntity player, int count, float exp)
    {
        if(exp == 0.0F)
        {
            count = 0;
        }
        else if(exp < 1.0F)
        {
            int totalExp = MathHelper.floor((float) count * exp);
            if(totalExp < MathHelper.ceil((float) count * exp) && Math.random() < (double) ((float) count * exp - (float) totalExp))
            {
                ++totalExp;
            }
            count = totalExp;
        }

        while(count > 0)
        {
            int splitExp = ExperienceOrbEntity.getXPSplit(count);
            count -= splitExp;
            player.world.addEntity(new ExperienceOrbEntity(player.world, player.getPosX(), player.getPosY() + 0.5, player.getPosZ(), splitExp));
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction)
    {
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction)
    {
        return index == 2;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if(index == 2)
        {
            return false;
        }
        else if(index != 1)
        {
            return true;
        }
        return this.getFreezeTime(stack) > 0;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound)
    {
        super.read(blockState, compound);
        this.freezeTime = compound.getInt("FreezeTime");
        this.freezeTimeTotal = compound.getInt("FreezeTimeTotal");
        this.fuelTime = compound.getInt("FuelTime");
        this.fuelTimeTotal = this.getFreezeTime(this.inventory.get(1));
        int recipesUsedSize = compound.getShort("RecipesUsedSize");
        for(int i = 0; i < recipesUsedSize; ++i)
        {
            ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + i));
            int amount = compound.getInt("RecipeAmount" + i);
            this.usedRecipeCount.put(resourcelocation, amount);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putInt("FreezeTime", this.freezeTime);
        compound.putInt("FreezeTimeTotal", this.freezeTimeTotal);
        compound.putInt("FuelTime", this.fuelTime);
        compound.putShort("RecipesUsedSize", (short)this.usedRecipeCount.size());
        int i = 0;
        for(Map.Entry<ResourceLocation, Integer> entry : this.usedRecipeCount.entrySet())
        {
            compound.putString("RecipeLocation" + i, entry.getKey().toString());
            compound.putInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }
        return compound;
    }

    public IIntArray getFreezerData()
    {
        return this.freezerData;
    }

    @Override
    public void openInventory(PlayerEntity playerEntity)
    {
        if(!playerEntity.isSpectator())
        {
            if(this.playerCount < 0)
            {
                this.playerCount = 0;
            }
            this.playerCount++;

            BlockState blockState = this.getBlockState();
            boolean open = blockState.get(FreezerBlock.OPEN);
            if(!open)
            {
                this.playDoorSound(blockState, ModSounds.BLOCK_FRIDGE_OPEN.get());
                this.setDoorState(blockState, true);
            }

            this.scheduleTick();
        }
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity)
    {
        if(!playerEntity.isSpectator())
        {
            this.playerCount--;
        }

    }

    private void scheduleTick()
    {
        this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
    }

    public void onScheduledTick()
    {
        World world = this.getWorld();
        if(world != null)
        {
            this.updatePlayerCount();
            if(this.playerCount > 0)
            {
                this.scheduleTick();
            }
            else
            {
                BlockState blockState = this.getBlockState();
                if(!(blockState.getBlock() instanceof FreezerBlock))
                {
                    this.remove();
                    return;
                }

                boolean open = blockState.get(FreezerBlock.OPEN);
                if(open)
                {
                    this.playDoorSound(blockState, ModSounds.BLOCK_FRIDGE_CLOSE.get());
                    this.setDoorState(blockState, false);
                }
            }
        }
    }

    private void playDoorSound(BlockState blockState, SoundEvent soundEvent)
    {
        Vector3i directionVec = blockState.get(FreezerBlock.DIRECTION).getOpposite().getDirectionVec();
        double x = this.pos.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.pos.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.pos.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        World world = this.getWorld();
        if(world != null)
        {
            world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private void setDoorState(BlockState blockState, boolean open)
    {
        World world = this.getWorld();
        if(world != null)
        {
            world.setBlockState(this.getPos(), blockState.with(FreezerBlock.OPEN, open), 3);
        }
    }

    public void updatePlayerCount()
    {
        int count = 0;
        float radius = 5.0F;
        for(PlayerEntity playerentity : this.world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double) ((float) this.pos.getX() - radius), (double) ((float) this.pos.getY() - radius), (double) ((float) this.pos.getZ() - radius), (double) ((float) (this.pos.getX() + 1) + radius), (double) ((float) (this.pos.getY() + 1) + radius), (double) ((float) (this.pos.getZ() + 1) + radius))))
        {
            if(playerentity.openContainer instanceof FreezerContainer)
            {
                count++;
            }
        }
        this.playerCount = count;
    }
}
