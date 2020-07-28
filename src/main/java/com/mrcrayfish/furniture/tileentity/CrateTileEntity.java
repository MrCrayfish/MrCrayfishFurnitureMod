package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.CabinetBlock;
import com.mrcrayfish.furniture.block.CrateBlock;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class CrateTileEntity extends BasicLootTileEntity
{
    private UUID ownerUuid;
    private boolean locked;
    private int playerCount;

    public CrateTileEntity()
    {
        super(ModTileEntities.CRATE);
    }

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.crate");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        if(this.locked && !this.ownerUuid.equals(playerInventory.player.getUniqueID()))
        {
            playerInventory.player.sendStatusMessage(new TranslationTextComponent("container.isLocked", this.getDisplayName()), true);
            playerInventory.player.playSound(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return null;
        }
        return new CrateContainer(windowId, playerInventory, this, this.locked);
    }

    public UUID getOwner()
    {
        return ownerUuid;
    }

    public void setOwner(UUID uuid)
    {
        this.ownerUuid = uuid;
    }

    public boolean isLocked()
    {
        return this.locked;
    }

    public void setLocked(boolean locked)
    {
        this.locked = locked;
        TileEntityUtil.sendUpdatePacket(this);
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
            boolean open = blockState.get(CrateBlock.OPEN);
            if(!open)
            {
                this.playLidSound(blockState, ModSounds.BLOCK_CABINET_OPEN);
                this.setLidState(blockState, true);
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
        int x = this.pos.getX();
        int y = this.pos.getY();
        int z = this.pos.getZ();
        World world = this.getWorld();
        if(world != null)
        {
            this.updatePlayerCount(world, this, x, y, z);
            if(this.playerCount > 0)
            {
                this.scheduleTick();
            }
            else
            {
                BlockState blockState = this.getBlockState();
                if(!(blockState.getBlock() instanceof CrateBlock))
                {
                    this.remove();
                    return;
                }

                boolean open = blockState.get(CrateBlock.OPEN);
                if(open)
                {
                    this.playLidSound(blockState, ModSounds.BLOCK_CABINET_CLOSE);
                    this.setLidState(blockState, false);
                }
            }
        }
    }

    private void updatePlayerCount(World world, IInventory inventory, int x, int y, int z)
    {
        this.playerCount = 0;
        for(PlayerEntity playerEntity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double) ((float) x - 5.0F), (double) ((float) y - 5.0F), (double) ((float) z - 5.0F), (double) ((float) (x + 1) + 5.0F), (double) ((float) (y + 1) + 5.0F), (double) ((float) (z + 1) + 5.0F))))
        {
            if(playerEntity.openContainer instanceof CrateContainer)
            {
                IInventory crateInventory = ((CrateContainer) playerEntity.openContainer).getCrateTileEntity();
                if(inventory == crateInventory)
                {
                    this.playerCount++;
                }
            }
        }
    }

    public void removeUnauthorisedPlayers()
    {
        if(this.locked)
        {
            int x = this.pos.getX();
            int y = this.pos.getY();
            int z = this.pos.getZ();
            for(PlayerEntity playerEntity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double) ((float) x - 5.0F), (double) ((float) y - 5.0F), (double) ((float) z - 5.0F), (double) ((float) (x + 1) + 5.0F), (double) ((float) (y + 1) + 5.0F), (double) ((float) (z + 1) + 5.0F))))
            {
                if(playerEntity.openContainer instanceof CrateContainer)
                {
                    IInventory crateInventory = ((CrateContainer) playerEntity.openContainer).getCrateTileEntity();
                    if(this == crateInventory && !playerEntity.getUniqueID().equals(ownerUuid))
                    {
                        playerEntity.closeScreen();
                    }
                }
            }
        }
    }

    private void playLidSound(BlockState blockState, SoundEvent soundEvent)
    {
        Vector3i directionVec = blockState.get(CabinetBlock.DIRECTION).getDirectionVec();
        double x = this.pos.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.pos.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.pos.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        World world = this.getWorld();
        if(world != null)
        {
            world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.75F, world.rand.nextFloat() * 0.1F + 0.7F);
        }
    }

    private void setLidState(BlockState blockState, boolean open)
    {
        World world = this.getWorld();
        if(world != null)
        {
            world.setBlockState(this.getPos(), blockState.with(CrateBlock.OPEN, open), 3);
        }
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound)
    {
        super.read(blockState, compound);
        this.readData(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        this.writeData(compound);
        return super.write(compound);
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.writeData(new CompoundNBT());
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
        this.readData(compound);
    }

    private void readData(CompoundNBT compound)
    {
        if(compound.hasUniqueId("OwnerUUID"))
        {
            this.ownerUuid = compound.getUniqueId("OwnerUUID");
        }
        if(compound.contains("Locked", Constants.NBT.TAG_BYTE))
        {
            this.locked = compound.getBoolean("Locked");
        }
    }

    private CompoundNBT writeData(CompoundNBT compound)
    {
        if(this.ownerUuid != null)
        {
            compound.putUniqueId("OwnerUUID", this.ownerUuid);
        }
        compound.putBoolean("Locked", this.locked);
        return compound;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, @Nullable Direction direction)
    {
        return !locked;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, Direction direction)
    {
        return !locked;
    }
}
