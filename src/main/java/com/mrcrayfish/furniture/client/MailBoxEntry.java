package com.mrcrayfish.furniture.client;

import net.minecraft.nbt.CompoundNBT;

import java.util.Objects;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MailBoxEntry
{
    private UUID mailBoxId;
    private String name;
    private UUID ownerId;
    private String ownerName;

    public MailBoxEntry(UUID mailBoxId, String name, UUID ownerId, String ownerName)
    {
        this.mailBoxId = mailBoxId;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public MailBoxEntry(CompoundNBT compound)
    {
        this.mailBoxId = compound.getUniqueId("MailBoxUUID");
        this.name = compound.getString("MailBoxName");
        this.ownerId = compound.getUniqueId("OwnerUUID");
        this.ownerName = compound.getString("OwnerName");
    }

    public UUID getMailBoxId()
    {
        return this.mailBoxId;
    }

    public String getName()
    {
        return this.name;
    }

    public UUID getOwnerId()
    {
        return this.ownerId;
    }

    public String getOwnerName()
    {
        return this.ownerName;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        MailBoxEntry other = (MailBoxEntry) obj;
        return this.mailBoxId.equals(other.mailBoxId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.mailBoxId);
    }
}
