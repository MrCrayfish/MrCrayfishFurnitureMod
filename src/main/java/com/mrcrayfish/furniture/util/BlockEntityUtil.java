package com.mrcrayfish.furniture.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class BlockEntityUtil
{
    /**
     * Sends an update packet to clients tracking a tile entity.
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(BlockEntity tileEntity)
    {
        ClientboundBlockEntityDataPacket packet = tileEntity.getUpdatePacket();
        if(packet != null)
        {
            sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
        }
    }

    /**
     * Sends an update packet to clients tracking a tile entity with a specific CompoundTag
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(BlockEntity tileEntity, CompoundTag compound)
    {
        ClientboundBlockEntityDataPacket packet = new ClientboundBlockEntityDataPacket(tileEntity.getBlockPos(), 0, compound);
        sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
    }

    public static void sendUpdatePacketSimple(BlockEntity tileEntity, CompoundTag compound)
    {
        ResourceLocation id = BlockEntityType.getKey(tileEntity.getType());
        compound.putString("id", id.toString());
        compound.putInt("x", tileEntity.getBlockPos().getX());
        compound.putInt("y", tileEntity.getBlockPos().getY());
        compound.putInt("z", tileEntity.getBlockPos().getZ());
        ClientboundBlockEntityDataPacket packet = new ClientboundBlockEntityDataPacket(tileEntity.getBlockPos(), 0, compound);
        sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
    }

    private static void sendUpdatePacket(Level level, BlockPos pos, ClientboundBlockEntityDataPacket packet)
    {
        if(level instanceof ServerLevel)
        {
            ServerLevel server = (ServerLevel) level;
            Stream<ServerPlayer> players = server.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }
}
