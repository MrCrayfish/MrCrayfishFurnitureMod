package com.mrcrayfish.furniture.common.mail;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MailBox implements INBTSerializable<CompoundTag>
{
    private UUID id;
    private String name;
    private UUID ownerId;
    private String ownerName;
    private BlockPos pos;
    private ResourceKey<Level> levelResourceKey = Level.OVERWORLD;
    private List<Mail> mailStorage = new ArrayList<>();

    public MailBox(UUID id, String name, UUID ownerId, String ownerName, BlockPos pos, ResourceKey<Level> levelResourceKey)
    {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.pos = pos;
        this.levelResourceKey = levelResourceKey;
    }

    public MailBox(CompoundTag compound)
    {
        this.deserializeNBT(compound);
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public UUID getOwnerId()
    {
        return this.ownerId;
    }

    public String getOwnerName()
    {
        return this.ownerName;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public ResourceKey<Level> getLevelResourceKey()
    {
        return levelResourceKey;
    }

    public void addMail(Mail mail)
    {
        this.mailStorage.add(mail);
    }

    public List<Mail> getMailStorage()
    {
        return this.mailStorage;
    }

    public int getMailCount()
    {
        return this.mailStorage.size();
    }

    public CompoundTag serializeDetails()
    {
        CompoundTag compound = new CompoundTag();
        compound.putUUID("MailBoxUUID", this.id);
        compound.putString("MailBoxName", this.name);
        compound.putUUID("OwnerUUID", this.ownerId);
        compound.putString("OwnerName", this.ownerName);
        return compound;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag compound = new CompoundTag();
        compound.putUUID("MailBoxUUID", this.id);
        compound.putString("MailBoxName", this.name);
        compound.putUUID("OwnerUUID", this.ownerId);
        compound.putString("OwnerName", this.ownerName);
        compound.putLong("Pos", this.pos.asLong());
        compound.putString("World", this.levelResourceKey.location().toString());

        if(!this.mailStorage.isEmpty())
        {
            ListTag mailStorageList = new ListTag();
            this.mailStorage.forEach(mail -> mailStorageList.add(mail.serializeNBT()));
            compound.put("MailStorage", mailStorageList);
        }

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound)
    {
        this.mailStorage = new ArrayList<>();

        this.id = compound.getUUID("MailBoxUUID");
        this.name = compound.getString("MailBoxName");
        this.ownerId = compound.getUUID("OwnerUUID");
        this.ownerName = compound.getString("OwnerName");
        this.pos = BlockPos.of(compound.getLong("Pos"));
        this.levelResourceKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(compound.getString("World")));

        if(compound.contains("MailStorage", Tag.TAG_LIST))
        {
            ListTag mailStorageList = compound.getList("MailStorage", Tag.TAG_COMPOUND);
            mailStorageList.forEach(nbt2 ->
            {
                CompoundTag mailCompound = (CompoundTag) nbt2;
                this.mailStorage.add(new Mail(mailCompound));
            });
        }
    }
}
