package com.mrcrayfish.furniture.common.mail;

import com.mrcrayfish.furniture.FurnitureConfig;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

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
public class PostOffice extends WorldSavedData
{
    private static final String DATA_NAME = Reference.MOD_ID + "_post_office";

    private final Map<UUID, Map<UUID, MailBox>> playerMailboxMap = new HashMap<>();

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
        this.playerMailboxMap.clear();
        if(compound.contains("PlayerMailBoxes", Constants.NBT.TAG_LIST))
        {
            ListNBT playerMailBoxesList = compound.getList("PlayerMailBoxes", Constants.NBT.TAG_COMPOUND);
            playerMailBoxesList.forEach(nbt ->
            {
                CompoundNBT playerMailBoxesCompound = (CompoundNBT) nbt;
                UUID playerId = playerMailBoxesCompound.getUniqueId("PlayerUUID");

                if(playerMailBoxesCompound.contains("MailBoxes", Constants.NBT.TAG_LIST))
                {
                    Map<UUID, MailBox> mailBoxMap = new HashMap<>();
                    ListNBT mailBoxList = playerMailBoxesCompound.getList("MailBoxes", Constants.NBT.TAG_COMPOUND);
                    mailBoxList.forEach(nbt2 ->
                    {
                        CompoundNBT mailBoxCompound = (CompoundNBT) nbt2;
                        UUID mailBoxId = mailBoxCompound.getUniqueId("MailBoxUUID");
                        MailBox mailBox = new MailBox(mailBoxCompound.getCompound("MailBox"));
                        mailBoxMap.put(mailBoxId, mailBox);
                    });
                    this.playerMailboxMap.put(playerId, mailBoxMap);
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

    public static void registerMailBox(ServerPlayerEntity playerEntity, UUID mailBoxId, String name, BlockPos pos)
    {
        PostOffice office = get(playerEntity.server);
        Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.computeIfAbsent(playerEntity.getUniqueID(), uuid -> new HashMap<>());
        mailBoxMap.put(mailBoxId, new MailBox(mailBoxId, name, playerEntity.getUniqueID(), playerEntity.getName().getString(), pos, playerEntity.world.getDimensionKey()));
        office.markDirty();
    }

    public static void unregisterMailBox(UUID playerId, UUID mailBoxId)
    {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null)
        {
            PostOffice office = get(server);
            Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.computeIfAbsent(playerId, uuid -> new HashMap<>());
            mailBoxMap.remove(mailBoxId);
            office.markDirty();
            //TODO spawn all items at mail box
        }
    }

    public static List<MailBox> getMailBoxes(ServerPlayerEntity playerEntity)
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
                    office.markDirty();
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
                            office.markDirty();
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
            if(office.playerMailboxMap.containsKey(playerId))
            {
                Map<UUID, MailBox> mailBoxMap = office.playerMailboxMap.get(playerId);
                if(mailBoxMap.containsKey(mailBoxId))
                {
                    MailBox mailBox = mailBoxMap.get(mailBoxId);
                    mailBox.setName(name);

                    ServerWorld world = server.getWorld(mailBox.getWorld());
                    if(world != null && world.isAreaLoaded(mailBox.getPos(), 0))
                    {
                        TileEntity tileEntity = world.getTileEntity(mailBox.getPos());
                        if(tileEntity instanceof MailBoxTileEntity)
                        {
                            ((MailBoxTileEntity) tileEntity).setMailBoxName(name);
                            TileEntityUtil.sendUpdatePacket(tileEntity);
                            return true;
                        }
                    }
                }
            }
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
        ServerWorld world = server.getWorld(World.OVERWORLD);
        return world.getSavedData().getOrCreate(PostOffice::new, DATA_NAME);
    }

    /*
     * Cleans up invalid mail boxes or mail boxes removed by other means than a player.
     */
    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(event.side != LogicalSide.SERVER)
            return;

        MinecraftServer server = event.world.getServer();
        if(server != null && server.getTickCounter() % 1200 == 0)
        {
            PostOffice office = get(server);
            office.playerMailboxMap.values().forEach(map ->
            {
                Predicate<MailBox> removePredicate = mailBox ->
                {
                    BlockPos pos = mailBox.getPos();
                    ServerWorld world = server.getWorld(mailBox.getWorld());
                    if(world != null)
                    {
                        if(world.isAreaLoaded(pos, 0))
                        {
                            TileEntity tileEntity = world.getTileEntity(pos);
                            if(tileEntity instanceof MailBoxTileEntity)
                            {
                                MailBoxTileEntity mailBoxTileEntity = (MailBoxTileEntity) tileEntity;
                                return mailBoxTileEntity.getId() == null || !Objects.equals(mailBoxTileEntity.getId(), mailBox.getId());
                            }
                            return true;
                        }
                        return false;
                    }
                    return true;
                };
                if(map.values().removeIf(removePredicate))
                {
                    office.markDirty();
                }
            });
        }
    }
}
