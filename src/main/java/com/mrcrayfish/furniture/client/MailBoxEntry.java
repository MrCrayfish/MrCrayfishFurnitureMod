package com.mrcrayfish.furniture.client;

import net.minecraft.nbt.CompoundNBT;

import java.util.Objects;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MailBoxEntry
{
    private String name;
    private String ownerName;
    private UUID mailBoxId;

    public MailBoxEntry(String name, String ownerName, UUID mailBoxId)
    {
        this.name = name;
        this.ownerName = ownerName;
        this.mailBoxId = mailBoxId;
    }

    public MailBoxEntry(CompoundNBT compound)
    {
        this.name = compound.getString("Name");
        this.ownerName = compound.getString("OwnerName");
        this.mailBoxId = compound.getUniqueId("MailBoxUUID");
    }

    public String getName()
    {
        return name;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public UUID getMailBoxId()
    {
        return mailBoxId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        MailBoxEntry other = (MailBoxEntry) obj;
        return mailBoxId.equals(other.mailBoxId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(mailBoxId);
    }
}
