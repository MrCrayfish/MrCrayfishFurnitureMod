package com.mrcrayfish.furniture.tileentity;

import com.google.common.collect.Maps;
import com.mrcrayfish.furniture.block.FreezerBlock;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModRecipeTypes;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.event.FreezerFuelTimeEvent;
import com.mrcrayfish.furniture.inventory.container.FreezerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class FreezerBlockEntity extends BasicLootBlockEntity
{
    private static final int[] SLOTS_SOURCE = new int[]{0};
    private static final int[] SLOTS_FUEL = new int[]{1};
    private static final int[] SLOTS_RESULT = new int[]{2};

    private int fuelTime;
    private int fuelTimeTotal;
    private int freezeTime;
    private int freezeTimeTotal;

    private LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    protected final SimpleContainerData freezerData = new SimpleContainerData(4)
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
    };

    private final Map<ResourceLocation, Integer> usedRecipeCount = Maps.newHashMap();

    protected FreezerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public FreezerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FREEZER.get(), pos, state);
    }
    
    public static void serverTick(Level level, BlockPos pos, BlockState state, FreezerBlockEntity blockEntity)
    {
        boolean freezing = blockEntity.isFreezing();
        boolean shouldMarkDirty = false;

        if(blockEntity.isFreezing())
        {
            --blockEntity.fuelTime;
        }

        ItemStack fuelStack = blockEntity.items.get(1);
        if(blockEntity.isFreezing() || !fuelStack.isEmpty() && !blockEntity.items.get(0).isEmpty())
        {
            Recipe<?> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.FREEZER_SOLIDIFY.get(), blockEntity, level).orElse(null);
            if(!blockEntity.isFreezing() && blockEntity.canFreeze(recipe))
            {
                blockEntity.fuelTime = blockEntity.getFreezeTime(fuelStack);
                blockEntity.fuelTimeTotal = blockEntity.fuelTime;
                if(blockEntity.isFreezing())
                {
                    shouldMarkDirty = true;
                    if(fuelStack.hasCraftingRemainingItem())
                    {
                        blockEntity.items.set(1, fuelStack.getCraftingRemainingItem());
                    }
                    else if(!fuelStack.isEmpty())
                    {
                        fuelStack.shrink(1);
                        if(fuelStack.isEmpty())
                        {
                            blockEntity.items.set(1, fuelStack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if(blockEntity.isFreezing() && blockEntity.canFreeze(recipe))
            {
                ++blockEntity.freezeTime;
                if(blockEntity.freezeTime == blockEntity.freezeTimeTotal)
                {
                    blockEntity.freezeTime = 0;
                    blockEntity.freezeTimeTotal = blockEntity.getFreezeTime();
                    blockEntity.freeze(recipe);
                    shouldMarkDirty = true;
                }
            }
            else
            {
                blockEntity.freezeTime = 0;
            }
        }
        else if(blockEntity.freezeTime > 0)
        {
            blockEntity.freezeTime = Mth.clamp(blockEntity.freezeTime - 2, 0, blockEntity.freezeTimeTotal);
        }

        if(shouldMarkDirty)
        {
            blockEntity.setChanged();
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

    private boolean canFreeze(@Nullable Recipe<?> recipe)
    {
        if(!this.items.get(0).isEmpty() && recipe != null)
        {
            ItemStack outputStack = recipe.getResultItem();
            if(outputStack.isEmpty())
            {
                return false;
            }

            ItemStack resultStack = this.items.get(2);
            if(resultStack.isEmpty())
            {
                return true;
            }
            else if(!resultStack.sameItem(outputStack))
            {
                return false;
            }
            else if(resultStack.getCount() + outputStack.getCount() <= this.getMaxStackSize() && resultStack.getCount() + outputStack.getCount() <= resultStack.getMaxStackSize())
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

    private void freeze(@Nullable Recipe<?> recipe)
    {
        if(recipe != null && this.canFreeze(recipe))
        {
            ItemStack sourceStack = this.items.get(0);
            ItemStack outputStack = recipe.getResultItem();
            ItemStack resultStack = this.items.get(2);
            if(resultStack.isEmpty())
            {
                this.items.set(2, outputStack.copy());
            }
            else if(resultStack.getItem() == outputStack.getItem())
            {
                resultStack.grow(outputStack.getCount());
            }

            if(!this.level.isClientSide)
            {
                this.addRecipeUsed(recipe);
            }

            if(sourceStack.hasCraftingRemainingItem())
            {
                this.items.set(0, sourceStack.getCraftingRemainingItem());
            }
            else
            {
                sourceStack.shrink(1);
            }
        }
    }

    @Override
    public int getContainerSize()
    {
        return 3;
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        super.setItem(index, stack);
        if(index == 0)
        {
            this.freezeTimeTotal = this.getFreezeTime();
            this.freezeTime = 0;
            this.setChanged();
        }
    }

    protected int getFreezeTime()
    {
        return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.FREEZER_SOLIDIFY.get(), this, this.level).map(AbstractCookingRecipe::getCookingTime).orElse(300);
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.translatable("container.cfm.freezer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new FreezerMenu(windowId, playerInventory, this);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof FreezerMenu freezerMenu && freezerMenu.getBlockEntity() == this;
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

    private void addRecipeUsed(@Nullable Recipe<?> recipe)
    {
        if(recipe != null)
        {
            this.usedRecipeCount.compute(recipe.getId(), (id, count) -> 1 + (count == null ? 0 : count));
        }
    }

    public void spawnExperience(Player player)
    {
        for(Map.Entry<ResourceLocation, Integer> entry : this.usedRecipeCount.entrySet())
        {
            player.level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) ->
            {
                spawnExperienceOrbs(player, entry.getValue(), ((AbstractCookingRecipe) recipe).getExperience());
            });
        }
        this.usedRecipeCount.clear();
    }

    private static void spawnExperienceOrbs(Player player, int count, float exp)
    {
        if(exp == 0.0F)
        {
            count = 0;
        }
        else if(exp < 1.0F)
        {
            int totalExp = Mth.floor((float) count * exp);
            if(totalExp < Mth.ceil((float) count * exp) && Math.random() < (double) ((float) count * exp - (float) totalExp))
            {
                ++totalExp;
            }
            count = totalExp;
        }

        while(count > 0)
        {
            int splitExp = ExperienceOrb.getExperienceValue(count);
            count -= splitExp;
            player.level.addFreshEntity(new ExperienceOrb(player.level, player.getX(), player.getY() + 0.5, player.getZ(), splitExp));
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        return index == 2;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack)
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
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.freezeTime = compound.getInt("FreezeTime");
        this.freezeTimeTotal = compound.getInt("FreezeTimeTotal");
        this.fuelTime = compound.getInt("FuelTime");
        this.fuelTimeTotal = this.getFreezeTime(this.items.get(1));
        int recipesUsedSize = compound.getShort("RecipesUsedSize");
        for(int i = 0; i < recipesUsedSize; ++i)
        {
            ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + i));
            int amount = compound.getInt("RecipeAmount" + i);
            this.usedRecipeCount.put(resourcelocation, amount);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putInt("FreezeTime", this.freezeTime);
        tag.putInt("FreezeTimeTotal", this.freezeTimeTotal);
        tag.putInt("FuelTime", this.fuelTime);
        tag.putShort("RecipesUsedSize", (short)this.usedRecipeCount.size());
        int i = 0;
        for(Map.Entry<ResourceLocation, Integer> entry : this.usedRecipeCount.entrySet())
        {
            tag.putString("RecipeLocation" + i, entry.getKey().toString());
            tag.putInt("RecipeAmount" + i, entry.getValue());
            i++;
        }
    }

    public ContainerData getFreezerData()
    {
        return this.freezerData;
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.playDoorSound(state, ModSounds.BLOCK_FRIDGE_OPEN.get());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.playDoorSound(state, ModSounds.BLOCK_FRIDGE_CLOSE.get());
        this.setDoorState(state, false);
    }

    private void playDoorSound(BlockState state, SoundEvent event)
    {
        Vec3i directionVec = state.getValue(FreezerBlock.DIRECTION).getOpposite().getNormal();
        double x = this.worldPosition.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.worldPosition.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.worldPosition.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        Level level = this.getLevel();
        if(level != null)
        {
            level.playSound(null, x, y, z, event, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(FreezerBlock.OPEN, open), 3);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if(!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if(facing == Direction.UP)
            {
                return this.handlers[0].cast();
            }
            else if(facing == Direction.DOWN)
            {
                return this.handlers[1].cast();
            }
            else
            {
                return this.handlers[2].cast();
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        for(LazyOptional<? extends IItemHandler> handler : this.handlers)
        {
            handler.invalidate();
        }
    }

    @Override
    public void reviveCaps()
    {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}
