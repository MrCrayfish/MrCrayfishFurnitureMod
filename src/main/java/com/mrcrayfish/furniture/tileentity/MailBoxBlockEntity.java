package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.inventory.container.MailBoxMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MailBoxBlockEntity extends BasicLootBlockEntity
{
    private UUID id;
    private String name = "Mail Box";
    private String ownerName;
    private UUID ownerId;

    protected MailBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public MailBoxBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.MAIL_BOX.get(), pos, state);
    }

    public void setId(UUID id)
    {
        if(this.id == null)
        {
            this.id = id;
        }
    }

    @Nullable
    public UUID getId()
    {
        return this.id;
    }

    public void setMailBoxName(String name)
    {
        this.name = name;
    }

    public String getMailBoxName()
    {
        return name;
    }

    public void setOwner(ServerPlayer entity)
    {
        this.ownerId = entity.getUUID();
        this.ownerName = entity.getName().getString();
    }

    @Nullable
    public UUID getOwnerId()
    {
        return this.ownerId;
    }

    @Nullable
    public String getOwnerName()
    {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public void updateOwnerName(ServerPlayer player)
    {
        if(player.getUUID().equals(this.ownerId))
        {
            if(!player.getName().getString().equals(this.ownerName))
            {
                this.ownerName = player.getName().getString();
            }
        }
    }

    public void updateIdAndAttemptClaim(ServerPlayer player)
    {
        if(this.id == null)
        {
            this.id = UUID.randomUUID();
        }
        if(this.ownerId == null)
        {
            this.setOwner(player);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MailBoxBlockEntity blockEntity)
    {
        MinecraftServer server = level.getServer();
        if(server == null || blockEntity.ownerId == null || blockEntity.id == null)
            return;

        // Attempt to register the mail box if it was somehow removed
        if(!PostOffice.isRegistered(blockEntity.ownerId, blockEntity.id))
        {
            ServerPlayer player = server.getPlayerList().getPlayer(blockEntity.ownerId);
            if(player != null)
            {
                PostOffice.registerMailBox(player, blockEntity.id, "Mail Box", blockEntity.worldPosition);
            }
        }
        else if(!blockEntity.isFull() && server.getTickCount() % FurnitureConfig.COMMON.pullMailInterval.get() == 0)
        {
            Supplier<Mail> supplier = PostOffice.getMailForPlayerMailBox(blockEntity.ownerId, blockEntity.id);
            while(!blockEntity.isFull())
            {
                Mail mail = supplier.get();
                if(mail == null) break;
                blockEntity.addItem(mail.getStack());
            }
        }
    }

    @Override
    public int getContainerSize()
    {
        return 9;
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container.cfm.mail_box", this.ownerName, this.name);
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new MailBoxMenu(windowId, playerInventory, this);
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
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        CompoundTag compound = pkt.getTag();
        this.readData(compound);
    }

    private void readData(CompoundTag compound)
    {
        if(compound.hasUUID("MailBoxUUID"))
        {
            this.id = compound.getUUID("MailBoxUUID");
        }
        if(compound.contains("MailBoxName", Constants.NBT.TAG_STRING))
        {
            this.name = compound.getString("MailBoxName");
        }
        if(compound.contains("OwnerName", Constants.NBT.TAG_STRING))
        {
            this.ownerName = compound.getString("OwnerName");
        }
        if(compound.hasUUID("OwnerUUID"))
        {
            this.ownerId = compound.getUUID("OwnerUUID");
        }
    }

    private CompoundTag writeData(CompoundTag compound)
    {
        if(this.id != null)
        {
            compound.putUUID("MailBoxUUID", this.id);
        }
        if(this.name != null)
        {
            compound.putString("MailBoxName", this.name);
        }
        if(this.ownerName != null && this.ownerId != null)
        {
            compound.putString("OwnerName", this.ownerName);
            compound.putUUID("OwnerUUID", this.ownerId);
        }
        return compound;
    }
}
