package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.CabinetBlock;
import com.mrcrayfish.furniture.block.CrateBlock;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.inventory.container.CrateMenu;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class CrateBlockEntity extends BasicLootBlockEntity
{
    private UUID ownerUuid;
    private boolean locked;

    protected CrateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public CrateBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CRATE.get(), pos, state);
    }

    @Override
    public int getContainerSize()
    {
        return 27;
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container.cfm.crate");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        if(this.locked && !this.ownerUuid.equals(playerInventory.player.getUUID()))
        {
            playerInventory.player.displayClientMessage(new TranslatableComponent("container.isLocked", this.getDisplayName()), true);
            playerInventory.player.playNotifySound(SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
            return null;
        }
        return new CrateMenu(windowId, playerInventory, this, this.locked);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof CrateMenu crateMenu && crateMenu.getBlockEntity() == this;
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
        BlockEntityUtil.sendUpdatePacket(this);
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.playLidSound(state, ModSounds.BLOCK_CABINET_OPEN.get());
        this.setLidState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.playLidSound(state, ModSounds.BLOCK_CABINET_CLOSE.get());
        this.setLidState(state, false);
    }

    public void removeUnauthorisedPlayers()
    {
        if(this.locked)
        {
            int x = this.worldPosition.getX();
            int y = this.worldPosition.getY();
            int z = this.worldPosition.getZ();
            for(Player player : level.getEntitiesOfClass(Player.class, new AABB((float) x - 5.0F, (float) y - 5.0F, (float) z - 5.0F, (float) (x + 1) + 5.0F, (float) (y + 1) + 5.0F, (float) (z + 1) + 5.0F)))
            {
                if(player.containerMenu instanceof CrateMenu)
                {
                    Container container = ((CrateMenu) player.containerMenu).getBlockEntity();
                    if(this == container && !player.getUUID().equals(this.ownerUuid))
                    {
                        player.closeContainer();
                    }
                }
            }
        }
    }

    private void playLidSound(BlockState state, SoundEvent event)
    {
        Vec3i directionVec = state.getValue(CabinetBlock.DIRECTION).getNormal();
        double x = this.worldPosition.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.worldPosition.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.worldPosition.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        Level level = this.getLevel();
        if(level != null)
        {
            level.playSound(null, x, y, z, event, SoundSource.BLOCKS, 0.75F, level.random.nextFloat() * 0.1F + 0.7F);
        }
    }

    private void setLidState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(CrateBlock.OPEN, open), 3);
        }
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.readData(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        this.writeData(compound);
        return super.save(compound);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.writeData(new CompoundTag());
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
        this.readData(compound);
    }

    private void readData(CompoundTag compound)
    {
        if(compound.hasUUID("OwnerUUID"))
        {
            this.ownerUuid = compound.getUUID("OwnerUUID");
        }
        if(compound.contains("Locked", Tag.TAG_BYTE))
        {
            this.locked = compound.getBoolean("Locked");
        }
    }

    private CompoundTag writeData(CompoundTag compound)
    {
        if(this.ownerUuid != null)
        {
            compound.putUUID("OwnerUUID", this.ownerUuid);
        }
        compound.putBoolean("Locked", this.locked);
        return compound;
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction)
    {
        return !locked;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction)
    {
        return !locked;
    }
}
