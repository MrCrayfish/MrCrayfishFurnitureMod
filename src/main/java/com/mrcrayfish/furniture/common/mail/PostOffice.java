package com.mrcrayfish.furniture.common.mail;

import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PostOffice extends SavedData
{
    private static final String ID = Reference.MOD_ID + "_post_office";

    private final Map<UUID, Map<UUID, MailBox>> playerMailboxMap = new HashMap<>();

    public static PostOffice load(CompoundTag tag)
    {
        PostOffice postOffice = new PostOffice();
        postOffice.read(tag);
        return postOffice;
    }

    public void read(CompoundTag compound)
    {
        this.playerMailboxMap.clear();
        if(compound.contains("PlayerMailBoxes", Tag.TAG_LIST))
        {
            ListTag playerMailBoxesList = compound.getList("PlayerMailBoxes", Tag.TAG_COMPOUND);
            playerMailBoxesList.forEach(nbt ->
            {
                CompoundTag playerMailBoxesCompound = (CompoundTag) nbt;
                UUID playerId = playerMailBoxesCompound.getUUID("PlayerUUID");

                if(playerMailBoxesCompound.contains("MailBoxes", Tag.TAG_LIST))
                {
                    Map<UUID, MailBox> mailBoxMap = new HashMap<>();
                    ListTag mailBoxList = playerMailBoxesCompound.getList("MailBoxes", Tag.TAG_COMPOUND);
                    mailBoxList.forEach(nbt2 ->
                    {
                        CompoundTag mailBoxCompound = (CompoundTag) nbt2;
                        UUID mailBoxId = mailBoxCompound.getUUID("MailBoxUUID");
                        MailBox mailBox = new MailBox(mailBoxCompound.getCompound("MailBox"));
                        mailBoxMap.put(mailBoxId, mailBox);
                    });
                    this.playerMailboxMap.put(playerId, mailBoxMap);
                }
            });
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        ListTag playerMailBoxesList = new ListTag();
        this.playerMailboxMap.forEach((playerId, mailStorage) ->
        {
            if(!mailStorage.isEmpty())
            {
                CompoundTag playerMailBoxesCompound = new CompoundTag();
                playerMailBoxesCompound.putUUID("PlayerUUID", playerId);

                ListTag mailBoxList = new ListTag();
                mailStorage.forEach((mailBoxId, mailBox) ->
                {
                    CompoundTag mailBoxCompound = new CompoundTag();
                    mailBoxCompound.putUUID("MailBoxUUID", mailBoxId);
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

    public static void registerMailBox(ServerPlayer player, UUID mailBoxId, String name, BlockPos pos)
    {
        PostOffice office = get(player.server);
        Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.computeIfAbsent(player.getUUID(), uuid -> new HashMap<>());
        mailBoxMap.put(mailBoxId, new MailBox(mailBoxId, name, player.getUUID(), player.getName().getString(), pos, player.level.dimension()));
        office.setDirty();
    }

    public static void unregisterMailBox(UUID playerId, UUID mailBoxId)
    {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null)
        {
            PostOffice office = get(server);
            Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.computeIfAbsent(playerId, uuid -> new HashMap<>());
            mailBoxMap.remove(mailBoxId);
            office.setDirty();
            //TODO spawn all items at mail box
        }
    }

    public static List<MailBox> getMailBoxes(ServerPlayer playerEntity)
    {
        PostOffice office = get(playerEntity.server);
        return office.playerMailboxMap.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toList());
    }

    public static boolean sendMailToPlayer(UUID playerId, UUID mailBoxId, Mail mail)
    {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null)
        {
            PostOffice office = get(server);
            Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.computeIfAbsent(playerId, uuid -> new HashMap<>());
            if(mailBoxMap.containsKey(mailBoxId))
            {
                if(mailBoxMap.get(mailBoxId).getMailCount() < FurnitureConfig.COMMON.maxMailQueue.get())
                {
                    mailBoxMap.get(mailBoxId).addMail(mail);
                    office.setDirty();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets a supplier which provides mail for the specified mail box. Every time you call
     * {@link Supplier#get} it will remove mail from the queue. You should also not store this
     * supplier as it contains a MinecraftServer instance.
     *
     * @param playerId
     * @param mailBoxId
     * @return
     */
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
                            office.setDirty();
                            return mailStorage.remove(0);
                        }
                    }
                }
            }
            return null;
        };
    }

    public static boolean setMailBoxName(UUID playerId, UUID mailBoxId, String name)
    {
        name = name.trim();
        if(name.trim().isEmpty())
            return false;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null)
        {
            PostOffice office = get(server);
            if(!office.playerMailboxMap.containsKey(playerId))
                return false;

            Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.get(playerId);
            if(!mailBoxMap.containsKey(mailBoxId))
                return false;

            MailBox mailBox = mailBoxMap.get(mailBoxId);
            mailBox.setName(name);

            ServerLevel level = server.getLevel(mailBox.getLevelResourceKey());
            if(level == null || !level.isAreaLoaded(mailBox.getPos(), 0))
                return false;

            if(!(level.getBlockEntity(mailBox.getPos()) instanceof MailBoxBlockEntity mailBoxBlockEntity))
                return false;

            mailBoxBlockEntity.setMailBoxName(name);
            BlockEntityUtil.sendUpdatePacket(mailBoxBlockEntity);
            return true;
        }
        return false;
    }

    public static boolean isRegistered(UUID playerId, UUID mailBoxId)
    {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null)
        {
            PostOffice office = get(server);
            if(office.playerMailboxMap.containsKey(playerId))
            {
                return office.playerMailboxMap.get(playerId).containsKey(mailBoxId);
            }
        }
        return false;
    }

    private static PostOffice get(MinecraftServer server)
    {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        return Objects.requireNonNull(level).getDataStorage().computeIfAbsent(PostOffice::load, PostOffice::new, ID);
    }

    /*
     * Cleans up invalid mail boxes or mail boxes removed by other means than a player.
     */
    @SubscribeEvent
    public static void onTick(TickEvent.LevelTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(event.side != LogicalSide.SERVER)
            return;

        MinecraftServer server = event.level.getServer();
        if(server != null && server.getTickCount() % 1200 == 0)
        {
            PostOffice office = get(server);
            office.playerMailboxMap.values().forEach(map ->
            {
                Predicate<MailBox> removePredicate = mailBox ->
                {
                    BlockPos pos = mailBox.getPos();
                    ServerLevel level = server.getLevel(mailBox.getLevelResourceKey());
                    if(level != null)
                    {
                        if(level.isAreaLoaded(pos, 0))
                        {
                            if(level.getBlockEntity(pos) instanceof MailBoxBlockEntity mailBoxBlockEntity)
                            {
                                return mailBoxBlockEntity.getId() == null || !Objects.equals(mailBoxBlockEntity.getId(), mailBox.getId());
                            }
                            return true;
                        }
                        return false;
                    }
                    return true;
                };
                if(map.values().removeIf(removePredicate))
                {
                    office.setDirty();
                }
            });
        }
    }
}
