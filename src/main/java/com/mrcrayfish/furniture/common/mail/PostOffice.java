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

    private Map<UUID, Map<UUID, MailBox>> playerMailboxMap = new HashMap<>();

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
        playerMailboxMap.clear();
        if(compound.contains("PlayerMailBoxes", Constants.NBT.TAG_LIST))
        {
            ListNBT playerMailBoxesList = compound.getList("PlayerMailBoxes", Constants.NBT.TAG_COMPOUND);
            playerMailBoxesList.forEach(nbt ->
            {
                CompoundNBT playerMailBoxesCompound = (CompoundNBT) nbt;
                UUID playerId = playerMailBoxesCompound.getUniqueId("PlayerUUID");

                if(compound.contains("MailBoxes", Constants.NBT.TAG_LIST))
                {
                    Map<UUID, MailBox> mailBoxMap = new HashMap<>();
                    ListNBT mailBoxList = compound.getList("MailBoxes", Constants.NBT.TAG_COMPOUND);
                    mailBoxList.forEach(nbt2 ->
                    {
                        CompoundNBT mailBoxCompound = (CompoundNBT) nbt2;
                        UUID mailBoxId = mailBoxCompound.getUniqueId("MailBoxUUID");
                        MailBox mailBox = new MailBox(mailBoxCompound.getCompound("MailBox"));
                        mailBoxMap.put(mailBoxId, mailBox);
                    });
                    playerMailboxMap.put(playerId, mailBoxMap);
                }
            });
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        ListNBT playerMailBoxesList = new ListNBT();
        this.playerMailboxMap.forEach((playerId, mailStorage) ->
        {
            if(!mailStorage.isEmpty())
            {
                CompoundNBT playerMailBoxesCompound = new CompoundNBT();
                playerMailBoxesCompound.putUniqueId("PlayerUUID", playerId);

                ListNBT mailBoxList = new ListNBT();
                mailStorage.forEach((mailBoxId, mailBox) ->
                {
                    CompoundNBT mailBoxCompound = new CompoundNBT();
                    mailBoxCompound.putUniqueId("MailBoxUUID", mailBoxId);
                    mailBoxCompound.put("MailBox", mailBox.serializeNBT());
                    mailBoxList.add(mailBoxCompound);
                });
                playerMailBoxesCompound.put("MailBoxes", mailBoxList);

                playerMailBoxesList.add(playerMailBoxesCompound);
            }
        });
        compound.put("PlayerMailBoxes", playerMailBoxesList);
        return compound;
    }

    public static boolean sendMailToPlayer(ServerPlayerEntity playerEntity, UUID mailBoxId, Mail mail)
    {
        PostOffice office = get(playerEntity.server);
        Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.computeIfAbsent(playerEntity.getUniqueID(), uuid -> new HashMap<>());
        if(mailBoxMap.containsKey(mailBoxId))
        {
            mailBoxMap.get(mailBoxId).addMail(mail);
            return true;
        }
        return false;
    }

    public static Supplier<Mail> getMailForPlayerMailBox(UUID playerId, UUID mailBoxId)
    {
        return () ->
        {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if(server != null)
            {
                PostOffice office = get(server);
                if(office.playerMailboxMap.containsKey(playerId))
                {
                    Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.get(playerId);
                    if(mailBoxMap.containsKey(mailBoxId))
                    {
                        MailBox mailBox = mailBoxMap.get(mailBoxId);
                        List<Mail> mailStorage = mailBox.getMailStorage();
                        if(!mailStorage.isEmpty())
                        {
                            return mailStorage.remove(0);
                        }
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
