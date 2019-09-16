package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MailBoxTileEntity extends BasicLootTileEntity implements ITickableTileEntity
{
    private UUID id;
    private UUID owner;

    public MailBoxTileEntity()
    {
        super(ModTileEntities.MAIL_BOX);
    }

    public void setId(UUID id)
    {
        if(this.id == null)
        {
            this.id = id;
        }
    }

    public UUID getId()
    {
        return id;
    }

    public void setOwner(UUID owner)
    {
        this.owner = owner;
    }

    public UUID getOwner()
    {
        return owner;
    }

    @Override
    public void tick()
    {
        if(world != null && !world.isRemote)
        {
            if(!this.isFull() && this.owner != null && this.id != null)
            {
                Supplier<Mail> supplier = PostOffice.getMailForPlayerMailBox(this.owner, this.id);
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
        return new TranslationTextComponent("container.cfm.mail_box");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        return new ChestContainer(ContainerType.GENERIC_9X2, windowId, playerInventory, this, 2);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if(compound.hasUniqueId("MailBoxUUID"))
        {
            this.id = compound.getUniqueId("MailBoxUUID");
        }
        if(compound.hasUniqueId("OwnerUUID"))
        {
            this.owner = compound.getUniqueId("OwnerUUID");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.putUniqueId("MailBoxUUID", this.id);
        compound.putUniqueId("OwnerUUID", this.owner);
        return super.write(compound);
    }

    @Override
    public void remove()
    {
        super.remove();
    }
}
