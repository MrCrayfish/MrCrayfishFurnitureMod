package com.mrcrayfish.furniture.common.mail;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class PostOffice extends WorldSavedData
{
    private static final String DATA_NAME = Reference.MOD_ID + "_PostOffice";

    private Map<UUID, List<Mail>> playerMailStorage = new HashMap<>();

    public PostOffice()
    {
        super(DATA_NAME);
    }

    public PostOffice(String name)
    {
        super(name);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        playerMailStorage.clear();
        if(compound.contains("PlayerMailStorage", Constants.NBT.TAG_LIST))
        {
            ListNBT playerMailStorageList = compound.getList("PlayerMailStorage", Constants.NBT.TAG_COMPOUND);
            playerMailStorageList.forEach(nbt ->
            {
                CompoundNBT mailStorageCompound = (CompoundNBT) nbt;

                List<Mail> mailStorage = new ArrayList<>();
                ListNBT mailStorageList = mailStorageCompound.getList("MailStorage", Constants.NBT.TAG_COMPOUND);
                mailStorageList.forEach(nbt2 ->
                {
                    CompoundNBT mailCompound = (CompoundNBT) nbt2;
                    mailStorage.add(new Mail(mailCompound));
                });

                UUID uuid = mailStorageCompound.getUniqueId("PlayerUUID");
                playerMailStorage.put(uuid, mailStorage);
            });
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        ListNBT playerMailStorageList = new ListNBT();
        this.playerMailStorage.forEach((uuid, mailStorage) ->
        {
            if(!mailStorage.isEmpty())
            {
                CompoundNBT mailStorageCompound = new CompoundNBT();
                mailStorageCompound.putUniqueId("PlayerUUID", uuid);

                ListNBT mailStorageList = new ListNBT();
                mailStorage.forEach(mail -> mailStorageList.add(mail.serializeNBT()));
                mailStorageCompound.put("MailStorage", mailStorageList);

                playerMailStorageList.add(mailStorageCompound);
            }
        });
        compound.put("PlayerMailStorage", playerMailStorageList);
        return compound;
    }

    public static void sendMailToPlayer(ServerPlayerEntity playerEntity, Mail mail)
    {
        PostOffice office = get(playerEntity.server);
        List<Mail> mailStorage = office.playerMailStorage.computeIfAbsent(playerEntity.getUniqueID(), uuid -> new ArrayList<>());
        mailStorage.add(mail);
    }

    public static Supplier<Mail> getMailForPlayer(UUID uuid)
    {
        return () ->
        {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if(server != null)
            {
                PostOffice office = get(server);
                if(office.playerMailStorage.containsKey(uuid))
                {
                    List<Mail> mailStorage = office.playerMailStorage.get(uuid);
                    if(!mailStorage.isEmpty())
                    {
                        Mail mail = mailStorage.remove(0);
                        if(mailStorage.isEmpty())
                        {
                            office.playerMailStorage.remove(uuid);  //remove memory if mail box is empty
                        }
                        return mail;
                    }
                }
            }
            return null;
        };
    }

    private static PostOffice get(MinecraftServer server)
    {
        ServerWorld world = server.getWorld(DimensionType.OVERWORLD);
        return world.getSavedData().getOrCreate(PostOffice::new, DATA_NAME);
    }
}
