package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.inventory.container.MailBoxContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MailBoxTileEntity extends BasicLootTileEntity implements ITickableTileEntity
{
    private UUID id;
    private String name = "Mail Box";
    private String ownerName;
    private UUID ownerId;

    public MailBoxTileEntity()
    {
        super(ModTileEntities.MAIL_BOX.get());
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

    public void setOwner(ServerPlayerEntity entity)
    {
        this.ownerId = entity.getUniqueID();
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

    public void updateOwnerName(ServerPlayerEntity player)
    {
        if(player.getUniqueID().equals(this.ownerId))
        {
            if(!player.getName().getString().equals(this.ownerName))
            {
                this.ownerName = player.getName().getString();
            }
        }
    }

    public void updateIdAndAttemptClaim(ServerPlayerEntity player)
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

    @Override
    public void tick()
    {
        if(this.world != null && !this.world.isRemote)
        {
            MinecraftServer server = this.world.getServer();
            if(server == null || this.ownerId == null || this.id == null)
                return;

            // Attempt to register the mail box if it was somehow removed
            if(!PostOffice.isRegistered(this.ownerId, this.id))
            {
                ServerPlayerEntity player = server.getPlayerList().getPlayerByUUID(this.ownerId);
                if(player != null)
                {
                    PostOffice.registerMailBox(player, this.id, "Mail Box", this.pos);
                }
            }
            else if(!this.isFull() && server.getTickCounter() % FurnitureConfig.COMMON.pullMailInterval.get() == 0)
            {
                Supplier<Mail> supplier = PostOffice.getMailForPlayerMailBox(this.ownerId, this.id);
                while(!this.isFull())
                {
                    Mail mail = supplier.get();
                    if(mail == null) break;
                    this.addItem(mail.getStack());
                }
            }
        }
    }

    @Override
    public int getSizeInventory()
    {
        return 18;
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.mail_box", this.ownerName, this.name);
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        return new MailBoxContainer(windowId, playerInventory, this);
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
        if(compound.hasUniqueId("MailBoxUUID"))
        {
            this.id = compound.getUniqueId("MailBoxUUID");
        }
        if(compound.contains("MailBoxName", Constants.NBT.TAG_STRING))
        {
            this.name = compound.getString("MailBoxName");
        }
        if(compound.contains("OwnerName", Constants.NBT.TAG_STRING))
        {
            this.ownerName = compound.getString("OwnerName");
        }
        if(compound.hasUniqueId("OwnerUUID"))
        {
            this.ownerId = compound.getUniqueId("OwnerUUID");
        }
    }

    private CompoundNBT writeData(CompoundNBT compound)
    {
        if(this.id != null)
        {
            compound.putUniqueId("MailBoxUUID", this.id);
        }
        if(this.name != null)
        {
            compound.putString("MailBoxName", this.name);
        }
        if(this.ownerName != null && this.ownerId != null)
        {
            compound.putString("OwnerName", this.ownerName);
            compound.putUniqueId("OwnerUUID", this.ownerId);
        }
        return compound;
    }
}
